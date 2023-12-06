package com.example.androidgame.GameLogic;


import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;

public class GameManager {
    private static GameManager instance_;
    private Engine myEngine_;

    private int width_, height_;
    private Difficulty levelDificulty_;
    private Board board_;
    private int[] levelSolution_;
    private boolean daltonics_;
    private int coins_;
    private Image backgroundImage_;

    private GameManager() {
        // Constructor privado
    }

    public static GameManager getInstance() {
        return instance_;
    }


    public static int init(Engine engine, int width, int height) {
        instance_ = new GameManager();
        instance_.myEngine_ = engine;
        instance_.width_ = width;
        instance_.height_ = height;
        instance_.daltonics_ = false;
        instance_.coins_ = 100;
        instance_.backgroundImage_ = null;
        AssetsManager.init( instance_.myEngine_);
        SceneManager.init();
        LevelManager.init();
        return 1;
    }

    public int getWidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }

    /*Pone el color que se le pase en el círculo correspondiente y su id en la
     * solución temporal para comprobarla más tarde */
    public void takeColor(int color, int id) {
        board_.putNewColor(id, color);
    }

    public Engine getIEngine() {
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

    public int[] getLevelSolution_() {
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

    public boolean getDaltonic() {
        return this.daltonics_;
    }

    public int getCoins() {
        return this.coins_;
    }

    public Image getBackgroundImage() {
        return backgroundImage_;
    }

    public void setBackgroundImage(Image i) {
        backgroundImage_ = i;
    }
    public void addCoins(int amount){
        coins_+=amount;
    }
    protected void setCoins(int coins){
        coins_=coins;
    }
    public void saveGameData() {
        Log.d("GAME","-----llamado a save gameData-------");
        SaveData.saveGameData(myEngine_.getMainActivity(), coins_,
                AssetsManager.getInstance().getPaletteColor(),
                LevelManager.getInstance().getPassedWorld(),
                LevelManager.getInstance().getPassedLevel());
    }

    // Llamado al iniciar el juego para cargar el progreso del jugador
    public void loadGameData() {
        Log.d("GAME","-----llamado a load gameData-------");
        SaveData.loadGameData(myEngine_.getMainActivity());
        // Actualiza las variables del GameManager según los datos cargados
        coins_ = getCoins();

    }
}