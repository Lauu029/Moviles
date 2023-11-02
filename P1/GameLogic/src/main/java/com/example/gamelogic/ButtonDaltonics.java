package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.io.IOException;

public class ButtonDaltonics implements IGameObject {
    IImage buttonImage_closed;
    IImage buttonImage_open;

    boolean daltonic_=false;
    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    ButtonDaltonics( int w, int h, int x, int y) {
        this.width = w;
        this.height = h;

        this.posX = x;
        this.posY = y;
        try {
            buttonImage_closed = GameManager.getInstance().getIEngine().getGraphics().newImage("eye_closed.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            buttonImage_open = GameManager.getInstance().getIEngine().getGraphics().newImage("eye_open.png");
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
        if(daltonic_)
        graph.drawImage(buttonImage_open, xText, yText, width, height);
        else  graph.drawImage(buttonImage_closed, xText, yText, width, height);

    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP){
            if (this.posX < event.x && this.posX + this.width > event.x
                    && this.posY < event.y && this.posY + this.height > event.y) {
                GameManager.getInstance().changeDaltonicsMode();
                daltonic_=!daltonic_;

                return true;
            }
        }

        return false;
    }
}
