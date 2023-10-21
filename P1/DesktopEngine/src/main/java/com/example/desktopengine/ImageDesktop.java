package com.example.desktopengine;

import com.example.engine.IImage;

public class ImageDesktop implements IImage {
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
