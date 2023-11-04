package com.example.engine;

import java.io.IOException;
import java.util.ArrayList;

public interface IScene {
    //render de la escena
    void render();
    //update de la escena
    void update(double time);
    //inicializacion de la escena
    void init() ;
    //altura de la escena
    int getHeight_();
    //ancho de la escena
    int getWidth_();
    //input de la escena
    void handleInput(ArrayList<TouchEvent> events);
}
