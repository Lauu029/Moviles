package com.example.androidgame.GameLogic;

public class AssetsManager {
    private EnumTheme circleTheme_= EnumTheme.ADVENTURE;
    private EnumTheme backgrounTheme_;
    private EnumPalette paletteColor_=EnumPalette.DEFAULT;
    private int backgroundColor_=0xFFFFF0F6;
    private int buttonColor_=0XFFFB839B;
    private int textColor_=0xFFFFFFFF;
    private int lineColor_=0XFF222222;
    private int defaultPalette[]={0xFFFFF0F6,0XFFFB839B,0xFFFFFFFF,0XFF222222};
    private int yellowPalette[]={0xFFebe57c,0xFFE3BE2B,0xFFDB8D07,0xFFDB8D07};
    private int bluePalette[]={0xFF70b2e0,0xFF1f438f,0xFFFFFFFF,0xFF30ace6};
    private int greenPalette[]={0xFF66d46b,0xFF40a845,0xFFFFFFFF,0xFF18571b};
    private int hotPinkPalette[]={0xFFe681b0,0xFFd60466,0xFFFFFFFF,0xFF9e0240};

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
                backgroundColor_= bluePalette[0];
                buttonColor_= bluePalette[1];
                textColor_= bluePalette[2];
                lineColor_= bluePalette[3];
                break;
            case GREEN:
                backgroundColor_= greenPalette[0];
                buttonColor_= greenPalette[1];
                textColor_= greenPalette[2];
                lineColor_= greenPalette[3];
                break;
            case HOT_PINK:
                backgroundColor_= hotPinkPalette[0];
                buttonColor_= hotPinkPalette[1];
                textColor_= hotPinkPalette[2];
                lineColor_= hotPinkPalette[3];

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
