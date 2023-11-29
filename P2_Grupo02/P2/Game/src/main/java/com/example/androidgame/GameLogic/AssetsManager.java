package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.IScene;

public class AssetsManager {
    EnumTematica circleTematica_=EnumTematica.ADVENTURE;
    EnumTematica backgrounTematica_;
    private static AssetsManager instance_;
    private AssetsManager() {
        // Constructor privado
    }
    EnumTematica getCirleTematic(){
        return circleTematica_;
    }
    void setCirleTematic(EnumTematica tematica){
         circleTematica_=tematica;
    }
    public static AssetsManager getInstance_() {

        return instance_;
    }
    public static int init() {
        instance_ = new AssetsManager();
        return 1;
    }




}
