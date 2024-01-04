package com.example.androidgame.GameLogic.Utils;

import com.example.androidengine.RewardedAddEarned;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

public class RewardedAddBehaviour implements RewardedAddEarned {
    private  boolean world_;
    public RewardedAddBehaviour(boolean world){
        world_=world;
    }
    @Override
    public void OnRewardedEarned() {
        if(!world_)
        SceneManager.getInstance().setScene(SceneNames.GAME.ordinal());
        else SceneManager.getInstance().setScene(SceneNames.WORLD_SCENE.ordinal());
    }
}
