package com.example.engine;

public interface IAudio {
    ISound playSound(String file, boolean loop);

    void stopSound(String file);
}
