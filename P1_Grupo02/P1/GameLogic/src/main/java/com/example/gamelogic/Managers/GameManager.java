package com.example.gamelogic.Managers;

import com.example.engine.IEngine;
import com.example.engine.IScene;
import com.example.gamelogic.Board;
import com.example.gamelogic.Difficulty;

public class GameManager {
    private static GameManager instance_;
    private IEngine myEngine_;
    private IScene actualScene_;
    private int width_, height_;
    private Difficulty levelDificulty_;
    private Board board_;
    private int[] levelSolution_;
    private boolean daltonics_;

    private GameManager() {
        // Constructor privado
    }

    public static GameManager getInstance_() {
        return instance_;
    }


    public static int init(IEngine engine, int width, int height) {
        instance_ = new GameManager();
        instance_.myEngine_ = engine;
        instance_.width_ = width;
        instance_.height_ = height;
        instance_.daltonics_ = false;
        SceneManager.init();
        SaveManager.init();
        return 1;
    }

    public void changeScene(IScene scene) {
        this.actualScene_ = scene;
        myEngine_.setScene(scene);
    }

    public int getwidth() {
        return width_;
    }

    public int getHeight_() {
        return height_;
    }

    /*Pone el color que se le pase en el círculo correspondiente y su id en la
    * solución temporal para comprobarla más tarde */
    public void takeColor(int color, int id) {
        board_.putNewColor(id, color);
    }

    public IEngine getIEngine() {
        return myEngine_;
    }

    //Prepara un nivel nuevo con un array para la solucion, la dificultad necesaria y resetea la solución final
    public void setLevel(Difficulty dif) {
        this.levelDificulty_ = dif;
        this.levelSolution_ = new int[dif.getSolutionColors()];
        resetLevelSolution();
    }

    public Difficulty getLevel() {
        return this.levelDificulty_;
    }

    public int[] getLevelSolution() {
        return this.levelSolution_;
    }

    public void resetLevelSolution() {
        for (int i = 0; i < levelSolution_.length; i++) {
            this.levelSolution_[i] = -1;
        }
    }

    public void putColorSolution(int id, int color_id) {
        this.levelSolution_[id] = color_id;
    }

    public void changeDaltonicsMode() {
        this.daltonics_ = !this.daltonics_;
        this.board_.changeDaltonics(this.daltonics_);
    }

    public void setBoard_(Board b) {
        this.board_ = b;
    }

    public boolean getDaltonic(){
        return this.daltonics_;
    }
}