package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class AudioDesktop implements IAudio {
    // Implementacion de la interfaz IAudio

    // Metodo para crear un nuevo objeto de sonido (SoundDesktop)
    int i=1;

    @Override
    public SoundDesktop newSound(String file, String id) {
        SoundDesktop sound= new SoundDesktop(file,id);
        return sound;

    }

    // Método para reproducir un sonido
    @Override
    public void playSound(ISound sound, int loop) {

        SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop
        Clip clip = dSound.getFreeClip(); // Obtiene el clip de sonido asociado

        if (clip != null) { // Verifica si el clip no es nulo
            if (loop != 0) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle si loop no es igual a 0
            }
            clip.setFramePosition(0); // Establece la posicion de reproduccion al principio

            clip.start(); // Inicia la reproducción del sonido

            System.out.print("Playing clip with id: "+i+"\n");
            i++;
        }
    }

    // Metodo para detener la reproducción de un sonido
   @Override
    public void stopSound(ISound sound) {
         /*SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop
        Clip clip = dSound.getClip(); // Obtiene el clip de sonido asociado

        if (clip != null && myUsedSounds_.contains(clip)) { // Verifica si el clip no es nulo y esta en uso
            myUsedSounds_.remove(clip);
            myFreeSounds_.add(clip);
            clip.stop(); // Detiene la reproducción del sonido
            clip.close(); // Cierra el clip para liberar recursos
        }else {
            throw new IllegalArgumentException("El sonido no se puede pausar, no está en uso.");
        }*/
    }
}
