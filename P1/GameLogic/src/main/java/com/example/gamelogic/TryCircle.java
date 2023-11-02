package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class TryCircle extends Circle {
    private int id_row;
    boolean hasColor;

    public TryCircle(String t, IFont f,int r, int x, int y, int row_, int id) {
        super(t,f,r, x, y, row_);
        this.id_row = id;
        this.hasColor = false;
    }

    public void render(IGraphics graph) {
        super.render(graph);
        if (!this.hasColor) {
            graph.setColor(0Xff332F2C);
            graph.drawCircle(this.posX, this.posY, this.radius / 3);
        } else if (this.isDaltonics) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font);
            graph.drawText(this.text, this.posX, this.posY);
        }
    }

    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP)
        if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
            if (gm.colorSelected() && this.row == this.game_try) {
                this.color = gm.getSelectedColor();
                this.id_color = gm.getTemporalId();
                this.text= Integer.toString(id_color);
                gm.resetTemporalId();
                this.hasColor = true;
                gm.putColorSolution(this.id_row, this.id_color);
            }
            return true;
        }
        return false;
    }
}
