package com.example.gamelogic;
public class GameInit {
    private Difficulty level_;

    public GameInit(LevelDifficulty difficulty) {
        level_ = new Difficulty();
        switch (difficulty) {
            case FACIL:
                level_.setSolutionColors(4);
                level_.setPosibleColors(4);
                level_.setRepeat(false);
                level_.setTries(6);
                break;
            case MEDIO:

                level_.setSolutionColors(4);
                level_.setPosibleColors(6);
                level_.setRepeat(false);
                level_.setTries(8);
                break;
            case DIFICIL:

                level_.setSolutionColors(5);
                level_.setPosibleColors(8);
                level_.setRepeat(true);
                level_.setTries(10);
                break;
            case IMPOSIBLE:

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
