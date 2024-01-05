package com.example.androidengine;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.engine.IFile;

import java.io.IOException;
import java.io.InputStream;

public class FileAndroid implements IFile {
    String path="Saved/saved_game.txt";
    private AssetManager myAssetManager_;
    public FileAndroid(Context cont){
        myAssetManager_= cont.getAssets();
    }
    @Override
    public String getPath() {
        InputStream is = null;
        try {
            is = this.myAssetManager_.open(path); //Abre el archivo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
