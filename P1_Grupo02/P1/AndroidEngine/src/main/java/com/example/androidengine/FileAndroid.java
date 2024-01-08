package com.example.androidengine;

import android.content.res.AssetManager;

import com.example.engine.IFile;

public class FileAndroid implements IFile {
    private AssetManager myAssetManager_;
    String path_;
    public FileAndroid(AssetManager assetManager ,String path){
        myAssetManager_=assetManager;
        path_=path;
    }

    @Override
    public String readFile() {
        return "";
    }

    @Override
    public void writeFile(String data) {

    }
}
