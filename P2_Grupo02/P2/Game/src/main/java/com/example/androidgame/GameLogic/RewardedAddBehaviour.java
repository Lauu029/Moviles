package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.RewardedAddEarned;

public class RewardedAddBehaviour implements RewardedAddEarned {
    private int numTries_;
    public RewardedAddBehaviour(int numTries){
        this.numTries_=numTries;
    }
    @Override
    public void OnRewardedEarned() {
        GameScene gs = (GameScene) SceneManager.getInstance().getScene(SceneNames.GAME.ordinal());
        gs.addTriesToBoard(numTries_);
        SceneManager.getInstance().setScene(SceneNames.GAME.ordinal());
    }
}
