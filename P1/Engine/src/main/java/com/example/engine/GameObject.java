package com.example.engine;

public interface GameObject {
    void update(double time);
    void render(Graphics graph);
    void init();
}
