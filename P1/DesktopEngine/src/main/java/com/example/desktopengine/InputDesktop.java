package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputDesktop implements IInput, MouseListener {
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    InputDesktop(JFrame jframe){
        jframe.addMouseListener(this);
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
    }
    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        this.myTouchEvent_.clear();
        this.myTouchEvent_.addAll(myPendingEvents_);

        myPendingEvents_.clear();

        return myTouchEvent_;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        //hay que hacer una pull de events
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
