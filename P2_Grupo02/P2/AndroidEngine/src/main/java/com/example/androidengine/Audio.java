package com.example.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;

public class Audio {
    private AssetManager myAssetManager_; //Referencia al asset manager para buscar los .wav
    private SoundPool mySoundPool_; //Pool donde gestionar y guardar los sonidos
    private boolean mute_=false;
    //Constructor de la clase con parámetros: asset manager y sound pool
    public Audio(AssetManager assetManager, SoundPool soundPool){
        myAssetManager_ =assetManager;
        mySoundPool_ = new SoundPool.Builder().setMaxStreams(10).build(); //El soundpool permite 10 "clips" por sonido
    }
    //Crea un nuev sonido de tipo SoundAndroid
    public Sound newSound(String file) {

        Sound s= new Sound(file, myAssetManager_, mySoundPool_);
        return s;
    }
    //Reproduce un determinado sonido en loop o una unica vez
    public void playSound(Sound sound, int loop) {
        if(mute_==false){
            Sound sAndroid= (Sound) sound;
            int sId=sAndroid.getSoundId();

            mySoundPool_.play(sId,1, 1,1, loop, 1);
        }

    }
   public void setMute(boolean mute){
        mute_=mute;

    }

    //Detiene la reproducción de un sonido
    public void stopSound(Sound sound) {
        Sound sAndroid= (Sound) sound;
        int sId=sAndroid.getSoundId();
        mySoundPool_.stop(sId);
    }
}
