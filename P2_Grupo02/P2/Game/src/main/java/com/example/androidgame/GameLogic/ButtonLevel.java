package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Sound;

public class ButtonLevel extends Button {
    private LevelDifficulty level_;

    ButtonLevel(String t, Font f, int c, int w, int h, int a, int x, int y,
                SceneNames sceneNames, LevelDifficulty level, Sound buttonSound) {
        super(t, f, c, w, h, a, x, y, sceneNames,buttonSound);
        this.level_ = level;
    }
    @Override
    void actionButton() {
        GameScene gameScene = (GameScene) scene_;
        GameInit gameInit = new GameInit(level_);
        GameManager.getInstance_().setLevel(gameInit.getDifficulty());
    }
}
