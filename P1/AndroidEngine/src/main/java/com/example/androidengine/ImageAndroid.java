package com.example.androidengine;

import android.graphics.Bitmap;

import com.example.engine.IImage;

//Aquí tendrán que ir todos los métodos que implementen para generar una imagen en android, entiendo
public class ImageAndroid implements IImage {
    private Bitmap bitmap_;
    public ImageAndroid(Bitmap image_){
        bitmap_ =image_;
    }
    public Bitmap getImage(){
        return bitmap_;
    }
    @Override
    public int getWidth() {
        return bitmap_.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap_.getHeight();
    }
}
