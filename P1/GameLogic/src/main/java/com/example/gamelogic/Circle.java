package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Circle implements IGameObject {
    boolean is_selectionable, hasColor;
    int color, radius;
    int width, height;
    int posX, posY;
    int id;
    GameManager gm;

    public Circle(boolean sel, int r, int x, int y) {
        this.is_selectionable = sel;
        this.color = 0Xff808080;
        this.posX = x;
        this.posY = y;
        this.radius = r;
        this.width = 2 * radius;
        this.height = 2 * radius;
        this.hasColor = false;
        gm = GameManager.getInstance();
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
        graph.setColor(0xFFFF0000);
        graph.drawRectangle(this.posX- this.radius,this.posY-this.radius,this.width,this.height);
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if (this.posX- this.radius < event.x && this.posX + this.radius > event.x
                && this.posY-this.radius < event.y && this.posY + this.radius > event.y) {
            System.out.println("Circulo " + id + " X del circulo: " + this.posX + " Y del circulo: " + this.posY + "\n" + "X del raton: " + event.x + " Y del raton: " + event.y + "\n");
            if (this.is_selectionable) {
                gm.takeColor(this.color);
            } else if (gm.colorSelected()) {
                this.color = gm.getSelectedColor();
                this.hasColor = true;
            }
            return true;
        }
        return false;
    }
}