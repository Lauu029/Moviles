package com.example.desktopengine;

import com.example.engine.IFile;

public class FileDesktop implements IFile {
    String path="Assets/Saved/saved_game.txt";
    @Override
    public String getPath() {
        return path;
    }
}
