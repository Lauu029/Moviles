package com.example.androidengine;

import android.graphics.Bitmap;

import com.example.engine.IImage;

public class ImageAndroid implements IImage {
    private Bitmap bitmap_; //Mapa de bits necesario para generar la imagen en android
    //Constructor de la clase
    public ImageAndroid(Bitmap image_){
        bitmap_ =image_;
    }
    //Devuelve el mapa de bits usado para crear la imagen
    public Bitmap getImage(){
        return bitmap_;
    }
    //Devuelve el ancho del mapa de bits
    @Override
    public int getWidth() {
        return bitmap_.getWidth();
    }
    //Devuelve el alto del mapa de bits
    @Override
    public int getHeight() {
        return bitmap_.getHeight();
    }
}
