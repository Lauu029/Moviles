package com.example.androidgame.GameLogic;

public class AssetsManager {
    private EnumTheme circleTheme_= EnumTheme.ADVENTURE;
    private EnumTheme backgrounTheme_;
    private EnumPalette paletteColor_=EnumPalette.DEFAULT;
    private int backgroundColor_,buttonColor_,textColor_,lineColor_;
    private int yellowPalette[]={0xFFF9A300,0xE3BE2B00,0xDB8D0700,0xDB8D0700};
    private int defaultPalette[]={0xFFFFF0F6,0XFFFB839B,0xFFFFFFFF,0XFF222222};
    private static AssetsManager instance_;
    private AssetsManager() {
        // Constructor privado
    }
    EnumTheme getCirleTheme(){
        return circleTheme_;
    }
    //EnumPalette getPaletteColor(){return paletteColor_;}

    public int getBackgroundColor() {
        return backgroundColor_;
    }

    public int getButtonColor() {
        return buttonColor_;
    }

    public int getTextColor() {
        return textColor_;
    }

    public int getLineColor() {
        return lineColor_;
    }

    public void setPaletteTheme(EnumPalette paletteColor){

        paletteColor_=paletteColor;
        switch(paletteColor){
            case DEFAULT:
                backgroundColor_= defaultPalette[0];
                buttonColor_= defaultPalette[1];
                textColor_= defaultPalette[2];
                lineColor_= defaultPalette[3];
                break;
            case YELLOW:
                backgroundColor_= yellowPalette[0];
                buttonColor_= yellowPalette[1];
                textColor_= yellowPalette[2];
                lineColor_= yellowPalette[3];
                break;
            case BLUE:
                System.out.println("Esta paleta aun no esta implementada en assetManager");

                break;
            case GREEN:
                System.out.println("Esta paleta aun no esta implementada en assetManager");
                break;
            case HOT_PINK:
                System.out.println("Esta paleta aun no esta implementada en assetManager");

                break;
        }
    }
    void setCirleTheme(EnumTheme tematica){
         circleTheme_=tematica;
    }
    public static AssetsManager getInstance() {
        return instance_;
    }
    public static int init() {
        instance_ = new AssetsManager();
        return 1;
    }




}
