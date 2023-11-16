package com.example.engine;

public interface IEngine {
    //setear la escena al engine
    void setScene(IScene myIScene);
    //geter de la escena
    IScene getScene();
    IGraphics getGraphics(); //geter del motor grafico
    IInput getInput();  //geter del motor de manejo de entrada
    IAudio getAudio(); //geter del motor de audio


}
