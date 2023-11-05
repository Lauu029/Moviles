package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.ISound;

public class ButtonLevel extends Button {
    private LevelDifficulty level_;

    ButtonLevel(String t, IFont f, int c, int w, int h, int a, int x, int y,
                SceneNames sceneNames, LevelDifficulty level, ISound buttonSound) {
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