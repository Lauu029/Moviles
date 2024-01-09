package com.example.desktopengine;

import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputHandler implements MouseListener, MouseMotionListener {
    private ArrayList<TouchEvent> myTouchEvent_;  // Lista de eventos táctiles
    private ArrayList<TouchEvent> myPendingEvents_;  // Lista de eventos pendientes
    private ArrayList<TouchEvent> usedEvents_;  // Lista de eventos en uso
    private ArrayList<TouchEvent> freeEvents_;  // Lista de eventos libres
    private int maxEvents_ = 1000;  // Numero máximo de eventos en el pool

    // Constructor de la clase, recibe un JFrame como argumento
    InputHandler(JFrame jframe) {
        jframe.addMouseListener(this);  // Registra esta instancia como MouseListener en el JFrame
        jframe.addMouseMotionListener(this);
        myTouchEvent_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos táctiles
        myPendingEvents_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos pendientes
        usedEvents_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos en uso
        freeEvents_ = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos libres

        // Llena el pool de eventos con objetos TouchEvent
        for (int i = 0; i < maxEvents_; i++) {
            freeEvents_.add(new TouchEvent());
        }
    }

    // Obtiene un objeto TouchEvent del pool
    private TouchEvent getEvent() {
        if (!freeEvents_.isEmpty()) {
            TouchEvent obj = freeEvents_.remove(freeEvents_.size() - 1);
            usedEvents_.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No hay objetos disponibles en el pool.");
        }
    }

    // Devuelve un objeto TouchEvent al pool
    private void returnObject(TouchEvent obj) {
        if (usedEvents_.contains(obj)) {
            usedEvents_.remove(obj);
            freeEvents_.add(obj);
        } else {
            throw new IllegalArgumentException("El objeto no está en uso.");
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        TouchEvent event = getEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_DRAG;
        synchronized (this) {
            myPendingEvents_.add(event);
        }
        returnObject(event);
    }

    // Obtiene eventos cuando se presiona el boton del mouse
    @Override
    public void mousePressed(MouseEvent mouseEvent) {


        TouchEvent event = getEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_DOWN;
        synchronized (this) {
            myPendingEvents_.add(event);
        }
        returnObject(event);
    }

    // Obtiene eventos cuando se libera el boton del mouse
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        TouchEvent event = getEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_UP;
        synchronized (this) {
            myPendingEvents_.add(event);
        }
        returnObject(event);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    // Devuelve la lista de eventos pendientes
    public ArrayList<TouchEvent> getMyPendingEvents_() {
        return myPendingEvents_;
    }

    // Limpia la lista de eventos pendientes
    public void myPendingEventsClear() {
        myPendingEvents_.clear();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        TouchEvent event = getEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_DRAG;
        synchronized (this) {
            myPendingEvents_.add(event);
        }
        returnObject(event);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
