package com.example.androidengine;

import android.view.SurfaceView;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputAndroid implements IInput{
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    private InputHandler myInputHandler_;
    InputAndroid(SurfaceView view){
        myTouchEvent_ =new ArrayList<TouchEvent>();
        myPendingEvents_ =new ArrayList<TouchEvent>();
        myInputHandler_ =new InputHandler(view);
    }
    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        this.myTouchEvent_.addAll(myInputHandler_.getMyPendingEvents_());
        myInputHandler_.myPendingEventsClear();
        return myTouchEvent_;
    }
    @Override
    public void myEventsClear() {
        myTouchEvent_.clear();
    }
}
