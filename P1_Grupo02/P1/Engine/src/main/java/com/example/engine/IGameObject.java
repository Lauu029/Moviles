package com.example.engine;

public interface IGameObject {
    void update(double time); //Actualiza la logica
    void render(IGraphics graph); //Actualiza el dibujado
    void init(); //Inicializa
    boolean handleInput(TouchEvent event); //Gestiona el input del objeto
}
