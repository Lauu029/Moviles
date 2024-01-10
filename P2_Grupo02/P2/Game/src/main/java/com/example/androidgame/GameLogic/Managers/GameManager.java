package com.example.androidgame.GameLogic.Managers;


import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Solution;
import com.example.androidgame.GameLogic.Utils.SaveData;

public class GameManager {
    private static GameManager instance_;
    private Engine myEngine_;
private Sound cancion_;
    private int width_, height_;
    private Difficulty levelDificulty_;
    private Board board_;
    private int[] levelSolution_;
    private boolean daltonics_;
    private int coins_;
    private Image backgroundImage_;
    private Solution sol_;
    private double record_ = 0;

    private GameManager() {
        // Constructor privado
    }

    public static GameManager getInstance() {
        return instance_;
    }

    public void setSolution(Solution s) {
        sol_ = s;
    }

    public static int init(Engine engine, int width, int height) {
        instance_ = new GameManager();
        instance_.myEngine_ = engine;
        instance_.width_ = width;
        instance_.height_ = height;
        instance_.daltonics_ = false;
        instance_.coins_ = 100;
        instance_.backgroundImage_ = null;
        instance_.cancion_= instance_.myEngine_.getAudio().newSound("LasDivinas.wav");
        instance_.myEngine_.getAudio().playSound(instance_.cancion_,0);
        AssetsManager.init(instance_.myEngine_);
        SceneManager.init();
        LevelManager.init();
        ShopManager.init();

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

    public void setBoard(Board b) {
        this.board_ = b;
    }

    public boolean getDaltonic() {
        return this.daltonics_;
    }

    public int getCoins() {
        return this.coins_;
    }

    public void addCoins(int amount) {
        coins_ += amount;
    }

    public void setCoins(int coins) {
        coins_ = coins;
    }

    //Guarda el estado actual de la partida
    public void saveGameData() {
        SaveData.saveGameData(coins_,
                AssetsManager.getInstance().getPaletteColor(),
                LevelManager.getInstance().getPassedWorld(),
                LevelManager.getInstance().getPassedLevel(),
                AssetsManager.getInstance().getBackgroundPath(),
                AssetsManager.getInstance().getCirleTheme(false).getPathBolas(),
                SceneManager.getInstance().getIdActualScene(),
                GameManager.getInstance().getLevel());
    }

    // Llamado al iniciar el juego para cargar el progreso del jugador
    public void loadGameData() {
        SaveData.loadGameData();
        // Actualiza las variables del GameManager según los datos cargados
        coins_ = getCoins();
    }

    public void activateCheating() {
        int[] realSolution = sol_.getSol();
        for (int i = 0; i < realSolution.length; i++) {
            board_.forcePutColor(i, realSolution[i]);
        }
        sol_.check(realSolution);
        board_.nexTry();
    }
    void setNewRecord(double time){
        record_=time;
    }
}