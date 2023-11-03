package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputDesktop implements IInput {
    private ArrayList<TouchEvent> myTouchEvent_;  // Lista de eventos táctiles
    private ArrayList<TouchEvent> myPendingEvents_;  // Lista de eventos pendientes
    private InputHandler myInputHandler_;  // Manejador de entrada

    // Constructor de la clase, recibe un JFrame como argumento
    InputDesktop(JFrame jframe){
        myInputHandler_ = new InputHandler(jframe);  // Inicializa el manejador de entrada
        myTouchEvent_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos
        myPendingEvents_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos pendientes
    }

    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        // Obtiene los eventos pendientes y los agrega a la lista de eventos táctiles
        this.myTouchEvent_.addAll(myInputHandler_.getMyPendingEvents_());
        myInputHandler_.myPendingEventsClear();  // Limpia los eventos pendientes en el manejador de entrada
        return myTouchEvent_;  // Devuelve la lista de eventos
    }

    public void myEventsClear(){
        myTouchEvent_.clear();  // Limpia la lista de eventos
    }
}
