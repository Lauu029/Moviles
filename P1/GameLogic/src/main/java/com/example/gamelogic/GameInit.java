package com.example.gamelogic;
enum LevelDifficulty {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}
class Difficulty
{
    LevelDifficulty levelDiff_;
    boolean repeat_;
    int tries_;
    int solutionColors_;
    int posibleColors_;

    public int getPosibleColors() {
        return posibleColors_;
    }

    public int getSolutionColors() {
        return solutionColors_;
    }

    public int getTries() {
        return tries_;
    }

    public LevelDifficulty getLevelDiff() {
        return levelDiff_;
    }

    public boolean isRepeat() {
        return repeat_;
    }

    public void setLevelDiff(LevelDifficulty levelDiff_) {
        this.levelDiff_ = levelDiff_;
    }

    public void setPosibleColors(int posibleColors_) {
        this.posibleColors_ = posibleColors_;
    }

    public void setRepeat(boolean repeat_) {
        this.repeat_ = repeat_;
    }

    public void setSolutionColors(int solutionColors_) {
        this.solutionColors_ = solutionColors_;
    }

    public void setTries(int tries_) {
        this.tries_ = tries_;
    }
};
public class GameInit {
    private Difficulty level_;

    GameInit(int difficulty){
        switch(difficulty){
            case 0:
                level_.setLevelDiff(LevelDifficulty.FACIL);
                level_.setSolutionColors(4);
                level_.setPosibleColors(4);
                level_.setRepeat(false);
                level_.setTries(6);
                break;
            case 1:
                level_.setLevelDiff(LevelDifficulty.MEDIO);
                level_.setSolutionColors(4);
                level_.setPosibleColors(6);
                level_.setRepeat(false);
                level_.setTries(8);
                break;
            case 2:
                level_.setLevelDiff(LevelDifficulty.DIFICIL);
                level_.setSolutionColors(5);
                level_.setPosibleColors(8);
                level_.setRepeat(true);
                level_.setTries(10);
                break;
            case 3:
                level_.setLevelDiff(LevelDifficulty.IMPOSIBLE);
                level_.setSolutionColors(6);
                level_.setPosibleColors(9);
                level_.setRepeat(true);
                level_.setTries(10);
                break;
        }
    }
}
