package com.example.gamelogic;

enum LevelDifficulty {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}

class Difficulty {
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
};

public class GameInit {
    private Difficulty level_;

    GameInit(LevelDifficulty difficulty) {
        level_ = new Difficulty();
        switch (difficulty) {
            case FACIL:
                level_.setLevelDiff_(LevelDifficulty.FACIL);
                level_.setSolutionColors(4);
                level_.setPosibleColors(4);
                level_.setRepeat(false);
                level_.setTries(6);
                break;
            case MEDIO:
                level_.setLevelDiff_(LevelDifficulty.MEDIO);
                level_.setSolutionColors(4);
                level_.setPosibleColors(6);
                level_.setRepeat(false);
                level_.setTries(8);
                break;
            case DIFICIL:
                level_.setLevelDiff_(LevelDifficulty.DIFICIL);
                level_.setSolutionColors(5);
                level_.setPosibleColors(8);
                level_.setRepeat(true);
                level_.setTries(10);
                break;
            case IMPOSIBLE:
                level_.setLevelDiff_(LevelDifficulty.IMPOSIBLE);
                level_.setSolutionColors(6);
                level_.setPosibleColors(9);
                level_.setRepeat(true);
                level_.setTries(10);
                break;
        }
    }

    public Difficulty getDifficulty() {
        return level_;
    }
}
