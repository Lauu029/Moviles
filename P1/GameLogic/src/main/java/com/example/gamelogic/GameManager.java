package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IScene;

public class GameManager {
    private static GameManager instance;
    private IEngine myEngine_;
    private int width_;
    private int height_;
    private int selectedColor;
    private boolean hasSelectedColor;
    private Difficulty levelDificulty;
    private int temporal_id;
    private int[] levelSolution;

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
        instance.temporal_id = -1;
        instance.selectedColor = 0;
        instance.hasSelectedColor = false;
        return 1;
    }

    // Ahora, puedes agregar métodos y propiedades a tu clase Singleton.
    // Por ejemplo:
    public void changeScene(IScene scene) {
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
        this.hasSelectedColor = true;
        this.selectedColor = color;
        this.temporal_id = id;
    }

    public boolean colorSelected() {
        return this.hasSelectedColor;
    }

    public IEngine getIEngine() {
        return myEngine_;
    }

    public int getSelectedColor() {
        this.hasSelectedColor = false;
        return this.selectedColor;
    }

    public int getTemporalId() {
        return this.temporal_id;
    }

    public void resetTemporalId() {
        this.temporal_id = -1;
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
        System.out.print("Poniendo color " + color_id + "en la posicion" + id + "\n");
        this.levelSolution[id] = color_id;
        for (int i = 0; i < levelSolution.length; i++) {
            System.out.print(this.levelSolution[i] + " ");
        }
        System.out.println();
    }
}