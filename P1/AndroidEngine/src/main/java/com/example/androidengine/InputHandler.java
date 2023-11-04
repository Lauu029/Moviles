package com.example.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputHandler implements View.OnTouchListener {
    private ArrayList<TouchEvent> myTouchEvent;
    private ArrayList<TouchEvent> myPendingEvents;
    private ArrayList<TouchEvent> usedEvents;
    private ArrayList<TouchEvent> freeEvents;
    private int maxEvents = 10;

    InputHandler(SurfaceView view) {
        myTouchEvent = new ArrayList<TouchEvent>();
        myPendingEvents = new ArrayList<TouchEvent>();
        usedEvents = new ArrayList<TouchEvent>();
        freeEvents = new ArrayList<TouchEvent>();
        view.setOnTouchListener(this);
        for (int i = 0; i < maxEvents; i++) {
            freeEvents.add(new TouchEvent());
        }
    }

    public ArrayList<TouchEvent> getMyPendingEvents() {
        return myPendingEvents;
    }

    public void myPendingEventsClear() {
        myPendingEvents.clear();
    }

    private TouchEvent getEvent() {
        if (!freeEvents.isEmpty()) {
            TouchEvent obj = freeEvents.remove(freeEvents.size() - 1);
            usedEvents.add(obj);
            return obj;
        } else {
            throw new IllegalStateException("No available objects in the pool.");
        }
    }

    private void returnObject(TouchEvent obj) {
        if (usedEvents.contains(obj)) {
            usedEvents.remove(obj);
            freeEvents.add(obj);
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
            myPendingEvents.add(event);
        }
        returnObject(event);
        return true;
    }
}
