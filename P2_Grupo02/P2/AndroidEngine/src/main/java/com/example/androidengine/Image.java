package com.example.androidengine;

import android.graphics.Bitmap;


public class Image {
    private Bitmap bitmap_; //Mapa de bits necesario para generar la imagen en android
    private boolean isVisible_;
    //Constructor de la clase
    public Image(Bitmap image_){
        bitmap_ =image_;
        isVisible_ = true;
    }
    //Devuelve el mapa de bits usado para crear la imagen
    public Bitmap getImage(){
        return isVisible_ ? bitmap_ : null;
    }
    //Devuelve el ancho del mapa de bits
    public int getWidth() {
        return bitmap_.getWidth();
    }
    //Devuelve el alto del mapa de bits
    public int getHeight() {
        return bitmap_.getHeight();
    }
    public void setVisibility(boolean isVisible) {
        isVisible_ = isVisible;
    }
    public boolean isVisible() {
        return isVisible_;
    }

}
