package com.example.desktopengine;

import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputHandler implements MouseListener {
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    private ArrayList<TouchEvent> usedEvnets;
    private ArrayList<TouchEvent> freeEvnets;
    private int maxEvents=10;
    InputHandler(JFrame jframe){
        jframe.addMouseListener(this);
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
        usedEvnets=new ArrayList<TouchEvent>();
        freeEvnets=new ArrayList<TouchEvent>();

        for(int i=0;i<maxEvents;i++){
            freeEvnets.add(new TouchEvent());
        }
    }
    private TouchEvent getEvent() {
        if (!freeEvnets.isEmpty()) {
            TouchEvent obj = freeEvnets.remove(freeEvnets.size() - 1);
            usedEvnets.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No available objects in the pool.");
        }
    }
    private void returnObject(TouchEvent obj) {
        if (usedEvnets.contains(obj)) {
            usedEvnets.remove(obj);
            freeEvnets.add(obj);
        } else {
            throw new IllegalArgumentException("The object is not in use.");
        }
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }
    public ArrayList<TouchEvent>getMyPendingEvents_(){
        return myPendingEvents_;
    }
    public void myPendingEventsClear(){
        myPendingEvents_.clear();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent event=getEvent();
        event.x=mouseEvent.getX();
        event.y=mouseEvent.getY();
        event.type= TouchEvent.TouchEventType.TOUCH_DOWN;
        synchronized (this){
            myPendingEvents_.add(event);
        }
        returnObject(event);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent event=getEvent();
        event.x=mouseEvent.getX();
        event.y=mouseEvent.getY();
        event.type= TouchEvent.TouchEventType.TOUCH_UP;
        synchronized (this){
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
}
