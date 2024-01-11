package com.example.androidgame.GameLogic.Utils;

import android.content.Context;

import com.example.androidengine.NDKManager;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.ShopManager;
import com.example.androidgame.GameLogic.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SaveData {

    private static final String FILENAME = "saved_data.json";
    private static final String HASHFILE = "hash_data.json";

    public static void saveGameData(int coins, String palette, int currWorld, int currLevel,
                                    String backgroundPath, String codePath, int lives) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("coins", coins);
            jsonObject.put("lives", lives);
            jsonObject.put("palette", palette);
            jsonObject.put("world", currWorld);
            jsonObject.put("level", currLevel);
            jsonObject.put("background", backgroundPath);
            jsonObject.put("codes", codePath);
            //Para registrar el map que contiene la informacion sobre los objetos comprados en la tienda
            JSONObject sectionItem = new JSONObject();
            JSONArray shopArray = new JSONArray();
            for (Map.Entry<String, Map<String, Boolean>> typeEntry : ShopManager.getInstance().getItemsState().entrySet()) {
                String typeId = typeEntry.getKey();
                Map<String, Boolean> itemMap = typeEntry.getValue();
                JSONArray itemsArray = new JSONArray();
                for (Map.Entry<String, Boolean> itemEntry : itemMap.entrySet()) {
                    String itemId = itemEntry.getKey();
                    boolean isLocked = itemEntry.getValue();
                    if (!isLocked) {
                        itemsArray.put(itemId);
                    }
                }
                sectionItem.put(typeId, itemsArray);
            }
            shopArray.put(sectionItem);
            jsonObject.put("shop", shopArray);

            //Guardado de progreso de un nivel
            JSONArray triesArray = new JSONArray();
            ArrayList<ArrayList<Integer>> levelArray = LevelManager.getInstance().getTries();
            jsonObject.put("TotalTries", LevelManager.getInstance().getTotalTries());
            for (int i = 0; i < levelArray.size(); i++) {
                JSONArray tryArray = new JSONArray();
                for (int j = 0; j < levelArray.get(i).size(); j++) {
                    tryArray.put(levelArray.get(i).get(j));
                }
                triesArray.put(tryArray);
            }
            jsonObject.put("tries", triesArray);
            //Guardado de nivel en el que estaba jugando
            jsonObject.put("playingWorld", LevelManager.getInstance().getActualWorld());
            jsonObject.put("playingLevel", LevelManager.getInstance().getActualLevel());

            //Tenemos que guardar la solucion del nivel en el que estabamos
            JSONArray solArray = new JSONArray();
            ArrayList<Integer> savedSol = LevelManager.getInstance().getCurrentSolution();
            for (int i = 0; i < savedSol.size(); i++) {
                solArray.put(savedSol.get(i));
            }
            jsonObject.put("solution", solArray);

            String jsonString = jsonObject.toString();
            FileOutputStream fileOutputStream = GameManager.getInstance().getIEngine().getFileManager().getFileOutputStream(FILENAME, true);
            //context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();

            //Vamos a generar el hash
            String finalHash = NDKManager.generateHashHMAC(jsonString);

            //Lo guardamos en otro archivo
            FileOutputStream hashOutputStream = GameManager.getInstance().getIEngine().getFileManager().getFileOutputStream(HASHFILE, true);
            hashOutputStream.write(finalHash.getBytes());
            hashOutputStream.close();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameData() {
        try {
            FileInputStream fileInputStream = GameManager.getInstance().getIEngine().getFileManager().getFileInputStream(FILENAME);
            if (fileInputStream == null)
                return;
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();

            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);

            //Aqui volvemos a hacer el hash
            //Vamos a generar el hash
            String finalHash = NDKManager.generateHashHMAC(jsonString);

            //Leer el archivo de hash
            FileInputStream hashInputStream = GameManager.getInstance().getIEngine().getFileManager().getFileInputStream(HASHFILE);
            if(hashInputStream!=null)
            {
                int sizeHash = hashInputStream.available();
                byte[] bufferHash = new byte[sizeHash];
                hashInputStream.read(bufferHash);
                hashInputStream.close();
                String hashString = new String(bufferHash, "UTF-8");

                //Si intenta cambiar los datos le endeudamos con 100 monedas
                if (!finalHash.equals(hashString)) {
                    GameManager.getInstance().setCoins(-100);
                } else {
                    int coins = jsonObject.getInt("coins");
                    GameManager.getInstance().setCoins(coins);
                }

            }

            String paletteStr = jsonObject.getString("palette");
            String paletteName = String.valueOf(paletteStr);
            AssetsManager.getInstance().addNewPalette(paletteName);

            int world = jsonObject.getInt("world");
            LevelManager.getInstance().setPassedWorld(world);
            int level = jsonObject.getInt("level");
            LevelManager.getInstance().setPassedLevel(level);

            String backgroundImage = jsonObject.getString("background");
            AssetsManager.getInstance().setBackgroundTheme(new Theme("PURCHASED", backgroundImage, "", ""));

            String codesPath = jsonObject.getString("codes");
            AssetsManager.getInstance().setCirleTheme(new Theme("PURCHASED", "", "", codesPath), false);
            // Registramos en el mapa las cosas desbloqueadas
            JSONObject tiendaInfo = jsonObject.getJSONArray("shop").getJSONObject(0);
            for (int i = 0; i < tiendaInfo.length(); i++) {
                String typeId = tiendaInfo.names().getString(i);
                JSONArray itemsArray = tiendaInfo.getJSONArray(typeId);

                for (int j = 0; j < itemsArray.length(); j++) {
                    String itemId = itemsArray.getString(j);
                    ShopManager.getInstance().registerShopItem(typeId, itemId);
                    ShopManager.getInstance().changeItemState(typeId, itemId, false);
                }
            }

            //Leemos la informacion acerca de la partida guardada
            int savedWorld = jsonObject.getInt("playingWorld");
            LevelManager.getInstance().setSavedWorld(savedWorld);
            int savedLevel = jsonObject.getInt("playingLevel");
            LevelManager.getInstance().setSavedLevel(savedLevel);

            //Numero total de intentos
            int savedTotalTries = jsonObject.getInt("TotalTries");
            LevelManager.getInstance().setTotalTries(savedTotalTries);
            //Nuevo arrayList de intentos
            ArrayList<ArrayList<Integer>> currentTries = new ArrayList<>();
            JSONArray triesArray = jsonObject.getJSONArray("tries");
            for (int i = 0; i < triesArray.length(); i++) {
                JSONArray tryArray = triesArray.getJSONArray(i);
                ArrayList<Integer> triesList = new ArrayList<>();

                for (int j = 0; j < tryArray.length(); j++) {
                    triesList.add(tryArray.getInt(j));
                }
                currentTries.add(triesList);
            }
            LevelManager.getInstance().setTries(currentTries);

            //Reestablecemos la solucion guardada
            JSONArray solArray = jsonObject.getJSONArray("solution");
            ArrayList<Integer> savedSolution = new ArrayList<>();
            for (int i = 0; i < solArray.length(); i++) {
                savedSolution.add(solArray.getInt(i));
            }
            LevelManager.getInstance().setCurrentSolution(savedSolution);

            int lives = jsonObject.getInt("lives");
            GameManager.getInstance().setCurrentLives_(lives);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
