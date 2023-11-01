package com.example.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class AudioAndroid implements IAudio {
    private AssetManager myAssetManager_;
    SoundPool mySoundPool_;
    float volume_;
    float rate_;
    public AudioAndroid(AssetManager assetManager, SoundPool soundPool){
        myAssetManager_=assetManager;
        mySoundPool_= new SoundPool.Builder().setMaxStreams(10).build();
        volume_=1;
        rate_=1;
    }
    @Override
    public ISound newSound(String file, String id) {
        SoundAndroid s= new SoundAndroid(file,id,myAssetManager_,mySoundPool_);
        return s;
    }

    @Override
    public void playSound(ISound sound, int loop) {

        SoundAndroid sAndroid= (SoundAndroid) sound;
        int sId=sAndroid.getSoundId();

        mySoundPool_.play(sId,volume_, volume_,
                1, loop, rate_);
    }

    @Override
    public void stopSound(ISound sound) {
        SoundAndroid sAndroid= (SoundAndroid) sound;
        int sId=sAndroid.getSoundId();
        mySoundPool_.stop(sId);

    }
}
