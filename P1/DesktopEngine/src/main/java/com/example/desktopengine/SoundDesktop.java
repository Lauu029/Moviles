package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundDesktop implements ISound {
    private String path_ = "Assets/"; // Ruta predeterminada para buscar archivos de sonido
    private Clip myClip_; // Objeto Clip para representar el sonido

    // Constructor de la clase
    public SoundDesktop(String file, String id) {
        myClip_ = null;
        try {
            File audioFile = new File(path_ + file); // Crea un objeto File para el archivo de sonido
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // Obtiene un flujo de audio a partir del archivo
            myClip_ = AudioSystem.getClip(); // Crea un nuevo Clip
            myClip_.open(audioStream); // Abre el Clip y carga el audio desde el flujo

        } catch (Exception e) {
            System.err.println("Couldn't load audio file"); // Imprime un mensaje de error si no se puede cargar el archivo de audio
            e.printStackTrace(); // Imprime la traza de la excepcion
        }
    }

    // Método para obtener el Clip de sonido
    public Clip getClip() {
        Clip currClip = myClip_;
        if (currClip.isOpen()) {
            return currClip; // Devuelve el Clip si está abierto y listo para su reproducción
        } else {
            System.out.print("The sound you're trying to play couldn't be found\n");
            return null; // Devuelve nulo si el Clip no se pudo encontrar
        }
    }
}
