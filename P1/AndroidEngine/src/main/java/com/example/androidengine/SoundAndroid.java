package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.ISound;

import java.io.IOException;
import java.util.HashMap;

public class SoundAndroid implements ISound {

    private int myStreamId_;

    public SoundAndroid(String filename,AssetManager assetManager, SoundPool soundPool) {
        myStreamId_ = -1;
        try {
            AssetFileDescriptor assetDescriptor =
                    assetManager.openFd(filename);
            myStreamId_ = soundPool.load(assetDescriptor, 1);
 
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound.");
        }

    }

    public int getSoundId() {
      return myStreamId_;
    }

}
