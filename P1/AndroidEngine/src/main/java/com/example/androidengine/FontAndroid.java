package com.example.androidengine;

import android.graphics.Typeface;

import com.example.engine.IFont;

public class FontAndroid implements IFont {
    private String name_;
    Typeface font;

    private int size_;
    private boolean bold_;

    public FontAndroid(String filename,int size,boolean bold,boolean italic) {

    }
    public Typeface getFont(){
        return font;
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
