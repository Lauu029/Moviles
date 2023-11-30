package com.example.androidengine;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManager {
    private AssetManager myAssetManager_;

    FileManager(AssetManager assetManager) {
        myAssetManager_ = assetManager;
    }

    public InputStream getInputStream(String f) {
        InputStream fInput_;

        try {
            fInput_ =  this.myAssetManager_.open(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return fInput_;
    }

    public OutputStream getOutputStream(String f) {
        OutputStream fOutput_;
        try {
            fOutput_ = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fOutput_;
    }

}
