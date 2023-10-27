package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class ButtonLevel implements IGameObject {
    private String text;
    private IFont font;
    private IEngine iEngine_;
    private boolean pressed_;
    private int color,actualcolor_,pressedColor_;
    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    ButtonLevel(String t, IFont f, int c, int w, int h, int a, int x, int y) {
        this.text = t;
        this.font = f;
        this.color = c;
        this.width = w;
        this.height = h;
        this.arc = a;
        this.posX = x;
        this.posY = y;
        actualcolor_=color;
        int valorARestar = 0x77;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        int ALPHA =  0xFF;
        red = Math.max(red - valorARestar, 0);
        green = Math.max(green - valorARestar, 0);
        blue = Math.max(blue - valorARestar, 0);
        pressed_=false;
        pressedColor_= ALPHA |(red << 16) | (green << 8) | blue;
        pressedColor_=0xFF000000;
    }
    void getIEngine(IEngine iengine){
        iEngine_=iengine;
    }
    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX +this.width / 2;
        yText = this.posY + this.height / 2 ;
        graph.setColor(0XFF222222);
        graph.fillRoundRectangle(this.posX-2, this.posY-2, this.width+4, this.height+4, this.arc);
        graph.setColor(this.actualcolor_);
        graph.fillRoundRectangle(this.posX, this.posY, this.width, this.height, this.arc);
        graph.setColor(0xFFFFFFFF);
        graph.setFont(this.font);
        graph.drawText(this.text, xText, yText);
    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
//        System.out.println("He llegado aqui x: " + event.x + " y: " + event.y);
//        System.out.println("mi posx: " + this.posX + " mi posy: " + this.posY+ " alto: "+ this.height);

        if (this.posX < event.x && this.posX + this.width > event.x
                && this.posY < event.y && this.posY + this.height > event.y) {
            // && this.posY < event.y && this.posY + this.height > event.y
            //  System.out.println("Boton tocado");
            actualcolor_=pressedColor_;
            GameScene game=new GameScene(iEngine_,  GameManager.getInstance().getwidth(),  GameManager.getInstance().getHeight());
            GameManager.getInstance().changeScene(game);


            return true;
        }

        return false;


    }
}
