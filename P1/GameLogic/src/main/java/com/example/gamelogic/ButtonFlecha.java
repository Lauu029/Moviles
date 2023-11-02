package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.TouchEvent;

import java.io.IOException;


public class ButtonFlecha implements IGameObject {
    IImage buttonImage_;
    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    ButtonFlecha(String image, int w, int h, int x, int y) {
        this.width = w;
        this.height = h;

        this.posX = x;
        this.posY = y;
        try {
            buttonImage_ = GameManager.getInstance().getIEngine().getGraphics().newImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX;
        yText = this.posY;
        graph.drawImage(buttonImage_, xText, yText, width, height);

    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP){
            if (this.posX < event.x && this.posX + this.width > event.x
                    && this.posY < event.y && this.posY + this.height > event.y) {
                MenuScene game = new MenuScene(GameManager.getInstance().getIEngine(), GameManager.getInstance().getwidth(), GameManager.getInstance().getHeight());
                GameManager.getInstance().changeScene(game);
                return true;
            }
        }

        return false;
    }
}
