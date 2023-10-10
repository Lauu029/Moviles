package com.example.engine;

public interface Engine {
    void resume();
    void pause();
    void setScene();
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();
}
