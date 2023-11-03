package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class SoundDesktop implements ISound {
    private String path_ = "Assets/"; // Ruta predeterminada para buscar archivos de sonido
    private Clip myClip_; // Objeto Clip para representar el sonido

    // Constructor de la clase
    private ArrayList<Clip> myFreeSounds_;  // Lista de sonidos libres
    private ArrayList<Clip> myUsedSounds_;  // Lista de sonidos en uso
    int maxSounds_;
    public SoundDesktop(String file, String id) {

        maxSounds_=5;
        myFreeSounds_= new ArrayList<Clip>(); //inicializa la lista que contiene los sonidos disponibles
        myUsedSounds_= new ArrayList<Clip>(); //inicializa la lista donde esten los sonidos en uso

        try {
            File audioFile = new File(path_ + file); // Crea un objeto File para el archivo de sonido
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // Obtiene un flujo de audio a partir del archivo
            for(int i=0;i<maxSounds_;i++){
                Clip c=AudioSystem.getClip();
                c.open(audioStream); // Abre el Clip y carga el audio desde el flujo
                myFreeSounds_.add(c);
            }
        } catch (Exception e) {
            System.err.println("Couldn't load audio file"); // Imprime un mensaje de error si no se puede cargar el archivo de audio
            e.printStackTrace(); // Imprime la traza de la excepcion
        }
    }
    // Método para obtener el Clip de sonido
    /*public Clip getUsedClip() {

        if (myClip_.isOpen()) {
            return myClip_; // Devuelve el Clip si está abierto y listo para su reproducción
        }
        else {
            throw new IllegalStateException("No hay ningún clip de est sonido en uso.");
        }
    }*/
    public Clip getFreeClip(){
        if(!myFreeSounds_.isEmpty()) { //Si hay hueco para crear un nuevo sonido
            Clip c=myFreeSounds_.remove(myFreeSounds_.size() - 1);
            myUsedSounds_.add(c);
            return c; //Quitamos el hueco disponible
        } else {
            return null;
        }
    }
    private void returnClip(Clip c) {
        if (myUsedSounds_.contains(c)) {
            myUsedSounds_.remove(c);
            myFreeSounds_.add(c);
        } else {
            throw new IllegalArgumentException("El objeto no está en uso.");
        }
    }

}
