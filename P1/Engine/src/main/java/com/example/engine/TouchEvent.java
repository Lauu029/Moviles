package com.example.engine;

public class TouchEvent {
    public static enum TouchEventType{
        TOUCH_DOWN,
        TOUCH_UP,
        CLICK,
        TOUCH_DRAG
    };
    public int x, y;
    public TouchEventType type;
}
