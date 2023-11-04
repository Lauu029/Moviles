package com.example.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputHandler implements View.OnTouchListener {
    private ArrayList<TouchEvent> myTouchEvent_;
    private ArrayList<TouchEvent> myPendingEvents_;
    private ArrayList<TouchEvent> usedEvents_;
    private ArrayList<TouchEvent> freeEvents_;
    private int maxEvents_ = 10;

    InputHandler(SurfaceView view) {
        myTouchEvent_ = new ArrayList<TouchEvent>();
        myPendingEvents_ = new ArrayList<TouchEvent>();
        usedEvents_ = new ArrayList<TouchEvent>();
        freeEvents_ = new ArrayList<TouchEvent>();
        view.setOnTouchListener(this);
        for (int i = 0; i < maxEvents_; i++) {
            freeEvents_.add(new TouchEvent());
        }
    }

    public ArrayList<TouchEvent> getMyPendingEvents_() {
        return myPendingEvents_;
    }

    public void myPendingEventsClear() {
        myPendingEvents_.clear();
    }

    private TouchEvent getEvent() {
        if (!freeEvents_.isEmpty()) {
            TouchEvent obj = freeEvents_.remove(freeEvents_.size() - 1);
            usedEvents_.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No available objects in the pool.");
        }
    }

    private void returnObject(TouchEvent obj) {
        if (usedEvents_.contains(obj)) {
            usedEvents_.remove(obj);
            freeEvents_.add(obj);
        } else {
            throw new IllegalArgumentException("The object is not in use.");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchEvent event = getEvent();

        int index = motionEvent.getActionIndex();
        int finger = motionEvent.getPointerId(index);
        int action = motionEvent.getActionMasked();
        event.x = (int) motionEvent.getX(finger);
        event.y = (int) motionEvent.getY(finger);
        if (action == motionEvent.ACTION_DOWN) {
            event.type = TouchEvent.TouchEventType.TOUCH_DOWN;
        } else if (action == motionEvent.ACTION_UP) {
            event.type = TouchEvent.TouchEventType.TOUCH_UP;
        }

        synchronized (this) {
            myPendingEvents_.add(event);
        }
        returnObject(event);
        return true;
    }
}
