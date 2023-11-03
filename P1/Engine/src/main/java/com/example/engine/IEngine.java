package com.example.engine;

public interface IEngine {
    //setear la escena al engine
    void setScene(IScene myIScene);
    //geter de la escena
    IScene getScene();

    IGraphics getGraphics();
    IInput getInput();
    IAudio getAudio();


}
