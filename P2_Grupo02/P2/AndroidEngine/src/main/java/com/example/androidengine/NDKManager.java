package com.example.androidengine;

public class NDKManager {
    //genera el hash de el string que se le pase
    public static native String generateHash(String data);
    static{
        System.loadLibrary("main");
    }
}
