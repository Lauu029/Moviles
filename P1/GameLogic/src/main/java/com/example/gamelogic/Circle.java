package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Circle implements IGameObject {
    int color;
    int radius;
    int posX, posY;

    public Circle( int rad, int x, int y) {
        this.color = 0xFF808080;
        this.radius = rad;
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        graph.setColor(this.color);
        graph.drawCircle(posX, posY, radius);
    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return false;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
