package com.example.androidgame.GameLogic;

public enum EnumTheme {
    DEFAULT("default",true),
    HALLOWEEN("halloween/",true),
    ADVENTURE("adventure/",true);

    private final String path_;
    boolean purchase_;

    // Constructor privado para asociar el String con el enum
    private EnumTheme(String path, boolean purchase_) {
        this.path_ = path;
    }

    // Método para obtener el valor asociado con el enum
    public String getPath() {
        return path_;
    }
    public Boolean getPurchased() {
        return purchase_;
    }
    void  setPurchased(boolean purc) {
         purchase_=purc;
    }
}