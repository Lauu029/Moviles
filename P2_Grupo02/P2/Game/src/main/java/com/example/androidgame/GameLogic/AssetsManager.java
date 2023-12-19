package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

public class AssetsManager {

    private TreeMap<String, TreeMap<String, Boolean>> tematica_;
    private Theme circleTheme_ = new Theme("DEFAULT", "", "", "");
    private Theme worldCircleTheme_ = new Theme("DEFAULT", "", "", "");
    private Theme backgrounTheme_ = new Theme("DEFAULT", "", "", "");
    private Theme worldbackgrounTheme_ = new Theme("DEFAULT", "", "", "");
    private String paletteColor_ = "DEFAULT";
    private int backgroundColor_ = 0xFFFFF0F6;
    private int buttonColor_ = 0XD0FB839B;
    private int textColor_ = 0xFFFFFFFF;
    private int lineColor_ = 0XFF222222;
    private HashMap<String, int[]> coloresFondo_ = new HashMap<String, int[]>();
    private JSONObject colorPalettesJson_;
    private int defaultPalette[] = {0xFFFFF0F6, 0XD0FB839B, 0xFFFFFFFF, 0XFF222222};
    private int yellowPalette[] = {0xFFebe57c, 0xD0E3BE2B, 0xFFDB8D07, 0xFFDB8D07};
    private int bluePalette[] = {0xFF70b2e0, 0xD01f438f, 0xFFFFFFFF, 0xFF30ace6};
    private int greenPalette[] = {0xFF66d46b, 0xD040a845, 0xFFFFFFFF, 0xFF18571b};
    private int hotPinkPalette[] = {0xFFe681b0, 0xD0d60466, 0xFFFFFFFF, 0xFF9e0240};
    private JSONObject paleteReader;
    private Engine iEngine_;
    private static AssetsManager instance_;

    private AssetsManager() {
        // Constructor privado
    }

    Theme getCirleTheme(boolean world) {
        if (!world) return circleTheme_;
        return worldCircleTheme_;
    }

    Theme getBackgrounTheme(boolean world) {
        if (!world) return backgrounTheme_;
        return worldbackgrounTheme_;
    }

    Image getBackgroundImage(boolean world) {
        if (!world) {
            if (backgrounTheme_.getBackground() != "") {
                return iEngine_.getGraphics().newImage(backgrounTheme_.getBackground());
            }
        } else if (worldbackgrounTheme_.getBackground() != "")
            return iEngine_.getGraphics().newImage(worldbackgrounTheme_.getBackground());
        return null;
    }

    String getPaletteColor() {
        return paletteColor_;
    }

    public int getBackgroundColor() {
        return backgroundColor_;
    }

    public int getButtonColor() {
        return buttonColor_;
    }

    public int getTextColor() {
        return textColor_;
    }

    public int getLineColor() {
        return lineColor_;
    }

    public void setPaletteTheme(String paletteColor/*int idPalette*/) {
        paletteColor_ = paletteColor;
        int[] putColors_ = coloresFondo_.get(paletteColor);
        backgroundColor_ = putColors_[0];
        buttonColor_ = putColors_[1];
        textColor_ = putColors_[2];
        lineColor_ = putColors_[3];
    }

    void setBackgroundTheme(Theme t) {
        this.backgrounTheme_ = t;
    }

    void setCirleTheme(Theme tema, boolean world) {

        String temaName = tema.getName();

        if (!tematica_.containsKey(temaName)) {
            // El tema no existe en el TreeMap, crear uno nuevo y agregarlo
            TreeMap<String, Boolean> nuevoTema = new TreeMap<>();
            nuevoTema.put(tema.getPathBolas(), tema.getPurchased());
            tematica_.put(temaName, nuevoTema);
        }
        if (!world)
            circleTheme_ = tema;
        else {
            worldCircleTheme_ = tema;
        }
    }

    void setWorldTheme(Theme tema) {

        String temaName = tema.getName();

        if (!tematica_.containsKey(temaName)) {
            // El tema no existe en el TreeMap, crear uno nuevo y agregarlo
            TreeMap<String, Boolean> nuevoTema = new TreeMap<>();
            nuevoTema.put(tema.getPathBolas(), tema.getPurchased());
            tematica_.put(temaName, nuevoTema);
        }
        worldCircleTheme_ = tema;
        worldbackgrounTheme_ = tema;
    }

    public static AssetsManager getInstance() {
        return instance_;
    }

    public static int init(Engine engine) {
        instance_ = new AssetsManager();
        instance_.iEngine_ = engine;
        instance_.tematica_ = new TreeMap<String, TreeMap<String, Boolean>>();
        String nuevaClave = "DEFAULT";
        TreeMap<String, Boolean> nuevoValor = new TreeMap<>();
        nuevoValor.put("", true);
        instance_.tematica_.put(nuevaClave, nuevoValor);
        instance_.getColorPalette();
        instance_.coloresFondo_.put("DEFAULT", instance_.defaultPalette);
        return 1;
    }

    void setDefaultPalette() {
        paletteColor_ = "DEFAULT";
        setPaletteTheme(paletteColor_);
    }

    void setDefaultBackground() {
        backgrounTheme_.setBackground("");
    }

    void setDefaultCodes() {
        circleTheme_ = new Theme("DEFAULT", "", "", "");
    }

    void getColorPalette() {
        InputStream file = iEngine_.getFileManager().getInputStream("Images/Shop/Color/Colores.json");
        colorPalettesJson_ = readFile(file);
    }

    void addNewPalette(String color) {
        if (!coloresFondo_.containsKey(color)) {
            coloresFondo_.put(color, getColorsArray(color));
        }
        setPaletteTheme(color);
    }

    private int[] getColorsArray(String path) {
        if (colorPalettesJson_ != null) {
            JSONObject config = null;
            try {
                config = colorPalettesJson_.getJSONObject(path);
                JSONArray colorsArray = config.getJSONArray("Palette");
                int[] logicArray = new int[colorsArray.length()];
                for (int i = 0; i < colorsArray.length(); i++) {
                    String hexColor = colorsArray.getString(i);
                    int intColor = parseHexToInt(hexColor);
                    logicArray[i] = intColor;
                }
                return logicArray;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static int parseHexToInt(String hex) {
        // Quitar el prefijo "0x" si está presente
        hex = hex.substring(2);
        // Convertir el hexadecimal a un entero
        return (int) Long.parseLong(hex, 16);
    }

    public JSONObject readFile(InputStream file) {
        try {
            // Lee el contenido del InputStream proporcionado
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            // Convierte la cadena JSON en un objeto JSONObject
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("ShopConfigReader", "Error al leer el archivo JSON desde InputStream");
            return null;
        }
    }
}
