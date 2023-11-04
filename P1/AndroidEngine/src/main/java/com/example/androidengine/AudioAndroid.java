package com.example.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class AudioAndroid implements IAudio {
    private AssetManager myAssetManager_; //Referencia al asset manager para buscar los .wav
    private SoundPool mySoundPool_; //Pool donde gestionar y guardar los sonidos

    //Constructor de la clase con parámetros: asset manager y sound pool
    public AudioAndroid(AssetManager assetManager, SoundPool soundPool){
        myAssetManager_ =assetManager;
        mySoundPool_ = new SoundPool.Builder().setMaxStreams(10).build(); //El soundpool permite 10 "clips" por sonido
    }
    //Crea un nuev sonido de tipo SoundAndroid
    @Override
    public ISound newSound(String file) {
        SoundAndroid s= new SoundAndroid(file, myAssetManager_, mySoundPool_);
        return s;
    }
    //Reproduce un determinado sonido
    @Override
    public void playSound(ISound sound, int loop) {
        SoundAndroid sAndroid= (SoundAndroid) sound;
        int sId=sAndroid.getSoundId();

        mySoundPool_.play(sId,1, 1,1, loop, 1);
    }
    //Detiene la reproducción de un sonido
    @Override
    public void stopSound(ISound sound) {
        SoundAndroid sAndroid= (SoundAndroid) sound;
        int sId=sAndroid.getSoundId();
        mySoundPool_.stop(sId);
    }
}
