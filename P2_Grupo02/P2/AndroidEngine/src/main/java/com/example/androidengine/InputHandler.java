package com.example.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class InputHandler implements View.OnTouchListener {
    private ArrayList<TouchEvent> myPendingEvents_; // Lista de eventos pendientes
    private ArrayList<TouchEvent> usedEvents_; // Lista de eventos en uso
    private ArrayList<TouchEvent> freeEvents_;  // Lista de eventos libres
    private int maxEvents_ = 1000; // Numero máximo de eventos en el pool

    InputHandler(SurfaceView view) {
        myPendingEvents_ = new ArrayList<TouchEvent>(); // Inicializa la lista de eventos pendientes
        usedEvents_ = new ArrayList<TouchEvent>(); // Inicializa la lista de eventos en uso
        freeEvents_ = new ArrayList<TouchEvent>(); // Inicializa la lista de eventos libres
        view.setOnTouchListener(this); // Definición de interfaz para que se invoque un callback cuando se envía un evento de pulsado a esta vista.
        for (int i = 0; i < maxEvents_; i++) {
            freeEvents_.add(new TouchEvent()); // Llena el pool de eventos con objetos TouchEvent
        }
    }
    // Devuelve la lista de eventos pendientes
    public ArrayList<TouchEvent> getMyPendingEvents_() {
        return myPendingEvents_;
    }
    // Limpia la lista de eventos pendientes
    public void myPendingEventsClear() {
        myPendingEvents_.clear();
    }
    // Obtiene un objeto TouchEvent del pool
    private TouchEvent getEvent() {
        if (!freeEvents_.isEmpty()) {
            TouchEvent obj = freeEvents_.remove(freeEvents_.size() - 1);
            usedEvents_.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No available objects in the pool.");
        }
    }
    // Devuelve un objeto TouchEvent al pool
    private void returnObject(TouchEvent obj) {
        if (usedEvents_.contains(obj)) {
            usedEvents_.remove(obj);
            freeEvents_.add(obj);
        } else {
            throw new IllegalArgumentException("The object is not in use.");
        }
    }
    //Se llama cuando un evento touch se envía a una vista
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int index = motionEvent.getActionIndex(); //Devuelve el índice de puntero asociado
        int finger = motionEvent.getPointerId(index); //Dedo que realiza el toque (pantallas multitactil)
        int action = motionEvent.getActionMasked();
        float startX = 0;
        if (action == motionEvent.ACTION_DOWN) { //Comprueba que tipo de accion ha realizado (pulsar, levantar)
            TouchEvent event = getEvent();
            event.x = (int) motionEvent.getX(index); //Obtiene las coordenadas donde ha tenido lugar la accion
            event.y = (int) motionEvent.getY(index);
            startX=event.x;
            event.type = TouchEvent.TouchEventType.TOUCH_DOWN;
            synchronized (this) { //Es necesario sincronizarlo para evitar fallos al borrar los elementos de esta lista
                myPendingEvents_.add(event);
            }
            returnObject(event);
        } else if (action == motionEvent.ACTION_UP) {
            TouchEvent event = getEvent();
            event.x = (int) motionEvent.getX(index); //Obtiene las coordenadas donde ha tenido lugar la accion
            event.y = (int) motionEvent.getY(index);
            event.type = TouchEvent.TouchEventType.TOUCH_UP;
            synchronized (this) { //Es necesario sincronizarlo para evitar fallos al borrar los elementos de esta lista
                myPendingEvents_.add(event);
            }
            returnObject(event);
       }
        else if (action==motionEvent.ACTION_MOVE) {
            TouchEvent event = getEvent();
            event.type = TouchEvent.TouchEventType.TOUCH_DRAG;

             event.x = (int)motionEvent.getX(index);
             event.y=(int)motionEvent.getY(index);
            synchronized (this) { //Es necesario sincronizarlo para evitar fallos al borrar los elementos de esta lista
                myPendingEvents_.add(event);
            }
            returnObject(event);
        }
        return true;
    }
}
