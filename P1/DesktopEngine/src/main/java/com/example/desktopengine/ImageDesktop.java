package com.example.desktopengine;

import com.example.engine.IImage;

import java.awt.Image;

import sun.font.GlyphRenderData;
import sun.jvm.hotspot.utilities.BitMap;

public class ImageDesktop implements IImage {

    Image image_;
    ImageDesktop(Image image){
        image_=image;
        
    }
    Image getImage(){
        return image_;
    }
    @Override
    public int getWidth() {
        return image_.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image_.getHeight(null);
    }
}
