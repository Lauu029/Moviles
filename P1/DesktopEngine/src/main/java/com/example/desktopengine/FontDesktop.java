package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FontDesktop implements IFont {
    private String name_;
    private Font myFont_;
    private int size_;
    private boolean bold_;
    private boolean italic_;

    public FontDesktop(String filename, int size, boolean isBold,boolean italic) {

        this.size_ = size;
        this.bold_ = isBold;
        this.italic_=italic;

        InputStream is=null;
        myFont_=null;

        try {
            is = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            myFont_ = Font.createFont(Font.TRUETYPE_FONT, is);
            myFont_=myFont_.deriveFont(Font.TRUETYPE_FONT,size_);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    Font getFont(){
        return myFont_;
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
