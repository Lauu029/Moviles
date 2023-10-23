package com.example.gamelogic;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Button implements IGameObject {
    String text;
    IFont font;
    int color;
    int width = 0, height = 0, posX = 0, posY = 0, arc = 0;

    Button(String t, IFont f, int c, int w, int h, int a, int x, int y) {
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
        xText = this.posX + 5;
        yText = this.posY + this.height / 2 + 5;
        graph.setColor(this.color);
        graph.fillRoundRectangle(this.posX, this.posY, this.width, this.height, this.arc);
        graph.setColor(0xFF000000);
        graph.drawText(this.text, xText, yText, this.height / 2, this.font);

    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        System.out.println("He llegado aqui x: " + event.x + " y: " + event.y);
        System.out.println("mi posx: " + this.posX + " mi posy: " + this.posY+ " alto: "+ this.height);

        if (this.posX < event.x && this.posX + this.width > event.x
                && this.posY < event.y && this.posY + this.height > event.y) {
            // && this.posY < event.y && this.posY + this.height > event.y
            System.out.println("Boton tocado");
            return true;
        }

        return false;


    }
}
