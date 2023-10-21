package com.example.engine;

public interface IGameObject {
    void update(double time);
    void render(IGraphics graph);
    void init();
}
