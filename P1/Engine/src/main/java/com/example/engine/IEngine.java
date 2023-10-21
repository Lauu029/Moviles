package com.example.engine;

public interface IEngine {
    void resume();
    void pause();
    void setScene(IScene myIScene);
    IScene getScene();
    IGraphics getGraphics();
    IInput getInput();
    IAudio getAudio();


}
