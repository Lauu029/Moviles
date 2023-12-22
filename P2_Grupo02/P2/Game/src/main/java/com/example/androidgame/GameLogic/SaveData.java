package com.example.androidgame.GameLogic;
import android.content.Context;
import android.util.Log;



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

    public static void saveGameData(Context context, int coins,String palette, int currWorld,int currLevel)
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("coins", coins);
            jsonObject.put("palette", palette);
            jsonObject.put("world", currWorld);
            jsonObject.put("level", currLevel);

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
                sectionItem.put(typeId,itemsArray);
            }
            shopArray.put(sectionItem);
            jsonObject.put("tienda", shopArray);
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

            // Registramos en el mapa las cosas desbloqueadas
            JSONObject tiendaInfo=jsonObject.getJSONArray("tienda").getJSONObject(0);
            for (int i=0; i<tiendaInfo.length(); i++) {
                String typeId = tiendaInfo.names().getString(i);
                JSONArray itemsArray =tiendaInfo.getJSONArray(typeId);

                for (int j=0; j<itemsArray.length(); j++) {
                    String itemId = itemsArray.getString(j);
                    ShopManager.getInstance().registerShopItem(typeId,itemId);
                    ShopManager.getInstance().changeItemState(typeId,itemId,false);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
