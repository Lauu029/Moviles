package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IScene;

public class GameManager {
    private static GameManager instance;
    private IEngine myEngine_;
    private IScene actualScene;
    private int width_;
    private int height_;
    private Difficulty levelDificulty;
    private Board board;
    private int[] levelSolution;
    private boolean daltonics;

    private GameManager() {
        // Constructor privado
    }

    public static GameManager getInstance() {
        return instance;
    }


    public static int init(IEngine engine, int width, int height) {
        instance = new GameManager();
        instance.myEngine_ = engine;
        instance.width_ = width;
        instance.height_ = height;
        instance.daltonics = false;
        return 1;
    }

    public void changeScene(IScene scene) {
        this.actualScene = scene;
        myEngine_.setScene(scene);
    }

    public int getwidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }

    /*Pone el color que se le pase en el círculo correspondiente y su id en la
    * solución temporal para comprobarla más tarde */
    public void takeColor(int color, int id) {
        board.putNewColor(id, color);
    }

    public IEngine getIEngine() {
        return myEngine_;
    }

    //Prepara un nivel nuevo con un array para la solucion, la dificultad necesaria y resetea la solución final
    public void setLevel(Difficulty dif) {
        this.levelDificulty = dif;
        this.levelSolution = new int[dif.getSolutionColors()];
        resetLevelSolution();
    }

    public Difficulty getLevel() {
        return this.levelDificulty;
    }

    public int[] getLevelSolution() {
        return this.levelSolution;
    }

    public void resetLevelSolution() {
        for (int i = 0; i < levelSolution.length; i++) {
            this.levelSolution[i] = -1;
        }
    }

    public void putColorSolution(int id, int color_id) {
        this.levelSolution[id] = color_id;
    }

    public void changeDaltonicsMode() {
        this.daltonics = !this.daltonics;
        this.board.changeDaltonics(this.daltonics);
    }

    public void setBoard(Board b) {
        this.board = b;
    }

    public boolean getDaltonic(){
        return this.daltonics;
    }
}