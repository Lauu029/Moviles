package com.example.androidgame.GameLogic;

public class Theme {

    private String tematica_;


    String background_;
    String gameBackground_;
    String bolas_;
    private boolean purchase_;

    // Constructor privado para asociar el String con el enum
    public Theme(String tematica, String background,String gameBackground,String bolas) {
        bolas_=bolas;
        tematica_ =tematica;
        gameBackground_=gameBackground;
        background_=background;
        purchase_=false;
    }

    // Método para obtener el valor asociado con el enum
    public String getPathBolas() {
        return bolas_;
    }
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
