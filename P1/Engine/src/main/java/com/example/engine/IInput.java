package com.example.engine;

import java.util.ArrayList;

public interface IInput {

    public ArrayList<TouchEvent>  getTouchEvent(); //Devuelve una lista de eventos de entrada

    public void myEventsClear(); //Limpia una lista de eventos de entradas
}
