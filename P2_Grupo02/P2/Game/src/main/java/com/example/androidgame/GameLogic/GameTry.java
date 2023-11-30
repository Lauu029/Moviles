package com.example.androidgame.GameLogic;

public class GameTry {
    private TryCircle[] tries_;
    private HintsCircle[] hints_;
    private int myTry_;

    GameTry(int solutonSize, int numTries) {
        tries_ = new TryCircle[numTries];
        hints_ = new HintsCircle[numTries];
        myTry_ = numTries;
    }

    public void TranslateY(int y) {

    }

    public void checkSolution(Solution sol) {

    }
}
