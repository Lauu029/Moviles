package com.example.androidengine;

public class NDKManager {
    public static native String generateHash(String data);
    static{
        System.loadLibrary("hashGenerator");
    }
}
