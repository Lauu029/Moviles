package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Circle implements IGameObject {
    protected String text;
    private IFont font;
    int color, radius;
    int width, height;
    int posX, posY;
    int id_color;
    int row;
    int game_try;
    boolean isDaltonics;
    GameManager gm;

    public Circle(String t, IFont f, int r, int x, int y, int row_) {
        this.font = f;
        this.text = t;
        this.color = 0Xff808080;
        this.posX = x;
        this.posY = y;
        this.radius = r;
        this.width = 2 * radius;
        this.height = 2 * radius;
        this.row = row_;
        this.isDaltonics = false;
        gm = GameManager.getInstance();
    }

    public void setColor(int color) {
        this.color = color;
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
        //Para debug
//        graph.setColor(0xFFFF0000);
//        graph.drawRectangle(this.posX-this.radius,this.posY-this.radius,this.radius*2, this.radius*2);
        graph.setColor(this.color);
        graph.drawCircle(this.posX, this.posY, this.radius);
        if (isDaltonics) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font);
            graph.drawText(this.text, this.posX, this.posY);
        }
    }

    @Override
    public void init() {
    }

    public void setIdColor(int id) {
        this.id_color = id;
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return true;
    }

    public void setGameTry(int t) {
        this.game_try = t;
    }
}