package com.example.engine;

public interface IAudio {
    void newSound (String file, String id);
    void playSound(String id);
    void stopSound(String id);
}
