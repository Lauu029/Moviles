package com.example.engine;

public interface IFont {
    void setBold_(boolean bold_);
    void setItalic_(boolean italic_);
    boolean isBold_();
    boolean isItalic_();
    int getSize_();
    void setSize_(int size_);
}
