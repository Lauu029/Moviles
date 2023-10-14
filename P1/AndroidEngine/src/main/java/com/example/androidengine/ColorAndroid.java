package com.example.androidengine;

public class ColorAndroid implements com.example.engine.Color {
    private String color;

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }
}
