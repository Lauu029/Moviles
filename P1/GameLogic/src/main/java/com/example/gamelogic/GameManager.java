package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IScene;

public class GameManager {
    private static GameManager instance;
    private IEngine myEngine_;
    private IScene actual_scene;
    private int width_;
    private int height_;
    private Difficulty levelDificulty;
    private Board board;
    private int[] levelSolution;
    private boolean daltonics;

    // Paso 2: Hacer el constructor privado para evitar la creación de instancias adicionales.
    private GameManager() {
        // Constructor privado
    }

    // Paso 3: Crear un método público para obtener la única instancia de la clase.
    public static GameManager getInstance() {
        // Si la instancia no ha sido creada, la creamos.
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

    // Ahora, puedes agregar métodos y propiedades a tu clase Singleton.
    // Por ejemplo:
    public void changeScene(IScene scene) {
        this.actual_scene = scene;
        myEngine_.setScene(scene);
    }

    public int getwidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }

    //para almacenar el color que ha cogido y quiere colocar en la matriz
    public void takeColor(int color, int id) {
        board.putNewColor(id, color);
    }

    public IEngine getIEngine() {
        return myEngine_;
    }

    public void setLevel(Difficulty dif) {
        this.levelDificulty = dif;
        this.levelSolution = new int[dif.solutionColors_];
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