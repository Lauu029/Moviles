package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;

public abstract class GameObject {
    public abstract void update(double time); //Actualiza la logica
    public abstract void render(Graphics graph); //Actualiza el dibujado
    public abstract void init(); //Inicializa
    public abstract boolean handleInput(TouchEvent event); //Gestiona el input del objeto
}
