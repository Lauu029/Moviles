package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.FileManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LevelReader {
    private String levelPath="Levels/";
    private int numLevels_;
    private Theme tematica_;
    private ArrayList<Difficulty>levels_;
    LevelReader(){
        levels_=new ArrayList<>();
    }
    int getNumLevels(){
        return numLevels_;
    }
    ArrayList<Difficulty>geDifficultylevels(){
        return levels_;
    }
    Theme getTematicaWorld(){
        return tematica_;
    }

    void readWorld(String path){
        FileManager fileManager=GameManager.getInstance().getIEngine().getFileManager();
        TreeMap<String, InputStream> niveles=fileManager.getFilesInFolder(levelPath+path+"/");


        for (Map.Entry<String, InputStream> entry : niveles.entrySet()) {
            String fileName = entry.getKey();
            InputStream inp = entry.getValue();

            Difficulty dif=new Difficulty();
            JsonNode jsonNode = null;
            Log.d("MainActivity","Leyendo json");
            ObjectMapper objectMapper = new ObjectMapper();
            try {

                jsonNode = objectMapper.readTree(inp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(fileName.compareTo("style.json")==0){
                String tematica=jsonNode.get("style").asText();
                String paths=jsonNode.get("path").asText();
                tematica_=new Theme(tematica,paths);

                continue;
            }
            // Asigna valores a las variables de tu programa desde el JSON
            int codeSize = jsonNode.get("codeSize").asInt();
            dif.setSolutionColors(codeSize);

            int codeOpt = jsonNode.get("codeOpt").asInt();
            dif.setPosibleColors(codeOpt);

            int attempts = jsonNode.get("attempts").asInt();
            dif.setTries(attempts);
            Log.d("MainActivity","attemps json"+attempts);
            Boolean repeat = jsonNode.get("repeat").asBoolean();
            dif.setRepeat(repeat);
            levels_.add(dif);

            try {
                inp.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        numLevels_=levels_.size();

       // tematica_=tematica;

    }
}
