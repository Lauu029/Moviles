package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

import java.io.IOException;

public class ButtonImage extends GameObject {
    private Image buttonImage_;
    private int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    private SceneNames name_;
    private Sound myButtonSound_;

    ButtonImage(String image, int w, int h, int x, int y, SceneNames name, Sound buttonSound) {
        this.width_ = w;
        this.height_ = h;

        this.posX_ = x;
        this.posY_ = y;
        buttonImage_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(image);

        this.name_ = name;
        myButtonSound_ = buttonSound;
    }

    public void update(double time) {
    }

    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_;
        yText = this.posY_;
        graph.drawImage(buttonImage_, xText, yText, width_, height_);
    }

    public void init() {
    }

    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                GameManager.getInstance_().getIEngine().getAudio().playSound(myButtonSound_, 0);
                IScene game = null;
                switch (name_) {
                    case GAME:
                        game = new GameScene(GameManager.getInstance_().getIEngine(), GameManager.getInstance_().getwidth(), GameManager.getInstance_().getHeight_());
                        break;
                    case MENU:
                        game = new MenuScene(GameManager.getInstance_().getIEngine(), GameManager.getInstance_().getwidth(), GameManager.getInstance_().getHeight_());
                        break;

                    case LEVEL:
                        game = new LevelScene(GameManager.getInstance_().getIEngine(), GameManager.getInstance_().getwidth(), GameManager.getInstance_().getHeight_());
                        break;


                }
                GameManager.getInstance_().changeScene(game);
                return true;
            }
        }

        return false;
    }
}
