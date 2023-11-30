package com.example.androidgame.GameLogic;

public class AssetsManager {
    EnumTheme circleTheme_= EnumTheme.ADVENTURE;
    EnumTheme backgrounTheme_;
    EnumPalette paletteColor_=EnumPalette.DEFAULT;
    private static AssetsManager instance_;
    private AssetsManager() {
        // Constructor privado
    }
    EnumTheme getCirleTheme(){
        return circleTheme_;
    }
    EnumPalette getPaletteColor(){return paletteColor_;}
    void setPaletteTheme(EnumPalette paletteColor){
        paletteColor_=paletteColor;
    }
    void setCirleTheme(EnumTheme tematica){
         circleTheme_=tematica;
    }
    public static AssetsManager getInstance_() {

        return instance_;
    }
    public static int init() {
        instance_ = new AssetsManager();
        return 1;
    }




}
