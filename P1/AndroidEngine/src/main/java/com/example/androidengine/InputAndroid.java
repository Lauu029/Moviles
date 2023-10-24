package com.example.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.engine.IInput;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputAndroid implements IInput,View.OnTouchListener{
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;

    @Override
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        this.myTouchEvent_.addAll(myPendingEvents_);
        myPendingEvents_.clear();
        return myTouchEvent_;
    }

    @Override
    public void resizeInput(float sclae, float tranlateX, float tranlateY) {

    }

    @Override
    public void myEventsClear() {
        myTouchEvent_.clear();
    }

    InputAndroid(SurfaceView view){
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
        view.setOnTouchListener( this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //hay que hacer una pull de events
        TouchEvent event=new TouchEvent();

        int index=motionEvent.getActionIndex();
        int finger=motionEvent.getPointerId(index);
        int action=motionEvent.getActionMasked();
        event.x=(int)motionEvent.getX(finger);
        event.y=(int)motionEvent.getY(finger);
        if(action==motionEvent.ACTION_DOWN){
            event.type= TouchEvent.TouchEventType.TOUCH_DOWN;
        }
        else if(action==motionEvent.ACTION_UP){
            event.type= TouchEvent.TouchEventType.TOUCH_UP;
        }


        synchronized (this){
            myPendingEvents_.add(event);
        }
        return true;
    }
}
