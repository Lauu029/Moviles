package com.example.androidgame.GameLogic;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveData {

    private static final String FILENAME = "saved_data.json";

    public static void saveGameData(Context context, int coins,EnumPalette palette,int currWorld,int currLevel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("coins", coins);
            jsonObject.put("palette", palette.name());
            jsonObject.put("world", currWorld);
            jsonObject.put("level", currLevel);

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
            EnumPalette paletteEnum=EnumPalette.valueOf(paletteStr);
            AssetsManager.getInstance().setPaletteTheme(paletteEnum);

            int world= jsonObject.getInt("world");
            LevelManager.getInstance().setActualWorld(world);
            int level= jsonObject.getInt("level");
            LevelManager.getInstance().setActualLevel(level);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
