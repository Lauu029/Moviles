package com.example.engine;

import java.io.IOException;
import java.util.ArrayList;

public interface IScene {
    void render();
    void update(double time);
    void init() ;
    int getHeight();
    int getWidth();
    void handleInput(ArrayList<TouchEvent> events);
}
