package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Circle implements IGameObject {
    boolean is_selectionable, hasColor;
    int color, radius;
    int posX, posY;
    int id;
    GameManager gm;

    public Circle(boolean sel, int r, int x, int y) {
        this.is_selectionable = sel;
        this.color = 0Xff808080;
        this.posX = x;
        this.posY = y;
        this.radius = r;
        this.hasColor = false;
        gm=GameManager.getInstance();
    }

    public void setColor(int color) {
        this.color = color;
        this.hasColor = true;
    }

    public void setPositions(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        graph.setColor(this.color);
        graph.drawCircle(this.posX, this.posY, this.radius);
        if (!this.hasColor) {
            graph.setColor(0Xff332F2C);
            graph.drawCircle(this.posX, this.posY, this.radius / 3);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if (this.posX < event.x && this.posX + this.radius * 2 > event.x
                && this.posY < event.y && this.posY + this.radius * 2 > event.y) {
            System.out.println("Me tocarooon\n");
            if (this.is_selectionable) {
                gm.takeColor(this.color);
            } else if (gm.colorSelected()) {
                this.color = gm.getSelectedColor();
                this.hasColor=true;
            }
            return true;
        }
        return false;
    }
}