package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioDesktop implements IAudio {
    private HashMap<String, Clip> mySounds_;
    private String path="assets/sounds/";
    public AudioDesktop() {
        mySounds_ = new HashMap<>();
    }
    @Override
    public void newSound(String file, String id) {
        try {
            File audioFile = new File(file);
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            mySounds_.put(id,clip);

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    @Override
    public void playSound(String id,boolean loop) {
        if(mySounds_.containsKey(id)){
            Clip clip=mySounds_.get(id);
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.setFramePosition(0);
            clip.start();
        }
        else{
            System.out.print("The sound you're trying to play couldn't be found\n");
        }
    }

    @Override
    public void stopSound(String id) {
        if(mySounds_.containsKey(id)){
            Clip clip=mySounds_.get(id);
            clip.stop();
            clip.close();
            mySounds_.remove(id);
        }
    }
}
