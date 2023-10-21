package com.example.androidengine;

import com.example.engine.IColor;

public class ColorAndroid implements IColor {
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
