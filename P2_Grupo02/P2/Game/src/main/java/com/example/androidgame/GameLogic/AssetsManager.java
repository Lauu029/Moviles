package com.example.androidgame.GameLogic;

import java.util.TreeMap;

public class AssetsManager {

    private TreeMap<String,TreeMap<String ,Boolean>>tematica_;
    private Theme circleTheme_= new Theme("DEFAULT","");
    private Theme worldCircleTheme_=  new Theme("DEFAULT","");
    private Theme backgrounTheme_;
    private EnumPalette paletteColor_=EnumPalette.DEFAULT;
    private int backgroundColor_=0xFFFFF0F6;
    private int buttonColor_=0XD0FB839B;
    private int textColor_=0xFFFFFFFF;
    private int lineColor_=0XFF222222;
    private int defaultPalette[]={0xFFFFF0F6,0XD0FB839B,0xFFFFFFFF,0XFF222222};
    private int yellowPalette[]={0xFFebe57c,0xD0E3BE2B,0xFFDB8D07,0xFFDB8D07};
    private int bluePalette[]={0xFF70b2e0,0xD01f438f,0xFFFFFFFF,0xFF30ace6};
    private int greenPalette[]={0xFF66d46b,0xD040a845,0xFFFFFFFF,0xFF18571b};
    private int hotPinkPalette[]={0xFFe681b0,0xD0d60466,0xFFFFFFFF,0xFF9e0240};

    private static AssetsManager instance_;
    private AssetsManager() {
        // Constructor privado
    }
    Theme getCirleTheme(boolean world){
        if(!world)return circleTheme_;
        return worldCircleTheme_;
    }
    EnumPalette getPaletteColor(){return paletteColor_;}
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
    void setCirleTheme(Theme tema,boolean world){

        String temaName = tema.getName();

        if (!tematica_.containsKey(temaName)) {
            // El tema no existe en el TreeMap, crear uno nuevo y agregarlo
            TreeMap<String, Boolean> nuevoTema = new TreeMap<>();
            nuevoTema.put(tema.getPath(), tema.getPurchased());
            tematica_.put(temaName, nuevoTema);
        }
        if(!world)
        circleTheme_=tema;
        else{
            worldCircleTheme_=tema;
        }
    }
    void setWorldThem(Theme tema){

        String temaName = tema.getName();

        if (!tematica_.containsKey(temaName)) {
            // El tema no existe en el TreeMap, crear uno nuevo y agregarlo
            TreeMap<String, Boolean> nuevoTema = new TreeMap<>();
            nuevoTema.put(tema.getPath(), tema.getPurchased());
            tematica_.put(temaName, nuevoTema);
        }

        worldCircleTheme_=tema;

    }
    public static AssetsManager getInstance() {
        return instance_;
    }
    public static int init() {
        instance_ = new AssetsManager();
        instance_.tematica_=new TreeMap<String,TreeMap<String ,Boolean>>();
        String nuevaClave = "DEFAULT";
        TreeMap<String, Boolean> nuevoValor = new TreeMap<>();
        nuevoValor.put("", true);
        instance_.tematica_.put(nuevaClave, nuevoValor);
        return 1;
    }




}
