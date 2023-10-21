package com.example.desktopengine;

import com.example.engine.IImage;

public class ImageDesktop implements IImage {
    private int width_ = 0, height_ = 0;

    @Override
    public int getWidth() {
        return width_;
    }

    @Override
    public int getHeight() {
        return height_;
    }
}
