package com.example.gamelogic;

enum LevelDifficulty {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}

class Difficulty {
    LevelDifficulty levelDiff;
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

    public LevelDifficulty getLevelDiff() {
        return levelDiff;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setLevelDiff(LevelDifficulty levelDiff_) {
        this.levelDiff = levelDiff_;
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
    private Difficulty level;

    GameInit(LevelDifficulty difficulty) {
        level = new Difficulty();
        switch (difficulty) {
            case FACIL:
                level.setLevelDiff(LevelDifficulty.FACIL);
                level.setSolutionColors(4);
                level.setPosibleColors(4);
                level.setRepeat(false);
                level.setTries(6);
                break;
            case MEDIO:
                level.setLevelDiff(LevelDifficulty.MEDIO);
                level.setSolutionColors(4);
                level.setPosibleColors(6);
                level.setRepeat(false);
                level.setTries(8);
                break;
            case DIFICIL:
                level.setLevelDiff(LevelDifficulty.DIFICIL);
                level.setSolutionColors(5);
                level.setPosibleColors(8);
                level.setRepeat(true);
                level.setTries(10);
                break;
            case IMPOSIBLE:
                level.setLevelDiff(LevelDifficulty.IMPOSIBLE);
                level.setSolutionColors(6);
                level.setPosibleColors(9);
                level.setRepeat(true);
                level.setTries(10);
                break;
        }
    }

    public Difficulty getDifficulty() {
        return level;
    }
}
