package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;

public abstract class GameObject {
    abstract void update(double time); //Actualiza la logica
    abstract void render(Graphics graph); //Actualiza el dibujado
    abstract void init(); //Inicializa
    abstract boolean handleInput(TouchEvent event); //Gestiona el input del objeto
}
