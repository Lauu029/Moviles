package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

import java.io.IOException;

public class ButtonImage extends Button {
    protected Image buttonImage_;

    ButtonImage(String image, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super("", null, 0, w, h, 0, x, y, buttonSound, function);
        buttonImage_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(image);
    }

    void init() {
    }

    @Override
    public void render(Graphics graph) {
        graph.drawImage(buttonImage_, this.posX_, this.posY_, width_, height_);
    }
}
