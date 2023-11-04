package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;

public class ButtonDaltonics implements IGameObject {

    private IImage buttonImage_closed_;
    private IImage buttonImage_open_;
    private ISound myButtonSound_;
    private int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;

    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    ButtonDaltonics(int w, int h, int x, int y) {
        this.width_ = w;
        this.height_ = h;

        this.posX_ = x;
        this.posY_ = y;
        try {
            buttonImage_closed_ = GameManager.getInstance_().getIEngine().getGraphics().newImage("eye_closed.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            buttonImage_open_ = GameManager.getInstance_().getIEngine().getGraphics().newImage("eye_open.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myButtonSound_ = GameManager.getInstance_().getIEngine().getAudio().newSound("daltonicsButton.wav");

    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
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

