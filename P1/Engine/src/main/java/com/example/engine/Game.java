package com.example.engine;

public interface Game {
    public void init();
    public void render();
    public void update();
    public void resume();
    public void setScene(Scene scene_);
    Scene getScene();
}
