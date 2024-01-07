package com.example.gamelogic;

public class Difficulty {

    private boolean repeat;
    private int tries;
    private int solutionColors;
    private int posiblecolors;
    public Difficulty() {}
    public int getPosibleColors() {
        return posiblecolors;
    }

    public int getSolutionColors() {
        return solutionColors;
    }

    public int getTries() {
        return tries;
    }


    public boolean isRepeat() {
        return repeat;
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
