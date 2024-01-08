package com.example.desktopengine;

import com.example.engine.IFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            InputStream inputStream= new BufferedInputStream(new FileInputStream(path_));
            StringBuilder builder= new StringBuilder();
            byte[] buff=new byte[1024];
            int bytesRead;
            while((bytesRead=inputStream.read(buff))!=-1)
            {
                builder.append(new String(buff,0,bytesRead, StandardCharsets.UTF_8));
            }
            readString=builder.toString();
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
}
