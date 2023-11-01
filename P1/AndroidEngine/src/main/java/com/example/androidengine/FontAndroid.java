package com.example.androidengine;

import android.graphics.Typeface;
import android.content.res.AssetManager;
import com.example.engine.IFont;

import java.io.InputStream;

public class FontAndroid implements IFont {
    private String name_="";
    Typeface myFont_;

    private int size_;
    private boolean bold_;
    private boolean italic_;
    public FontAndroid(String filename,int size,boolean isBold,boolean italic, AssetManager assetManager) {
        this.size_ = size;
        this.bold_ = isBold;
        this.italic_=italic;
        myFont_=null;
        myFont_=Typeface.createFromAsset(assetManager,filename);

    }
    public Typeface getFont(){
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
