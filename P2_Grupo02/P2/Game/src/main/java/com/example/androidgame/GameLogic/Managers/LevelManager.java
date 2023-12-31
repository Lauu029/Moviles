package com.example.androidgame.GameLogic.Managers;

import android.util.Log;

import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Utils.LevelReader;
import com.example.androidgame.GameLogic.Theme;

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
    private int passedLevel_ = 0;
    private int passedWorld_ = 0;
    private int numWorlds;
    private int savedWorld_;
    private  int savedLevel_;
    private int savedTotalTries_ = 0;
    private ArrayList<Integer> currentSolution_;
   private ArrayList<ArrayList<Integer>>tries_;

    public void setSavedWorld(int savedWorld){ savedWorld_=savedWorld;}
    public void setSavedLevel(int savedLevel){ savedLevel_=savedLevel;}
    public int getSavedWorld(){return savedWorld_;}
    public int getSavedLevel(){return savedLevel_;}
    public int getPassedLevel() {
        return passedLevel_;
    }
    public ArrayList<Integer> getCurrentSolution(){
        return currentSolution_;
    }
    public void setCurrentSolution(ArrayList<Integer> savedSolution){
        currentSolution_=savedSolution;
    }
    public void setPassedLevel(int passedLevel) {
        passedLevel_ = passedLevel;
    }
    public void addTries(ArrayList<Integer> trie){
        tries_.add(trie);
    }
    public void setTries(ArrayList<ArrayList<Integer>>savedTries){
        tries_=savedTries;
    }
    public ArrayList<ArrayList<Integer>> getTries(){
        return tries_;
    }
    public int getTotalTries(){return savedTotalTries_;}
    public void setTotalTries(int tries){
        savedTotalTries_ =tries;
    }
    public void clearTries(){
        tries_.clear();
    }
    public void setPassedWorld(int passedWorld) {
        passedWorld_ = passedWorld;
    }

    //Desbloquea los niveles de los mundos
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

    private LevelManager() {
    }

    public Difficulty getNextLevelDifficulty() {
        if (actualLevel_ + 1 >= diff_.size()) return null;
        else {
            actualLevel_ += 1;
            tries_.clear();
            return diff_.get(actualLevel_);
        }
    }

    public static void init() {
        instance = new LevelManager();
        instance.tries_=new ArrayList<>();
        instance.currentSolution_= new ArrayList<>();
        instance.read_.readWorlds("world1");
        instance.niveles_ = instance.read_.getNumLevels(instance.actualWorld_);
        instance.tema_ = instance.read_.getTematicaWorld(instance.actualWorld_);
        instance.diff_ = instance.read_.geDifficultylevels(instance.actualWorld_);
        instance.numWorlds = instance.read_.getNumWorlds_();

    }
    //Crea un mundo de niveles nuevos
    public void setNewWorld() {
        instance.niveles_ = read_.getNumLevels(actualWorld_);
        instance.tema_ = read_.getTematicaWorld(actualWorld_);
        instance.diff_ = read_.geDifficultylevels(actualWorld_);
    }

    public static LevelManager getInstance() {
        return instance;
    }

}
