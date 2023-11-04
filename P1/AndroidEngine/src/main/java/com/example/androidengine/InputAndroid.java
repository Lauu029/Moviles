package com.example.androidengine;

import android.view.SurfaceView;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputAndroid implements IInput{
    private ArrayList<TouchEvent> myTouchEvent;
    private ArrayList<TouchEvent> myPendingEvents;
    private InputHandler myInputHandler;
    InputAndroid(SurfaceView view){
        myTouchEvent =new ArrayList<TouchEvent>();
        myPendingEvents =new ArrayList<TouchEvent>();
        myInputHandler =new InputHandler(view);
    }
    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        this.myTouchEvent.addAll(myInputHandler.getMyPendingEvents());
        myInputHandler.myPendingEventsClear();
        return myTouchEvent;
    }
    @Override
    public void myEventsClear() {
        myTouchEvent.clear();
    }
}
