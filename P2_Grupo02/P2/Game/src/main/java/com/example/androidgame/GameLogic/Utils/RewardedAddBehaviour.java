package com.example.androidgame.GameLogic.Utils;

import com.example.androidengine.RewardedAddEarned;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

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
