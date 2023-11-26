package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

import java.io.IOException;

public class ButtonDaltonics extends GameObject {

    private Image buttonImage_closed_;
    private Image buttonImage_open_;
    private Sound myButtonSound_;
    private int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;

    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    ButtonDaltonics(int w, int h, int x, int y) {
        this.width_ = w;
        this.height_ = h;

        this.posX_ = x;
        this.posY_ = y;
        buttonImage_closed_ = GameManager.getInstance_().getIEngine().getGraphics().newImage("eye_closed.png");
        buttonImage_open_ = GameManager.getInstance_().getIEngine().getGraphics().newImage("eye_open.png");
        myButtonSound_ = GameManager.getInstance_().getIEngine().getAudio().newSound("daltonicsButton.wav");

    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_;
        yText = this.posY_;
        if (GameManager.getInstance_().getDaltonic())
            graph.drawImage(buttonImage_open_, xText, yText, width_, height_);
        else graph.drawImage(buttonImage_closed_, xText, yText, width_, height_);
    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {

        if (event.type == TouchEvent.TouchEventType.TOUCH_UP) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                GameManager.getInstance_().changeDaltonicsMode();
                GameManager.getInstance_().getIEngine().getAudio().playSound(myButtonSound_, 0);
                return true;
            }

        }
        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                GameManager.getInstance_().getIEngine().getAudio().stopSound(myButtonSound_);
                return true;
            }

        }
        return false;
    }
}

