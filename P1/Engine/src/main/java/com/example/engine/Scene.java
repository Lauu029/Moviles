package com.example.engine;

public interface Scene {
    void render();
    void update(double time);
    void init();

    void addGameObject(GameObject gm);
}
