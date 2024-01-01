package com.example.gamelogic;

import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public abstract class GameObject {
    public abstract void update(double time); //Actualiza la logica
    public abstract void render(IGraphics graph); //Actualiza el dibujado
    public abstract void init(); //Inicializa
    public abstract boolean handleInput(TouchEvent event); //Gestiona el input del objeto
}
