package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;

public class FontDesktop implements IFont {
    private String name_;
    private Font font;
    private int size_;
    private boolean bold_;

    public FontDesktop(String filename, int size, boolean isBold,boolean italic) {
        this.size_ = size;
        this.bold_ = isBold;
    }
    Font getFont(){
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
