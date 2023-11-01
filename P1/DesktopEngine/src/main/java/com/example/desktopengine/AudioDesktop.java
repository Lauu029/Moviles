package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioDesktop implements IAudio {
    @Override
    public SoundDesktop newSound(String file, String id) {

        return new SoundDesktop(file,id);
    }

    @Override
    public void playSound(ISound sound,boolean loop) {
        SoundDesktop dSound=(SoundDesktop) sound;
        Clip clip=dSound.getClip();
        if(clip!=null){
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.setFramePosition(0);
            clip.start();
        }
    }

    @Override
    public void stopSound(ISound sound) {
        SoundDesktop dSound=(SoundDesktop) sound;
        Clip clip=dSound.getClip();
        if(clip!=null){
            clip.stop();
            clip.close();
        }
    }
}
