package com.example.engine;

public interface Engine extends Runnable {
    void resume();
    void pause();
    void run();
    void setScene(Scene myScene);
    Scene getScene();
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();


}
