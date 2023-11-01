package com.example.engine;

public interface IAudio {
    void newSound (String file, String id);
    void playSound(String id,boolean loop);
    void stopSound(String id);
}
