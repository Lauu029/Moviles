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

import java.util.Arrays;
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

            throw new RuntimeException(e);
        }


        return fInput_;
    }
    //te devuelve un array de las carpetas que hay dentro de una carpeta
    public String[] getFolderNamesInFolder(String parentFolderPath) {
        String[] folderNames = null;

        try {
            String[] fileNames = myAssetManager_.list(parentFolderPath);

            if (fileNames != null) {
                // Filtra solo los nombres de carpetas
                folderNames = Arrays.stream(fileNames)
                        .filter(fileName -> isDirectory(parentFolderPath + fileName))
                        .toArray(String[]::new);
            }
        } catch (IOException e) {
            Log.d("MainActivity", "Error al obtener carpetas en la carpeta: " + parentFolderPath);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return folderNames;
    }
    //metodo para saber si la ruta "path" es una carpeta
    private boolean isDirectory(String path) {
        try {
            // Intenta abrir el elemento como un InputStream
            InputStream fileStream = myAssetManager_.open(path);

            // Si se logra abrir, es un archivo, así que cierra el InputStream y devuelve falso
            if (fileStream != null) {
                fileStream.close();
                return false;
            }
        } catch (IOException e) {
            // Si no se puede abrir como InputStream, es probable que sea un directorio
            return true;
        }

        return false;
    }
    //te devuelve los files dentro de una carpeta dada
    public TreeMap<String, InputStream> getFilesInFolder(String folderPath) {
        TreeMap<String, InputStream> fileStreamsMap = new TreeMap<>();

        try {
            String[] fileNames = myAssetManager_.list(folderPath);

            if (fileNames != null) {

                for (String fileName : fileNames) {
                    String filePath = folderPath + fileName;
                    InputStream fileStream = myAssetManager_.open(filePath);
                    fileStreamsMap.put(fileName, fileStream);
                }
            }
        } catch (IOException e) {
            Log.d("MainActivity", "Error al obtener archivos en la carpeta: " + folderPath);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return fileStreamsMap;
    }


    public OutputStream getOutputStream(String f,boolean privateMode) {
        OutputStream fOutput_;
        try {
            if(privateMode)
            {
                fOutput_ = myContext_.openFileOutput(f,myContext_.MODE_PRIVATE);
            }
            else fOutput_ = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fOutput_;
    }

}
