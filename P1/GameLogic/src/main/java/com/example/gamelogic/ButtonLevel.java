package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class ButtonLevel implements IGameObject {
    private String text;
    private IFont font;


    private int color;
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
        graph.setColor(this.color);
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

            GameScene game=new GameScene(GameManager.getInstance().getIEngine(),  GameManager.getInstance().getwidth(),  GameManager.getInstance().getHeight());
            GameManager.getInstance().changeScene(game);


            return true;
        }

        return false;


    }
}
