package com.example.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputHandler implements View.OnTouchListener{
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    InputHandler(SurfaceView view){
        myTouchEvent_=new ArrayList<TouchEvent>();
        myPendingEvents_=new ArrayList<TouchEvent>();
        view.setOnTouchListener( this);
    }
    public ArrayList<TouchEvent>getMyPendingEvents_(){
        return myPendingEvents_;
    }
    public void myPendingEventsClear(){
        myPendingEvents_.clear();
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
