package com.example.androidengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.TreeMap;

public class FileManager {
    private AssetManager myAssetManager_;
    Context myContext_;
    FileManager(AssetManager assetManager, Context myContext) {
        myAssetManager_ = assetManager;
        myContext_=myContext;
    }

    public InputStream getInputStream(String f) {
        InputStream fInput_;

        try {
            fInput_ =  this.myAssetManager_.open(f);

        } catch (IOException e) {
            Log.d("MainActivity", "jolin "+f);
            throw new RuntimeException(e);
        }


        return fInput_;
    }

    public TreeMap<String, InputStream> getFilesInFolder(String folderPath) {
        TreeMap<String, InputStream> fileStreamsMap = new TreeMap<>();

        try {
            String[] fileNames = myAssetManager_.list(folderPath);

            if (fileNames != null) {
                Log.d("MainActivity", "Size:" + fileNames.length);
                for (String fileName : fileNames) {
                    String filePath = folderPath + fileName;
                    InputStream fileStream = myAssetManager_.open(filePath);
                    fileStreamsMap.put(fileName, fileStream);

                    // Realizar operaciones con el InputStream, por ejemplo, leer el contenido del archivo
                    // ...

                    // No olvides cerrar el InputStream cuando hayas terminado con él
                    // fileStream.close(); // Puedes cerrar el InputStream aquí o donde lo utilices

                    Log.d("MainActivity", "Abierto y leído: " + filePath);
                }
            }
        } catch (IOException e) {
            Log.d("MainActivity", "Error al obtener archivos en la carpeta: " + folderPath);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return fileStreamsMap;
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
