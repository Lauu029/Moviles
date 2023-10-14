package com.example.androidengine;

public class FontAndroid implements com.example.engine.Font {
    private String name;
    private int size;
    private boolean bold;

    public FontAndroid(String filename, int size, boolean isBold) {
        this.size = size;
        this.bold = isBold;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    @Override
    public boolean isBold() {
        return this.bold;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
