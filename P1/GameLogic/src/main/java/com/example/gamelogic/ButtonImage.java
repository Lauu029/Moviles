package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.io.IOException;


public class ButtonImage implements IGameObject {
    private IImage buttonImage;
    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;
    private SceneNames name;

    ButtonImage(String image, int w, int h, int x, int y, SceneNames name) {
        this.width = w;
        this.height = h;

        this.posX = x;
        this.posY = y;
        try {
            buttonImage = GameManager.getInstance().getIEngine().getGraphics().newImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
    }

    @Override
    public void update(double time) {
    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX;
        yText = this.posY;
        graph.drawImage(buttonImage, xText, yText, width, height);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP) {
            if (this.posX < event.x && this.posX + this.width > event.x
                    && this.posY < event.y && this.posY + this.height > event.y) {
                IScene game = null;
                switch (name) {
                    case GAME:
                        game = new GameScene(GameManager.getInstance().getIEngine(), GameManager.getInstance().getwidth(), GameManager.getInstance().getHeight());
                        break;
                    case MENU:
                        game = new MenuScene(GameManager.getInstance().getIEngine(), GameManager.getInstance().getwidth(), GameManager.getInstance().getHeight());
                        break;

                    case LEVEL:
                        game = new LevelScene(GameManager.getInstance().getIEngine(), GameManager.getInstance().getwidth(), GameManager.getInstance().getHeight());
                        break;


                }
                GameManager.getInstance().changeScene(game);
                return true;
            }
        }

        return false;
    }
}
