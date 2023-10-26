package com.example.engine;

public interface IEngine {
    void setScene(IScene myIScene);
    IScene getScene();
    IGraphics getGraphics();
    IInput getInput();
    IAudio getAudio();


}
