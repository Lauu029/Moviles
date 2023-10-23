package com.example.androidengine;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.TouchEvent;

public class GameObjectAndroid implements IGameObject {

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {

    }

    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return false;
    }
}
