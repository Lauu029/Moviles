package com.example.androidgame.GameLogic.Buttons;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Managers.GameManager;

public class ButtonColorBlind extends ButtonImage {
    private Image buttonImage_open_;

    public ButtonColorBlind(String eyeOpenImage, String eyeClosedImage, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super(eyeClosedImage ,w,h,x,y, GameManager.getInstance().getIEngine().getAudio().newSound("colorBlindButton.wav"),function);
        buttonImage_open_ = GameManager.getInstance().getIEngine().getGraphics().newImage(eyeOpenImage);
    }

    @Override
    public void render(Graphics graph) {
        if (GameManager.getInstance().getDaltonic())
            graph.drawImage(buttonImage_open_, posX_, posY_, width_, height_);
        else graph.drawImage(buttonImage_, posX_, posY_, width_, height_);
    }
}

