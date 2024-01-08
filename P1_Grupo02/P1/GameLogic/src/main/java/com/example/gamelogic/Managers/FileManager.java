package com.example.gamelogic.Managers;

import com.example.engine.IFile;

public class FileManager {
    private static FileManager instance_;
    private IFile fileReader_;
    private FileManager() {
        // Constructor privado
    }
    public static FileManager getInstance_() {
        return instance_;
    }
    public static int init(IFile file) {
        instance_ = new FileManager();
        instance_.fileReader_=file;
        return 1;
    }
    public IFile getFile(String ruta){
        return fileReader_;
    }

}
