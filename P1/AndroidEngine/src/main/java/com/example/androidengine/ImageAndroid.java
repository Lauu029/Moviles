package com.example.androidengine;

import android.graphics.Bitmap;

import com.example.engine.IImage;

//Aquí tendrán que ir todos los métodos que implementen para generar una imagen en android, entiendo
public class ImageAndroid implements IImage {
    private Bitmap bitmap;
    public ImageAndroid(Bitmap image_){
        bitmap =image_;
    }
    public Bitmap getImage(){
        return bitmap;
    }
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }
}
