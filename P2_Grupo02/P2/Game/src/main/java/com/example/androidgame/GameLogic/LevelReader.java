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


    private ArrayList<Theme>tematicas_;
    ArrayList<ArrayList<Difficulty>>dificultades;
    private ArrayList<Integer>numNiveles;
    private  int numWorlds_ =0;
    public int getNumWorlds_(){
        return  numWorlds_;
    }
    LevelReader(){

        numNiveles=new ArrayList<>();
        tematicas_=new ArrayList<>();
        dificultades=new ArrayList<>();
    }
    int getNumLevels(int world){
        return numNiveles.get(world);
    }
    ArrayList<Difficulty>geDifficultylevels(int world){
        return dificultades.get(world);
    }
    Theme getTematicaWorld(int world){
        return tematicas_.get(world);
    }
    void readWorlds(String path){

        String[] nameWorld= GameManager.getInstance().getIEngine().getFileManager().getFolderNamesInFolder(levelPath);

        for(int i=0;i<nameWorld.length;i++){
            readWorld(nameWorld[i]);
            numWorlds_++;
        }
    }

    void readWorld(String path){

        FileManager fileManager=GameManager.getInstance().getIEngine().getFileManager();
        TreeMap<String, InputStream> niveles=fileManager.getFilesInFolder(levelPath+path+"/");

        ArrayList<Difficulty> diff_ =new ArrayList<>();
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
                String background=jsonNode.get("background").asText();
                String gameBackground=jsonNode.get("gameBackground").asText();
                String bolas=jsonNode.get("bolas").asText();


                Theme tematica_=new Theme(tematica,background,gameBackground,bolas);
                tematicas_.add(tematica_);
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
            diff_.add(dif);

            try {
                inp.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        numNiveles.add( diff_.size());
        dificultades.add(diff_);
       // tematica_=tematica;

    }
}
