package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
/*
 * Clase correspondiente a los círculos de pistas, extiende la clase Círculo que es un Game Object
 * tiene almacenados los posibles colores que puede tomar y se encarga de activar el color del círculo
 * cuando corresponde y de dibujar outline a los círculos blancos.*/
public class HintsCircle extends Circle {
    private boolean active_;
    private int inactiveColor = 0xFF947c86, rightPosition = 0xFF000000, justRightColor = 0xFFFFFFFF;

    public HintsCircle(String t, Font f, int r, int x, int y, int row_) {
        super(t, f, r, x, y, row_,false);
        this.active_ = false;
        this.setColor_(inactiveColor);
    }

    public void putHintColor(boolean correctPosition) {
        this.active_ = true;
        if (correctPosition)
            this.setColor_(rightPosition);
        else this.setColor_(justRightColor);
    }

    public void render(Graphics graph) {
        graph.setColor(this.color_);
        graph.drawCircle(this.posX_, this.posY_, this.radius_);
        if (this.color_ == justRightColor) {
            graph.setColor(0xFF000000);
            graph.drawRoundRectangle(this.posX_ - this.radius_, this.posY_ - this.radius_,
                    this.radius_ * 2, this.radius_ * 2, 2 * this.radius_);
        }
    }
}
