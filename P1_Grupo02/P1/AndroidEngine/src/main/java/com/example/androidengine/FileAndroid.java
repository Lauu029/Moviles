package com.example.androidengine;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.engine.IFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileAndroid implements IFile {
    private AssetManager myAssetManager_;
    String path_;
    String outputPath_;
    Context context_;
    public FileAndroid(AssetManager assetManager , Context context, String path){
        myAssetManager_=assetManager;
        context_=context;
        path_=path;
    }

    @Override
    public String readFile() {
        String readString="";
        if(context_.getFileStreamPath(path_).exists())
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(context_.openFileInput(path_)));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                return stringBuilder.toString();
            }
            catch (IOException e)
            {

            }
        }
        return readString;
    }

    @Override
    public void writeFile(String data) {

        try
        {   OutputStream outputStream=context_.openFileOutput(path_,Context.MODE_PRIVATE);
            byte [] allBytes=data.getBytes();
            outputStream.write(allBytes);
            outputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void emptyFile() {
        if(context_.getFileStreamPath(path_).exists())
        {
            try
            {
                String empty="";
                OutputStream outputStream = context_.openFileOutput(path_,Context.MODE_PRIVATE);
                byte[] datos=empty.getBytes();
                outputStream.write(datos);
                outputStream.close();

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
