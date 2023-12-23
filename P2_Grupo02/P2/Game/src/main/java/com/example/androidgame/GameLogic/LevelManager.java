package com.example.androidgame.GameLogic;

import java.util.ArrayList;

public class LevelManager {

    // Unique instance of the class
    private LevelReader read_ = new LevelReader();
    private static LevelManager instance;
    private ArrayList<Difficulty> diff_;
    private Theme tema_;
    private int niveles_;
    private int actualLevel_ = 0;
    private int actualWorld_ = 0;
    private int passedLevel_ = 4;
    private int passedWorld_ = 1;
    private int numWorlds;

    public int getPassedLevel() {
        return passedLevel_;
    }

    public void setPassedLevel(int passedLevel) {
        passedLevel_ = passedLevel;
    }

    public void setPassedWorld(int passedWorld) {
        passedWorld_ = passedWorld;
    }

    public void nextPassedLevel() {
        if (passedLevel_ + 1 >= diff_.size()) {
            if (passedWorld_ < numWorlds) {
                passedWorld_++;
                passedLevel_ = 0;
            }
        } else passedLevel_++;
    }

    public int getPassedWorld() {
        return passedWorld_;
    }

    public ArrayList<Difficulty> getDiff() {
        return diff_;
    }

    public int getNumWorlds() {
        return numWorlds;
    }

    // Getter y Setter para tema_
    public Theme getTema() {
        return tema_;
    }

    public void setTema(Theme tema) {
        this.tema_ = tema;
    }

    // Getter y Setter para niveles_
    public int getNiveles() {
        return niveles_;
    }

    public void setNiveles(int niveles) {
        this.niveles_ = niveles;
    }

    // Getter y Setter para actualLevel_
    public int getActualLevel() {
        return actualLevel_;
    }

    public void setActualLevel(int actualLevel) {
        this.actualLevel_ = actualLevel;
    }

    // Getter y Setter para actualWorld_
    public int getActualWorld() {
        return actualWorld_;
    }

    public void setActualWorld(int actualWorld) {
        this.actualWorld_ = actualWorld;
    }

    // Private constructor to prevent instantiation from outside the class
    private LevelManager() {
        // Private constructor
    }

    public Difficulty getNextLevelDifficulty() {
        if (actualLevel_ + 1 >= diff_.size()) return null;
        else {
            actualLevel_ += 1;
            return diff_.get(actualLevel_);
        }

    }

    // Initialize the unique instance of SceneManager
    public static void init() {
        instance = new LevelManager();

        instance.read_.readWorlds("world1");
        instance.niveles_ = instance.read_.getNumLevels(instance.actualWorld_);
        instance.tema_ = instance.read_.getTematicaWorld(instance.actualWorld_);
        instance.diff_ = instance.read_.geDifficultylevels(instance.actualWorld_);
        instance.numWorlds = instance.read_.getNumWorlds_();
    }

    public void setNewWorld() {

        instance.niveles_ = read_.getNumLevels(actualWorld_);
        instance.tema_ = read_.getTematicaWorld(actualWorld_);
        instance.diff_ = read_.geDifficultylevels(actualWorld_);
    }

    // Method to get the unique instance of the class
    public static LevelManager getInstance() {
        // Returns the unique instance

        return instance;
    }


    // Method to add a scene to the stack

}
