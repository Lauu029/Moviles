package com.example.engine;

import java.util.ArrayList;

public interface IInput {

    public ArrayList<TouchEvent>  getTouchEvent();
    public void resizeInput(float sclae,float tranlateX, float tranlateY);
}
