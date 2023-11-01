package com.example.engine;

public interface IAudio {
    ISound newSound (String file, String id);
    void playSound(ISound sound,int loop);
    void stopSound(ISound sound);
}
