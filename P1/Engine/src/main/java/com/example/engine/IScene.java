package com.example.engine;

public interface IScene {
    void render();
    void update(double time);
    void init();

    void addGameObject(IGameObject gm);

    int getHeight();
    int getWidth();
}
