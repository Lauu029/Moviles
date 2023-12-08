package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Image;

public class Theme {
    private String tematica_;
    private String background_;
    private String gameBackground_;
    private String bolas_;
    private Image gameBackgroundImage_,backgroundImage_;
    private boolean purchase_;

    // Constructor privado para asociar el String con el enum
    public Theme(String tematica, String background,String gameBackground,String bolas) {
        bolas_= bolas;
        tematica_ = tematica;
        gameBackground_= gameBackground;
        background_ = background;
        purchase_=false;
    }

    // Método para obtener el valor asociado con el enum
    public String getPathBolas() {
        return bolas_;
    }
    public void setBackground(String background_){
        this.background_ = background_;
    }
    public void setPathBolas(String balls_){ bolas_ = balls_; }
    public String getName() {
        return tematica_;
    }
    public String getGameBackground() {
        return gameBackground_;
    }
    public String getBackground() {
        return background_;
    }
    public Boolean getPurchased() {
        return purchase_;
    }
    void  setPurchased(boolean purc) {
         purchase_=purc;
    }
}
