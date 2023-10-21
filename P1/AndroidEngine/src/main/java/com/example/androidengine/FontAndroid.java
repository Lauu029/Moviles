package com.example.androidengine;

import com.example.engine.IFont;

public class FontAndroid implements IFont {
    private String name_;
    private int size_;
    private boolean bold_;

    public FontAndroid(String filename, int size, boolean isBold) {
        this.size_ = size;
        this.bold_ = isBold;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold_ = bold;
    }

    @Override
    public boolean isBold() {
        return this.bold_;
    }

    @Override
    public int getSize() {
        return this.size_;
    }

    @Override
    public void setSize(int size) {
        this.size_ = size;
    }
}
