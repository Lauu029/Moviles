package com.example.desktopengine;

import com.example.engine.IImage;

import java.awt.Image;

import sun.font.GlyphRenderData;
import sun.jvm.hotspot.utilities.BitMap;

public class ImageDesktop implements IImage {
    private int width_ = 0, height_ = 0;
    private BitMap bitmap;
    Image image;
    ImageDesktop(Image bit){
        image=bit;
        
    }
    Image getImage(){
        return image;
    }
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
