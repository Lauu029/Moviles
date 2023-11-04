package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

    // Constructor de la clase
    private ArrayList<Clip> myFreeSounds_;  // Lista de sonidos libres
    private ArrayList<Clip> myUsedSounds_;  // Lista de sonidos en uso
    private AudioInputStream myAudioStream_;
    public SoundDesktop(String file) {

        int maxSounds=10; //Maximo 10 sonidos iguales acorde con nuestro soundpool de android
        myFreeSounds_= new ArrayList<Clip>(); //inicializa la lista que contiene los sonidos disponibles
        myUsedSounds_= new ArrayList<Clip>(); //inicializa la lista donde esten los sonidos en uso

        try {
            File audioFile = new File(path_ + file); // Crea un objeto File para el archivo de sonido

            for(int i=0;i<maxSounds;i++){
                myAudioStream_= AudioSystem.getAudioInputStream(audioFile); // Obtiene un flujo de audio a partir del archivo
                Clip c=AudioSystem.getClip();
                c.open(myAudioStream_); // Abre el Clip y carga el audio desde el flujo

                if(c.isOpen())
                    myFreeSounds_.add(c);
            }
        } catch (Exception e) {
            System.err.println("Couldn't load audio file"); // Imprime un mensaje de error si no se puede cargar el archivo de audio
            e.printStackTrace(); // Imprime la traza de la excepcion
        }
    }
    // Método para obtener un Clip de sonido que no este en uso
    public Clip getFreeClip(){
        if(!myFreeSounds_.isEmpty()) { //Si hay hueco para crear un nuevo sonido
            Clip c=myFreeSounds_.remove(myFreeSounds_.size() - 1);
            myUsedSounds_.add(c);
            return c; //Quitamos el hueco disponible
        } else {
            System.out.print("No hay mas clips disponibles");
            return null;
        }
    }
    // Método para devolver un Clip de sonido que se haya usado
    public void returnClip(Clip c){
        if (myUsedSounds_.contains(c)) {
            myUsedSounds_.remove(c);
            try {
                c.open(myAudioStream_);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(c.isOpen())
                myFreeSounds_.add(c);
        } else {
            throw new IllegalArgumentException("El objeto no está en uso.");
        }
    }

}
