package com.example.gamelogic.Managers;

import com.example.engine.IEngine;
import com.example.gamelogic.Scenes.MenuScene;
import com.example.gamelogic.Scenes.Scene;
import com.example.gamelogic.Scenes.SceneNames;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import netscape.javascript.JSObject;


public class SaveManager {

    private static SaveManager instance;
    private int[][]matrizJuego;
    private int solution[];
    private boolean saved_=false;
    private boolean repeat;
    private int tries;
    private int solutionColors;
    private int posiblecolors;
    private SaveManager() {

    }
    public boolean isSaved(){
        return saved_;
    }
    public void setSaved(boolean saved){
         saved_=saved;
    }
    public int[] getSolution() {
        return solution;
    }
    public void setSolution(int[]sol){
        solution=sol;
    }
    public int[][] getmatrizJuego() {
        return matrizJuego;
    }
    public void setmatrizJuego(int[][]sol){
        matrizJuego=sol;
    }

    public static void init() {
        instance = new SaveManager();

    }

    public static SaveManager getInstance() {
        return instance;
    }


    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public int getSolutionColors() {
        return solutionColors;
    }

    public void setSolutionColors(int solutionColors) {
        this.solutionColors = solutionColors;
    }

    public int getPosiblecolors() {
        return posiblecolors;
    }

    public void setPosiblecolors(int posiblecolors) {
        this.posiblecolors = posiblecolors;
    }
    void saveData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("saved",saved_);
        jsonObject.addProperty("repeat",repeat);
        jsonObject.addProperty("tries",tries);
        jsonObject.addProperty("solutionColors",solutionColors);
        jsonObject.addProperty("posiblecolors",posiblecolors);
        JsonArray solutionArray = new JsonArray();
        for (int value : solution) {
            solutionArray.add(value);
        }

        // Agregar el array de JSON al objeto principal
        jsonObject.add("solution", solutionArray);
        JsonArray matriz = new JsonArray();
        for (int[] row : matrizJuego) {
            JsonArray rowArray = new JsonArray();
            for (int value : row) {
                rowArray.add(value);
            }
            matriz.add(rowArray);
        }
        jsonObject.add("matrizJuego", matriz);
        String js=jsonObject.toString();
       FileOutputStream fileOutputStream = GameManager.getInstance_().getIEngine().getFileOutputStream("salvado.json");
        try {
            fileOutputStream.write(js.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Escribir el objeto JSON en el archivo



            System.out.println("Archivo guardado exitosamente en: saved.json");

    }


    public void ReadData() {
        FileInputStream file = GameManager.getInstance_().getIEngine().getFileInputStream("salvado.json");
        if (file == null) {
            return;
        }
        int size = 0;
        byte[] buffer;
        try {
            size = file.available();
            buffer = new byte[size];
            file.read(buffer);
            file.close();
        } catch (IOException e) {
           return;
        }


        String jsonString = null;
        try {
            jsonString = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return;
        }
        if (jsonString != null) {
            // Convertir la cadena JSON a un objeto JsonObject
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

            // Ahora puedes trabajar con el objeto JsonObject
            System.out.println("Contenido cargado del archivo JSON: " + jsonObject);
            saved_= jsonObject.getAsJsonPrimitive("saved").getAsBoolean();
            repeat = jsonObject.getAsJsonPrimitive("repeat").getAsBoolean();
            tries = jsonObject.getAsJsonPrimitive("tries").getAsInt();
            solutionColors = jsonObject.getAsJsonPrimitive("solutionColors").getAsInt();
            posiblecolors = jsonObject.getAsJsonPrimitive("posiblecolors").getAsInt();


            JsonArray triesArray = jsonObject.getAsJsonArray("solution");
            solution=new int[triesArray.size()];
            for (int i = 0; i < triesArray.size(); i++) {
                solution[i] = triesArray.get(i).getAsInt();
            }
            JsonArray matrizArray = jsonObject.getAsJsonArray("matrizJuego");
            matrizJuego = new int[matrizArray.size()][];
            for (int i = 0; i < matrizArray.size(); i++) {
                JsonArray rowArray = matrizArray.get(i).getAsJsonArray();
                matrizJuego[i] = new int[rowArray.size()];
                for (int j = 0; j < rowArray.size(); j++) {
                    matrizJuego[i][j] = rowArray.get(j).getAsInt();
                }
            }


        }

    }
}
