package com.example.androidgame.GameLogic;

import com.example.androidgame.GameLogic.LevelDifficulty;

public class Difficulty {
    public Difficulty() {
    }

    LevelDifficulty levelDiff_;
    private boolean repeat;
    private int tries;
    private int solutionColors;
    private int posiblecolors;

    public int getPosibleColors() {
        return posiblecolors;
    }

    public int getSolutionColors() {
        return solutionColors;
    }

    public int getTries() {
        return tries;
    }

    public LevelDifficulty getLevelDiff_() {
        return levelDiff_;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setLevelDiff_(LevelDifficulty levelDiff_) {
        this.levelDiff_ = levelDiff_;
    }

    public void setPosibleColors(int posibleColors_) {
        this.posiblecolors = posibleColors_;
    }

    public void setRepeat(boolean repeat_) {
        this.repeat = repeat_;
    }

    public void setSolutionColors(int solutionColors_) {
        this.solutionColors = solutionColors_;
    }

    public void setTries(int tries_) {
        this.tries = tries_;
    }
}
