package com.example.desktopengine;

public class ImageDesktop implements com.example.engine.Image {
    private int width = 0, height = 0;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
