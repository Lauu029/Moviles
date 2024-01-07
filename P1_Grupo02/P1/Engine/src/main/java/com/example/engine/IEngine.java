package com.example.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public interface IEngine {
    //setear la escena al engine
    void setScene(IScene myIScene);
    //geter de la escena
    IScene getScene();
    IGraphics getGraphics(); //geter del motor grafico
    IInput getInput();  //geter del motor de manejo de entrada
    IAudio getAudio(); //geter del motor de audio
    FileInputStream getFileInputStream(String s);
    FileOutputStream getFileOutputStream(String s);

}
