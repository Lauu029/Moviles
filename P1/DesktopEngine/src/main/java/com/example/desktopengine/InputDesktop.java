package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

import javax.swing.JFrame;

public class InputDesktop implements IInput {
    private ArrayList<TouchEvent> myTouchEvent;  // Lista de eventos táctiles
    private ArrayList<TouchEvent> myPendingEvents;  // Lista de eventos pendientes
    private InputHandler myInputHandler;  // Manejador de entrada

    // Constructor de la clase, recibe un JFrame como argumento
    InputDesktop(JFrame jframe){
        myInputHandler = new InputHandler(jframe);  // Inicializa el manejador de entrada
        myTouchEvent = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos
        myPendingEvents = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos pendientes
    }

    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        // Obtiene los eventos pendientes y los agrega a la lista de eventos táctiles
        this.myTouchEvent.addAll(myInputHandler.getMyPendingEvents());
        myInputHandler.myPendingEventsClear();  // Limpia los eventos pendientes en el manejador de entrada
        return myTouchEvent;  // Devuelve la lista de eventos
    }

    public void myEventsClear(){
        myTouchEvent.clear();  // Limpia la lista de eventos
    }
}
