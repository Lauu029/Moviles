package com.example.desktopengine;

import com.example.engine.IFile;

public class FileDesktop implements IFile {
    String path="Assets/Saved/";
    @Override
    public String getPath() {
        return path;
    }
}
