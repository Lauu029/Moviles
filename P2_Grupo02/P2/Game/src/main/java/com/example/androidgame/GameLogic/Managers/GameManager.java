package com.example.androidgame.GameLogic.Managers;


import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;
import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Utils.SaveData;

public class GameManager {
    private static GameManager instance_;
    private Engine myEngine_;

    private int width_, height_;
    private Difficulty levelDificulty_;
    private Board board_;
    private int [] levelSolution_;
    private boolean daltonics_;
    private int coins_;
    private Image backgroundImage_;
    private boolean contrarreloj_;
    private int pasadosContrarreloj_;
    private int nivelesContrarreloj_;
    private float minutosContrarreloj_;
    private float timeLeft_;
    private float totalTimePassed_;
    private float bestTimeInSecs_;
    private boolean shortcut_;
    int currentLives_;
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
        instance_.contrarreloj_=false;
        instance_.pasadosContrarreloj_=0;
        instance_.nivelesContrarreloj_=4;
        instance_.minutosContrarreloj_=3;
        instance_.totalTimePassed_=0;
        instance_.timeLeft_= instance_.minutosContrarreloj_*60;
        instance_.bestTimeInSecs_= instance_.timeLeft_;
        instance_.currentLives_=5;
        instance_.shortcut_=false;
        AssetsManager.init( instance_.myEngine_);
        SceneManager.init();
        LevelManager.init();
        ShopManager.init();
        return 1;
    }
    public void resetCurrentLives(){currentLives_=5;}
    public int getCurrentLives(){return currentLives_;}
    public void lostLife(){
        if(currentLives_-1>=0)
            currentLives_--;}
    public void gotLife(){currentLives_++;}
    public void setShortcut(boolean s){shortcut_=s;}
    public boolean getShortcut(){return shortcut_;}
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
    public void addCoins(int amount){
        coins_+=amount;
    }
    public void setCoins(int coins){
        coins_=coins;
    }

    //Guarda el estado actual de la partida
    public void saveGameData() {
        SaveData.saveGameData(coins_,
                AssetsManager.getInstance().getPaletteColor(),
                LevelManager.getInstance().getPassedWorld(),
                LevelManager.getInstance().getPassedLevel(),
                AssetsManager.getInstance().getBackgroundPath(),
                AssetsManager.getInstance().getCirleTheme(false).getPathBolas());
    }

    // Llamado al iniciar el juego para cargar el progreso del jugador
    public void loadGameData() {
        SaveData.loadGameData();
        // Actualiza las variables del GameManager según los datos cargados
        coins_ = getCoins();
    }
    public void setContrarreloj(boolean active){contrarreloj_=active;}
    public boolean getContrarreloj(){return contrarreloj_;}
    public int getPasadosContrarreloj(){return pasadosContrarreloj_;}
    public void passedContrarreloj(){pasadosContrarreloj_++;}
    public int getNivelesContrarreloj(){ return  nivelesContrarreloj_;}
    public void setTimePassedInLevel(float t){totalTimePassed_=t;}
    public float getTimeLeft(){
        timeLeft_-=totalTimePassed_;
        return timeLeft_;
    }
    public void setBestTimeInSecs(float best){
        if(best<bestTimeInSecs_)
            bestTimeInSecs_=best;
    }
    public float getBestTimeInSecs(){return bestTimeInSecs_;}

    public float getContrarrelojDuration(){return minutosContrarreloj_*60;}
    public void reset()
    {
        pasadosContrarreloj_=0;
        nivelesContrarreloj_=4;
        minutosContrarreloj_=3;
        totalTimePassed_=0;
        timeLeft_= minutosContrarreloj_*60;
    }
}