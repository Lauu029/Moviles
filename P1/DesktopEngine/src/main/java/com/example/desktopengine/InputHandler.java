package com.example.desktopengine;

import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputHandler implements MouseListener {
    private ArrayList<TouchEvent> myTouchEvent;  // Lista de eventos táctiles
    private ArrayList<TouchEvent> myPendingEvents;  // Lista de eventos pendientes
    private ArrayList<TouchEvent> usedEvents;  // Lista de eventos en uso
    private ArrayList<TouchEvent> freeEvents;  // Lista de eventos libres
    private int maxEvents = 10;  // Numero máximo de eventos en el pool

    // Constructor de la clase, recibe un JFrame como argumento
    InputHandler(JFrame jframe) {
        jframe.addMouseListener(this);  // Registra esta instancia como MouseListener en el JFrame
        myTouchEvent = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos táctiles
        myPendingEvents = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos pendientes
        usedEvents = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos en uso
        freeEvents = new ArrayList<TouchEvent>();  // Inicializa la lista de eventos libres

        // Llena el pool de eventos con objetos TouchEvent
        for (int i = 0; i < maxEvents; i++) {
            freeEvents.add(new TouchEvent());
        }
    }

    // Obtiene un objeto TouchEvent del pool
    private TouchEvent getEvent() {
        if (!freeEvents.isEmpty()) {
            TouchEvent obj = freeEvents.remove(freeEvents.size() - 1);
            usedEvents.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No hay objetos disponibles en el pool.");
        }
    }

    // Devuelve un objeto TouchEvent al pool
    private void returnObject(TouchEvent obj) {
        if (usedEvents.contains(obj)) {
            usedEvents.remove(obj);
            freeEvents.add(obj);
        } else {
            throw new IllegalArgumentException("El objeto no está en uso.");
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    // Obtiene eventos cuando se presiona el boton del mouse
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent event = getEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_DOWN;
        synchronized (this) {
            myPendingEvents.add(event);
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
            myPendingEvents.add(event);
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
    public ArrayList<TouchEvent> getMyPendingEvents() {
        return myPendingEvents;
    }

    // Limpia la lista de eventos pendientes
    public void myPendingEventsClear() {
        myPendingEvents.clear();
    }
}
