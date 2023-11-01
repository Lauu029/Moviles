package com.example.gamelogic;

import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class TryCircle extends Circle {
    private int id_row;
    boolean hasColor;

    public TryCircle(int r, int x, int y, int row_, int id) {
        super(r, x, y, row_);
        this.id_row = id;
        this.hasColor = false;
    }

    public void render(IGraphics graph) {
        super.render(graph);
        if (!this.hasColor) {
            graph.setColor(0Xff332F2C);
            graph.drawCircle(this.posX, this.posY, this.radius / 3);
        }
    }

    public boolean handleInput(TouchEvent event) {
        if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
            if (gm.colorSelected() && this.row == this.game_try) {
                this.color = gm.getSelectedColor();
                this.id_color = gm.getTemporalId();
                gm.resetTemporalId();
                this.hasColor = true;
                System.out.print("Color " + id_color + " en la fila\n");
                gm.putColorSolution(this.id_row, this.id_color);
            }
            return true;
        }
        return false;
    }
}
