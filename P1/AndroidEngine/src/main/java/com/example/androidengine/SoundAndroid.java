package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.ISound;

import java.io.IOException;
import java.util.HashMap;

public class SoundAndroid implements ISound {

    private int myStreamId_;
    private HashMap<String, Integer> mySounds_;
    private String soundId_;

    public SoundAndroid(String filename, String id,AssetManager assetManager, SoundPool soundPool) {
        myStreamId_ = -1;
        mySounds_ = new HashMap<>();
        try {
            AssetFileDescriptor assetDescriptor =
                    assetManager.openFd(filename);
            soundId_=id;
            myStreamId_ = soundPool.load(assetDescriptor, 1);
            mySounds_.put(id,myStreamId_);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound.");
        }

    }

    public int getSoundId() {
        if (mySounds_.containsKey(soundId_)) {
            myStreamId_ = mySounds_.get(soundId_);
            return myStreamId_;
        }
        else return -1;
    }

}
