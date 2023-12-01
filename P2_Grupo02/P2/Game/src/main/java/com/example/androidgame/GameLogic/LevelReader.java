package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.FileManager;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelReader {
    String levelPath="Levels/";
    int numLevels;
    ArrayList<Difficulty>levels_;
    LevelReader(){
        levels_=new ArrayList<>();
    }
    void readWorld(String path){
        FileManager fileManager=GameManager.getInstance().getIEngine().getFileManager();
        List<InputStream> niveles=fileManager.getFilesInFolder(levelPath+path+"/");
        numLevels=niveles.size();
        for(InputStream inp:niveles){
            Difficulty dif=new Difficulty();
            JsonNode jsonNode = null;
            Log.d("MainActivity","Leyendo json");
            ObjectMapper objectMapper = new ObjectMapper();
            try {

                jsonNode = objectMapper.readTree(inp);
            } catch (IOException e) {
                throw new RuntimeException(e);
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

    }
}
