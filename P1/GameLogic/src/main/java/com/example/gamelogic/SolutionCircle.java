package com.example.gamelogic;

import com.example.engine.TouchEvent;

public class SolutionCircle extends Circle {
    public SolutionCircle( int r, int x, int y, int row_) {
        super( r, x, y, row_);
    }

    public boolean handleInput(TouchEvent event) {
        if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
            gm.takeColor(this.color, this.id_color);
            return true;
        }
        return false;
    }
}
