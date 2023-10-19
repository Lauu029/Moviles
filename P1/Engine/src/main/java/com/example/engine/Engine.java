package com.example.engine;

public interface Engine{
    void resume();
    void pause();
    void setScene(Scene myScene);
    Scene getScene();
    Graphics getGraphics();
    Input getInput();
    Audio getAudio();


}
