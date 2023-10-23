package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputDesktop implements IInput {
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    private InputHandler myInputHandler_;
    InputDesktop(JFrame jframe){
        myInputHandler_=new InputHandler(jframe);
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
    }
    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        this.myTouchEvent_.clear();
        this.myTouchEvent_.addAll(myInputHandler_.getMyPendingEvents_());
        myInputHandler_.myPendingEventsClear();
        return myTouchEvent_;
    }

    @Override
    public void resizeInput(float scale, float tranlateX, float tranlateY) {
        for(TouchEvent event:myInputHandler_.getMyPendingEvents_()){
            event.x-=tranlateX;
            event.y-=tranlateY;
            event.x/=scale;
            event.y/=scale;
        }
    }


}
