package com.example.androidengine;

import com.example.engine.IColor;

public class ColorAndroid implements IColor {
    private int color;

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
