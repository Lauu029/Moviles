package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.ISound;

import java.io.IOException;

public class SoundAndroid implements ISound {

    private int myStreamId_;
    private String path_ = "Audio/"; // Ruta predeterminada para buscar archivos de sonido

    public SoundAndroid(String filename, AssetManager assetManager, SoundPool soundPool) {
        myStreamId_ = -1;
        try {
            AssetFileDescriptor assetDescriptor =
                    assetManager.openFd(path_+filename);
            myStreamId_ = soundPool.load(assetDescriptor, 1);

        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound.");
        }
    }

    public int getSoundId() {
        return myStreamId_;
    }
}
