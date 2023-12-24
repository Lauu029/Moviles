package com.example.androidgame.GameLogic.Utils;

import com.example.androidengine.RewardedAddEarned;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

public class RewardedAddBehaviour implements RewardedAddEarned {

    public RewardedAddBehaviour(){

    }
    @Override
    public void OnRewardedEarned() {
        SceneManager.getInstance().setScene(SceneNames.GAME.ordinal());
    }
}
