package com.example.androidgame.GameLogic;
import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveData {

    private static final String FILENAME = "saved_data.json";

    public static void saveGameData(Context context, int coins,String palette,
                                    int currWorld,int currLevel/*,Map<String, Map<String, Boolean>> itemsState*/)
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("coins", coins);
            jsonObject.put("palette", palette);
            jsonObject.put("world", currWorld);
            jsonObject.put("level", currLevel);

            //Para registrar el map que contiene la informacion sobre los objetos comprados en la tienda
            for (Map.Entry<String, Map<String, Boolean>> typeEntry : ShopManager.getInstance().getItemsState().entrySet()) {
                String typeId = typeEntry.getKey();
                Map<String, Boolean> itemMap = typeEntry.getValue();

                JSONArray itemsArray = new JSONArray();

                for (Map.Entry<String, Boolean> itemEntry : itemMap.entrySet()) {
                    String itemId = itemEntry.getKey();
                    boolean isBought = itemEntry.getValue();
                    if (isBought) {
                        itemsArray.put(itemId);
                    }
                }
                jsonObject.put(typeId, itemsArray);
            }

            String jsonString = jsonObject.toString();
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
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
            int coins = jsonObject.getInt("coins");
            GameManager.getInstance().setCoins(coins);

            String paletteStr = jsonObject.getString("palette");
            String paletteName = String.valueOf(paletteStr);
            AssetsManager.getInstance().addNewPalette(paletteName);

            int world= jsonObject.getInt("world");
            LevelManager.getInstance().setPassedWorld(world);
            int level= jsonObject.getInt("level");
            LevelManager.getInstance().setPassedLevel(level);

            // Nuevo mapa que guarda la información sobre los items comprados
            Map<String, Map<String, Boolean>> itemsStateMap = new HashMap<>();

            for (Map.Entry<String, Map<String, Boolean>> typeEntry : ShopManager.getInstance().getItemsState().entrySet()) {
                String typeId = typeEntry.getKey();
                JSONArray itemsArray =jsonObject.getJSONArray(typeId);
                
                for (int i=0; i<itemsArray.length(); i++) {
                    String itemId = itemsArray.getString(i);
                    ShopManager.getInstance().registerShopItem(typeId,itemId);
                }
            }

            /*for (int i=0; i<jsonObject.names().length(); i++) {

                String key=jsonObject.names().getString(i);

                if (!key.equals("coins") && !key.equals("palette") && !key.equals("world") && !key.equals("level")) {
                    //COdigos,fondos etc
                    String typeId = key;
                    //Nombre del item
                    String itemId = jsonObject.getString(typeId);
                    // Obtener o crear el mapa correspondiente al tipo de item
                    Map<String, Boolean> itemMap = itemsStateMap.getOrDefault(typeId, new HashMap<>());
                    // Marcar el item como comprado en el mapa
                    itemMap.put(itemId, true);
                    // Actualizar
                    itemsStateMap.put(typeId, itemMap);
                }
            }*/

            // Guardar el mapa recuperado en la instancia de ShopManager
            ShopManager.getInstance().setItemsStateMap(itemsStateMap);


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
