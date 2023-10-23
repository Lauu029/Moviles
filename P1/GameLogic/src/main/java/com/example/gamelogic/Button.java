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
    int widht = 0, height = 0, posX = 0, posY = 0, arc = 0;

    Button(String t, IFont f, int c, int w, int h, int a, int x, int y) {
        this.text = t;
        this.font = f;
        this.color = c;
        this.widht = w;
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
        xText= this.posX+5;
        yText = this.posY+this.height/2+5;
        graph.setColor(this.color);
        graph.fillRoundRectangle(this.posX, this.posY, this.widht, this.height, this.arc);
        graph.setColor(0xFFFFFFFF);
        graph.drawText(this.text, xText, yText, this.height / 2, this.font);

    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return false;
    }
}
