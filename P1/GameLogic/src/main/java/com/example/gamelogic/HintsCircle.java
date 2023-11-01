package com.example.gamelogic;

public class HintsCircle extends Circle {
    private boolean active;
    private int inactiveColor = 0xFF959595, rightPosition = 0xFF000000, justRightColor = 0xFFFFFFFF;

    public HintsCircle(int r, int x, int y, int row_) {
        super(r, x, y, row_);
        this.active = false;
        this.setColor(inactiveColor);
    }

    public void putHintColor(boolean correctPosition) {
        this.active = true;
        if (correctPosition)
            this.setColor(rightPosition);
        else this.setColor(justRightColor);
    }
}
