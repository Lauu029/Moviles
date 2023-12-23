package com.example.androidgame.GameLogic.Buttons;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.AssetsManager;
import com.example.androidgame.GameLogic.GameManager;

public class ButtonImage extends Button {
    protected Image buttonImage_;

    public ButtonImage(String image, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super("", null, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(), w, h, 0, x, y, buttonSound, function);
        buttonImage_ = GameManager.getInstance().getIEngine().getGraphics().newImage(image);
    }

    public void init() {
    }

    @Override
    public void render(Graphics graph) {
        if (active) {
            graph.drawImage(buttonImage_, this.posX_, this.posY_, width_, height_);
        }
    }


    public boolean handleInput(TouchEvent event) {
        boolean input = super.handleInput(event);
        return input;
    }

}