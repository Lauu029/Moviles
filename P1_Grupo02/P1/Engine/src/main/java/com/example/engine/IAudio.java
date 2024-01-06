package com.example.engine;

public interface IAudio {
    ISound newSound (String file); //Crea un sonido
    void playSound(ISound sound,int loop); //Reproduce un sonido
    void stopSound(ISound sound); //Detiene un sonido
    void muteAllSound(boolean mute);
}
