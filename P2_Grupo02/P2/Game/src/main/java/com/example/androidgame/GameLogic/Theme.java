package com.example.androidgame.GameLogic;

public class Theme {

    private String name_;
    private  String path_;
    private boolean purchase_;

    // Constructor privado para asociar el String con el enum
    public Theme(String name, String path) {
        this.path_ = path;
        name_=name;
        purchase_=false;
    }

    // Método para obtener el valor asociado con el enum
    public String getPath() {
        return path_;
    }
    public String getName() {
        return name_;
    }
    public void setPath(String path) {
         path_=path;
    }
    public void setName(String name) {
        name_= name;
    }
    public Boolean getPurchased() {
        return purchase_;
    }
    void  setPurchased(boolean purc) {
         purchase_=purc;
    }
}
