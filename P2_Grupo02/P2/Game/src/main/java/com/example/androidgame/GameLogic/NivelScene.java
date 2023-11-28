package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;

import java.util.ArrayList;

public class NivelScene extends Scene {
    private Font font_;
    private int niveles_=5;
    private int numberLevel_=0;
    int columnas_=3;
    ArrayList<ButtonMundo>botones_;
    @Override
    public void init() {
        Graphics graph=iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);
        int widthScene=GameManager.getInstance_().getwidth();
        int heightScene=GameManager.getInstance_().getHeight_();

        int wButton=(widthScene)/(columnas_+1);
        int margen=(widthScene-(wButton*columnas_))/(columnas_+1);
        int x=0;
        int y=0;

        for(int i=0;i<niveles_;i++){
            if(x>=columnas_){x=0;y++;}
            gameObjects_.add(new ButtonMundo(""+i,font_,0XF0FB839B,wButton,wButton,25,(x*(wButton+margen))+margen,y*(wButton+margen)+100,null,null,false));
            x++;
        }
    }
    public void render() {
        iEngine_.getGraphics().clear(0xFFfff0f6);
        this.iEngine_.getGraphics().setFont(font_);
        this.iEngine_.getGraphics().setColor(0xFF000000);
        iEngine_.getGraphics().drawText("Mundo "+numberLevel_, width_ / 2, 30);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }
}
