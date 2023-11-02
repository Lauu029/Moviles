package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;

public class HintsCircle extends Circle {
    private boolean active;
    private int inactiveColor = 0xFF718280, rightPosition = 0xFF000000, justRightColor = 0xFFFFFFFF;

    public HintsCircle(String t, IFont f,int r, int x, int y, int row_) {
        super(t,f,r, x, y, row_);
        this.active = false;
        this.setColor(inactiveColor);
    }

    public void putHintColor(boolean correctPosition) {
        this.active = true;
        if (correctPosition)
            this.setColor(rightPosition);
        else this.setColor(justRightColor);
    }

    public void render(IGraphics graph) {
        super.render(graph);
        if(this.color== justRightColor){
            graph.setColor(0xFF000000);
            graph.drawRoundRectangle(this.posX-this.radius,this.posY-this.radius,this.radius*2, this.radius*2,2*this.radius);
        }
    }
}
