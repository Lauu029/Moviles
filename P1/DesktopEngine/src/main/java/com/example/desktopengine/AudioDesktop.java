package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;
import javax.sound.sampled.Clip;

public class AudioDesktop implements IAudio {
    // Implementacion de la interfaz IAudio

    // Metodo para crear un nuevo objeto de sonido (SoundDesktop)
    @Override
    public SoundDesktop newSound(String file, String id) {
        return new SoundDesktop(file, id);
    }

    // Método para reproducir un sonido
    @Override
    public void playSound(ISound sound, int loop) {
        SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop
        Clip clip = dSound.getClip(); // Obtiene el clip de sonido asociado

        if (clip != null) { // Verifica si el clip no es nulo
            if (loop != 0) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle si loop no es igual a 0
            }
            clip.setFramePosition(0); // Establece la posicion de reproduccion al principio
            clip.start(); // Inicia la reproducción del sonido
        }
    }

    // Metodo para detener la reproducción de un sonido
    @Override
    public void stopSound(ISound sound) {
        SoundDesktop dSound = (SoundDesktop) sound; // Convierte el objeto ISound a SoundDesktop
        Clip clip = dSound.getClip(); // Obtiene el clip de sonido asociado

        if (clip != null) { // Verifica si el clip no es nulo
            clip.stop(); // Detiene la reproducción del sonido
            clip.close(); // Cierra el clip para liberar recursos
        }
    }
}
