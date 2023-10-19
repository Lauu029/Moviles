package com.example.gamelogic;

import com.example.engine.Color;

public class Circles {
    private int ratius;
    private Color c;

    Circles(Color c, int r) {
        this.c = c;
        ratius = r;
    }

    public int getRatius() {
        return ratius;
    }

    public void setRatius(int r) {
        ratius = r;
    }

}
