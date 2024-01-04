package com.example.androidgame.GameLogic.Utils;


import com.example.androidengine.FileManager;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Theme;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LevelReader {
    private String levelPath = "Levels/";
    private ArrayList<Theme> tematicas_;
    ArrayList<ArrayList<Difficulty>> dificultades;
    private ArrayList<Integer> numNiveles;
    private int numWorlds_ = 0;

    public int getNumWorlds_() {
        return numWorlds_;
    }

    public LevelReader() {
        numNiveles = new ArrayList<>();
        tematicas_ = new ArrayList<>();
        dificultades = new ArrayList<>();
    }

    public int getNumLevels(int world) {
        return numNiveles.get(world);
    }

    public ArrayList<Difficulty> geDifficultylevels(int world) {
        return dificultades.get(world);
    }

    public Theme getTematicaWorld(int world) {
        return tematicas_.get(world);
    }

    public void readWorlds(String path) {

        String[] nameWorld = GameManager.getInstance().getIEngine().getFileManager().getFolderNamesInFolder(levelPath);

        for (int i = 0; i < nameWorld.length; i++) {
            readWorld(nameWorld[i]);
            numWorlds_++;
        }
    }

    private JSONObject readJsonFromInputStream(InputStream inp) {
        try {
            // Lee el contenido del InputStream proporcionado
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp));

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
            throw new RuntimeException("Error al leer el archivo JSON desde InputStream", e);
        }
    }

    private Theme readThemeFromJson(InputStream inp) {
        try {
            JSONObject jsonObject = readJsonFromInputStream(inp);

            String tematica = jsonObject.getString("style");
            String background = jsonObject.getString("background");
            String gameBackground = jsonObject.getString("gameBackground");
            String bolas = jsonObject.getString("bolas");

            Theme tematica_ = new Theme(tematica, background, gameBackground, bolas);
            return tematica_;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Difficulty readDifficultyFromJson(InputStream inp) {
        Difficulty dif = new Difficulty();
        try {
            JSONObject jsonObject = readJsonFromInputStream(inp);

            int codeSize = jsonObject.getInt("codeSize");
            int codeOpt = jsonObject.getInt("codeOpt");
            int attempts = jsonObject.getInt("attempts");
            boolean repeat = jsonObject.getBoolean("repeat");


            dif.setSolutionColors(codeSize);
            dif.setPosibleColors(codeOpt);
            dif.setTries(attempts);
            dif.setRepeat(repeat);
        } catch (JSONException e) {
            dif.setSolutionColors(1);
            dif.setPosibleColors(1);
            dif.setTries(1);
            dif.setRepeat(false);
        }
        return dif;
    }

    void readWorld(String path) {
        FileManager fileManager = GameManager.getInstance().getIEngine().getFileManager();
        TreeMap<String, InputStream> niveles = fileManager.getFilesInFolder(levelPath + path + "/");

        ArrayList<Difficulty> diff_ = new ArrayList<>();
        for (Map.Entry<String, InputStream> entry : niveles.entrySet()) {
            String fileName = entry.getKey();
            InputStream inp = entry.getValue();

            Difficulty dif = new Difficulty();

            if (fileName.compareTo("style.json") == 0) {
                tematicas_.add(readThemeFromJson(inp));
                continue;
            }
            diff_.add(readDifficultyFromJson(inp));

            try {
                inp.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        numNiveles.add(diff_.size());
        dificultades.add(diff_);
    }
}
