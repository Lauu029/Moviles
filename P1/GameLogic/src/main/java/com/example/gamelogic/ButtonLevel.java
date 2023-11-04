package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.ISound;

public class ButtonLevel extends Button {
    private LevelDifficulty level;

    ButtonLevel(String t, IFont f, int c, int w, int h, int a, int x, int y,
                SceneNames sceneNames, LevelDifficulty level, ISound buttonSound) {
        super(t, f, c, w, h, a, x, y, sceneNames,buttonSound);
        this.level = level;
    }
    @Override
    void actionButton() {
        GameScene gameScene = (GameScene) scene;
        GameInit gameInit = new GameInit(level);
        GameManager.getInstance().setLevel(gameInit.getDifficulty());
    }
}
