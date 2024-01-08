package com.example.desktopengine;

import com.example.engine.IFile;

public class FileDesktop implements IFile {
    String path_;
    public FileDesktop(String filepath){
        path_=filepath;
    }
    @Override
    public String readFile() {

        return "";
    }
    @Override
    public void writeFile(String  data) {

    }
}
