package com.example.engine;

public interface IFont {
    void setBold(boolean bold);
    void setItalic(boolean italic);
    boolean isBold();
    boolean isItalic();
    int getSize();
    void setSize(int size);
}
