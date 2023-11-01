package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundDesktop implements ISound {
    private HashMap<String, Clip> mySounds_;
    private String path_="Assets/";
    private Clip myClip_;
    SoundDesktop(String file, String id){
        mySounds_ = new HashMap<>();
        myClip_=null;
        try {
            File audioFile = new File(path_+file);
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(audioFile);
            myClip_ = AudioSystem.getClip();
            myClip_.open(audioStream);
            mySounds_.put(id,myClip_);

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }
    public Clip getClip(){
        Clip currClip=myClip_;
        if(mySounds_.containsValue(currClip)&& currClip.isOpen()){
            return currClip;
        }
        else{
            System.out.print("The sound you're trying to play couldn't be found\n");
            return null;
        }
    }
}
