package com.example.gamelogic.Managers;
import com.example.engine.IAudio;
import com.example.engine.ISound;
import com.example.gamelogic.Scenes.SceneNames;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance_;
    private IAudio audioEngine_;
    Map<SceneNames,ISound> musicMap_= new HashMap<>();
    private AudioManager() {
        // Constructor privado
    }
    public static AudioManager getInstance_() {
        return instance_;
    }
    public static int init() {
        instance_ = new AudioManager();
        return 1;
    }
    public void setAudioEngine(IAudio e)
    {
        instance_.audioEngine_=e;
    }
    public void addSceneMusic(SceneNames sceneName,ISound songName){
        musicMap_.put(sceneName,songName);
    }
    public void playSceneMusic(SceneNames nombre){
        if(musicMap_.containsKey(nombre))
        {
            ISound sound= musicMap_.get(nombre);
            audioEngine_.playSound(sound,0);
        }
    }
    public void stopSceneMusic(SceneNames nombre){
        if(musicMap_.containsKey(nombre))
        {
            ISound sound= musicMap_.get(nombre);
            audioEngine_.stopSound(sound);
        }
    }
}
