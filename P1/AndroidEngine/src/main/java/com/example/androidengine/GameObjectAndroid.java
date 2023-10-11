package com.example.androidengine;

public class GameObjectAndroid implements com.example.engine.Image {
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
