package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class ButtonColorBlind extends ButtonImage {

    private Image buttonImage_closed_;
    private Image buttonImage_open_;

    ButtonColorBlind(String eyeOpenImage, String eyeClosedImage,int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super(eyeClosedImage ,w,h,x,y,GameManager.getInstance_().getIEngine().getAudio().newSound("colorBlindButton.wav"),function);
        buttonImage_open_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(eyeOpenImage);
    }

    @Override
    public void render(Graphics graph) {
        if (GameManager.getInstance_().getDaltonic())
            graph.drawImage(buttonImage_open_, posX_, posY_, width_, height_);
        else graph.drawImage(buttonImage_, posX_, posY_, width_, height_);
    }
}

