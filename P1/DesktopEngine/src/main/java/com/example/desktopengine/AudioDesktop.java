package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import javax.sound.sampled.Clip;

public class AudioDesktop implements IAudio {
    // Implementacion de la interfaz IAudio
    private Clip usedClip; //Clip que va a ser usado
    // Metodo para crear un nuevo objeto de sonido (SoundDesktop)
    @Override
    public SoundDesktop newSound(String file) {
        SoundDesktop sound= new SoundDesktop(file);
        return sound;
    }

    // Método para reproducir un sonido
    @Override
    public void playSound(ISound sound, int loop) {

        SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop
        usedClip = dSound.getFreeClip(); // Obtiene el clip de sonido asociado

        if (usedClip != null) { // Verifica si el clip no es nulo
            if (loop != 0) {
                usedClip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle si loop no es igual a 0
            }
            usedClip.setFramePosition(0); // Establece la posicion de reproduccion al principio

            usedClip.start(); // Inicia la reproducción del sonido
        }
    }

    // Metodo para detener la reproducción de un sonido
   @Override
    public void stopSound(ISound sound){
        SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop

        if (usedClip != null) { // Verifica si el clip no es nulo y esta en uso
            usedClip.stop(); // Detiene la reproducción del sonido
            usedClip.close(); // Cierra el clip para liberar recursos
            dSound.returnClip(usedClip);
        }else {
            throw new IllegalArgumentException("El sonido no se puede pausar, no está en uso.");
        }
    }
}
