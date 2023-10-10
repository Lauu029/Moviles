package com.example.engine;

public interface Scene {
    void render();
    void update();
    void init();

    void addGameObject(GameObject gm);
}
