package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;

public class ButtonDaltonics implements IGameObject {

    private IImage buttonImage_closed;
    private IImage buttonImage_open;
    private ISound myButtonSound;

    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;
    boolean playedSound_=false;
    boolean canPlaySound_=true;
    double currTime_,playTime_;
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
        myButtonSound =GameManager.getInstance().getIEngine().getAudio().newSound("daltonicsButton.wav");

    }

    @Override
    public void update(double time) {

        if(playedSound_)
        {
            currTime_=System.currentTimeMillis();
            if(currTime_-playTime_>=10000) {
                GameManager.getInstance().getIEngine().getAudio().stopSound(myButtonSound);
                //playedSound_ = false;
                /*canPlaySound_=true;*/
            }
        }
    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX;
        yText = this.posY;
        if(GameManager.getInstance().getDaltonic())
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
                GameManager.getInstance().getIEngine().getAudio().playSound(myButtonSound,0);
                //if(canPlaySound_){
                    System.out.println("Puede sonar");
                    GameManager.getInstance().getIEngine().getAudio().playSound(myButtonSound,0);
                    System.out.println("HA SONDADO");
                    playTime_=System.currentTimeMillis();
                    playedSound_=true;
                    canPlaySound_=false;
                //}
                //else System.out.println("No puede sonar aun");

                return true;
            }
        }
        return false;
    }
}
