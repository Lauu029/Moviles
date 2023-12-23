package com.example.androidgame.GameLogic;

import android.content.Context;
import android.util.Log;

import com.example.androidengine.NDKManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class SaveData {

    private static final String FILENAME = "saved_data.json";
    private static final String HASHFILE= "hash_data.json";
    private static String codigo1_="GARBANZOS";
    private static String codigo2="BUSTAMANTE";
    public static void saveGameData(Context context, int coins, String palette, int currWorld, int currLevel,
                                    String backgroundPath, String codePath) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("coins", coins);
            jsonObject.put("palette", palette);
            jsonObject.put("world", currWorld);
            jsonObject.put("level", currLevel);
            jsonObject.put("background", backgroundPath);
            jsonObject.put("codes",codePath);
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
            jsonObject.put("tienda", shopArray);
            String jsonString = jsonObject.toString();

            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();

            //Vamos a generar el hash
            String primerHash= codigo1_+jsonString;
            String hashJSON= NDKManager.generateHash(primerHash);
            String segundoHash= codigo2+hashJSON;
            String finalHash=NDKManager.generateHash(segundoHash);

            //Lo guardamos en otro archivo
            FileOutputStream hashOutputStream = context.openFileOutput(HASHFILE, Context.MODE_PRIVATE);
            hashOutputStream.write(finalHash.getBytes());
            hashOutputStream.close();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameData(Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();

            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);

            //Aqui volvemos a hacer el hash
            //Vamos a generar el hash
            String primerHash= codigo1_+jsonString;
            String hashJSON= NDKManager.generateHash(primerHash);
            String segundoHash= codigo2+hashJSON;
            String finalHash=NDKManager.generateHash(segundoHash);

            //Leer el archivo de hash
            FileInputStream hashInputStream = context.openFileInput(HASHFILE);
            int sizeHash = hashInputStream.available();
            byte[] bufferHash = new byte[sizeHash];
            hashInputStream.read(bufferHash);
            hashInputStream.close();
            String hashString = new String(bufferHash, "UTF-8");

            //Si intenta cambiar los datos le endeudamos con 100 monedas
            if(!finalHash.equals(hashString)){
                GameManager.getInstance().setCoins(-100);
            }
            else
            {
                int coins = jsonObject.getInt("coins");
                GameManager.getInstance().setCoins(coins);
            }

            String paletteStr = jsonObject.getString("palette");
            String paletteName = String.valueOf(paletteStr);
            AssetsManager.getInstance().addNewPalette(paletteName);

            int world = jsonObject.getInt("world");
            LevelManager.getInstance().setPassedWorld(world);
            int level = jsonObject.getInt("level");
            LevelManager.getInstance().setPassedLevel(level);

            String backgroundImage = jsonObject.getString("background");
            AssetsManager.getInstance().setBackgroundTheme(new Theme("DEFAULT",backgroundImage,"",""));

            String codesPath = jsonObject.getString("codes");
            AssetsManager.getInstance().setCirleTheme(new Theme("DEFAULT", "","",codesPath),false);
            // Registramos en el mapa las cosas desbloqueadas
            JSONObject tiendaInfo = jsonObject.getJSONArray("tienda").getJSONObject(0);
            for (int i = 0; i < tiendaInfo.length(); i++) {
                String typeId = tiendaInfo.names().getString(i);
                JSONArray itemsArray = tiendaInfo.getJSONArray(typeId);

                for (int j = 0; j < itemsArray.length(); j++) {
                    String itemId = itemsArray.getString(j);
                    ShopManager.getInstance().registerShopItem(typeId, itemId);
                    ShopManager.getInstance().changeItemState(typeId, itemId, false);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
