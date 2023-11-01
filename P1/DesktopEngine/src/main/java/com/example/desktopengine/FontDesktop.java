package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FontDesktop implements IFont {
    private String path_ ="Assets/";
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
            is = new FileInputStream(path_ +filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            myFont_ = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int style = Font.PLAIN;
        if (bold_) {
            style |= Font.BOLD;
        }
        if (italic_) {
            style |= Font.ITALIC;
        }
        myFont_=myFont_.deriveFont(style,size_);
    }
    Font getFont(){
        return myFont_;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold_ = bold;
    }

    @Override
    public void setItalic(boolean italic) {
        italic_=italic;
    }

    @Override
    public boolean isBold() {
        return this.bold_;
    }

    @Override
    public boolean isItalic() {
        return italic_;
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
