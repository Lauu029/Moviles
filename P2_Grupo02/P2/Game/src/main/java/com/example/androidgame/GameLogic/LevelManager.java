package com.example.androidgame.GameLogic;

import java.util.ArrayList;

public class LevelManager {

    // Unique instance of the class
    private static LevelManager instance;
    public ArrayList<Difficulty> diff_;
    public Theme tema_;
    public int niveles_;
    public int actualLevel_ =0;
    public int actualWorld_=0;
    // Private constructor to prevent instantiation from outside the class
    private LevelManager() {
        // Private constructor
    }
    Difficulty getNextLevelDifficulty(){
        if(actualLevel_ +1>=diff_.size())return null;
        else {
            actualLevel_+=1;
            return diff_.get(actualLevel_);
        }

    }

    // Initialize the unique instance of SceneManager
    public static void init() {
        instance = new LevelManager();
        LevelReader read_=new LevelReader();
        read_.readWorlds("world1");
        instance.niveles_=read_.getNumLevels(instance.actualWorld_);
        instance.tema_=read_.getTematicaWorld(instance.actualWorld_);
        instance.diff_ =read_.geDifficultylevels(instance.actualWorld_);

    }
    void readWorld(String world){
        instance = new LevelManager();
        LevelReader read_=new LevelReader();
        read_.readWorld(world);
        instance.niveles_=read_.getNumLevels(actualWorld_);
        instance.tema_=read_.getTematicaWorld(actualWorld_);
        instance.diff_ =read_.geDifficultylevels(actualWorld_);
    }

    // Method to get the unique instance of the class
    public static LevelManager getInstance() {
        // Returns the unique instance

        return instance;
    }


    // Method to add a scene to the stack

}
