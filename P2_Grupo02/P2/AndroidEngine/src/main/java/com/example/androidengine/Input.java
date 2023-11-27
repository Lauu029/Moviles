package com.example.androidengine;

import android.view.SurfaceView;

import java.util.ArrayList;

public class Input {

    private ArrayList<TouchEvent> myTouchEvent_; // Lista de eventos de entrada

    private InputHandler myInputHandler_; // Manejador de entrada
    // Constructor de la clase
    Input(SurfaceView view){
        myTouchEvent_ =new ArrayList<TouchEvent>(); // Inicializa la lista de eventos

        myInputHandler_ =new InputHandler(view); // Inicializa el manejador de entrada
    }
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        // Obtiene los eventos pendientes y los agrega a la lista de eventos de entrada
        this.myTouchEvent_.addAll(myInputHandler_.getMyPendingEvents_());
        myInputHandler_.myPendingEventsClear(); // Limpia los eventos pendientes en el manejador de entrada
        return myTouchEvent_; // Devuelve la lista de eventos
    }
    public synchronized void myEventsClear() {
        myTouchEvent_.clear();
    } // Limpia la lista de eventos

}
