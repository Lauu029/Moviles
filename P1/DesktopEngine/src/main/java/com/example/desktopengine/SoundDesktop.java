package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SoundDesktop implements ISound {
    private String path = "Assets/"; // Ruta predeterminada para buscar archivos de sonido

    // Constructor de la clase
    private ArrayList<Clip> myFreeSounds;  // Lista de sonidos libres
    private ArrayList<Clip> myUsedSounds;  // Lista de sonidos en uso
    private AudioInputStream myAudioStream;
    public SoundDesktop(String file) {
        int maxSounds=10; //Maximo 10 sonidos iguales acorde con nuestro soundpool de android
        myFreeSounds = new ArrayList<Clip>(); //inicializa la lista que contiene los sonidos disponibles
        myUsedSounds = new ArrayList<Clip>(); //inicializa la lista donde esten los sonidos en uso
        try {
            File audioFile = new File(path + file); // Crea un objeto File para el archivo de sonido
            for(int i=0;i<maxSounds;i++){
                myAudioStream = AudioSystem.getAudioInputStream(audioFile); // Obtiene un flujo de audio a partir del archivo
                Clip c=AudioSystem.getClip();
                c.open(myAudioStream); // Abre el Clip y carga el audio desde el flujo
                if(c.isOpen())
                    myFreeSounds.add(c);
            }
        } catch (Exception e) {
            System.err.println("Couldn't load audio file"); // Imprime un mensaje de error si no se puede cargar el archivo de audio
            e.printStackTrace(); // Imprime la traza de la excepcion
        }
    }
    // Método para obtener un Clip de sonido que no este en uso
    public Clip getFreeClip(){
        if(!myFreeSounds.isEmpty()) { //Si hay hueco para crear un nuevo sonido
            Clip c= myFreeSounds.remove(myFreeSounds.size() - 1);
            myUsedSounds.add(c);
            return c; //Quitamos el hueco disponible
        } else {
            System.out.print("No hay mas clips disponibles\n");
            return null;
        }
    }
    // Método para devolver un Clip de sonido que se haya usado
    public void returnClip(Clip c){
        if (myUsedSounds.contains(c)) {
            myUsedSounds.remove(c);
            try {
                c.open(myAudioStream);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(c.isOpen())
            {
                myFreeSounds.add(c);
                System.out.println("lips disponibles "+myFreeSounds.size());
            } else {
                throw new IllegalArgumentException("El objeto no está en uso.");
            }
        }
    }
}
