package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.io.IOException;


public class ButtonImage implements IGameObject {
    private IImage buttonImage_;
    private int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    private SceneNames name_;

    ButtonImage(String image, int w, int h, int x, int y, SceneNames name) {
        this.width_ = w;
        this.height_ = h;

        this.posX_ = x;
        this.posY_ = y;
        try {
            buttonImage_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name_ = name;
    }

    @Override
    public void update(double time) {
    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX_;
        yText = this.posY_;
        graph.drawImage(buttonImage_, xText, yText, width_, height_);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
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
