package com.example.desktopengine;

import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputHandler implements MouseListener {
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    InputHandler(JFrame jframe){
        jframe.addMouseListener(this);
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
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
        TouchEvent event=new TouchEvent();
        event.x=mouseEvent.getX();
        event.y=mouseEvent.getY();
        event.type= TouchEvent.TouchEventType.TOUCH_DOWN;
        synchronized (this){
            myPendingEvents_.add(event);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
