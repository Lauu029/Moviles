package com.example.desktopengine;

import com.example.engine.IFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileDesktop implements IFile {
    String path_;
    public FileDesktop(String filepath){
        path_=filepath;
    }
    @Override
    public String readFile() {
        String readString="";
        try {
            InputStream inputStream= new FileInputStream(path_);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            return stringBuilder.toString();
        }catch(IOException e)
        {
            //e.printStackTrace();
        }

        return readString;
    }
    @Override
    public void writeFile(String  data) {
        try
        {
            OutputStream outputStream = new FileOutputStream(path_);
            byte[] datos=data.getBytes();
            outputStream.write(datos);
            outputStream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void emptyFile() {
        try
        {
            String empty="";
            OutputStream outputStream = new FileOutputStream(path_);
            byte[] datos=empty.getBytes();
            outputStream.write(datos);
            outputStream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
