package com.example.androidengine;

public class ColorAndroid implements com.example.engine.Color {
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
