package com.example.gamelogic;

import com.example.engine.IFont;

public class ButtonDaltonics extends Button{
    ButtonDaltonics(String t, IFont f, int c, int w, int h, int a, int x, int y, SceneNames sceneNames) {
        super(t, f, c, w, h, a, x, y, sceneNames);
    }
    void actionButton() {
        GameManager.getInstance().changeDaltonicsMode();
    }
}
