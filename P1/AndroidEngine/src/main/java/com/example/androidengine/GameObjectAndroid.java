package com.example.androidengine;

import com.example.engine.IImage;

public class GameObjectAndroid implements IImage {
    private int with = 0, height = 0;

    @Override
    public int getWidth() {
        return with;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
