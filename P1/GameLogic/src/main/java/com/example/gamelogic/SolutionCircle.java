package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class SolutionCircle extends Circle {

    public SolutionCircle(String t, IFont f, int r, int x, int y, int row_) {
        super(t,f, r, x, y, row_);
    }

    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP)
        if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
            gm.takeColor(this.color, this.id_color);
            return true;
        }
        return false;
    }
    public void render(IGraphics graph) {
        super.render(graph);
        if (this.isDaltonics) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font);
            graph.drawText(this.text, this.posX, this.posY);
        }
    }
}
