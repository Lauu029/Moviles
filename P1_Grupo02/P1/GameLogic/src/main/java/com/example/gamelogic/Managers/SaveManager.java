package com.example.gamelogic.Managers;

import com.example.engine.IEngine;
import com.example.gamelogic.Scenes.MenuScene;
import com.example.gamelogic.Scenes.Scene;
import com.example.gamelogic.Scenes.SceneNames;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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



    }
    void ReadData(){
        FileInputStream file= GameManager.getInstance_().getIEngine().getFileInputStream("saved.json");
    }
}
