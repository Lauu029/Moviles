package com.example.desktopengine;

import com.example.engine.IColor;

public class ColorDesktop implements IColor {
    private int color_;
    @Override
    public void setColor(int color) {
        this.color_ = color;
    }

    @Override
    public int getColor() {
        return color_;
    }
}
