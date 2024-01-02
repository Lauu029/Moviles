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
    private String path_ = "Assets/Audio/"; // Ruta predeterminada para buscar archivos de sonido
    private ArrayList<Clip> myFreeSounds_;  // Lista de sonidos libres
    private ArrayList<Clip> myUsedSounds_;  // Lista de sonidos en uso
    private AudioInputStream myAudioStream_;
    // Constructor de la clase
    public SoundDesktop(String file) {
        int maxSounds=10;
        myFreeSounds_ = new ArrayList<Clip>(); //inicializa la lista que contiene los sonidos disponibles
        myUsedSounds_ = new ArrayList<Clip>(); //inicializa la lista donde esten los sonidos en uso
        try {
            File audioFile = new File(path_ + file); // Crea un objeto File para el archivo de sonido
            for(int i=0;i<maxSounds;i++){

                myAudioStream_ = AudioSystem.getAudioInputStream(audioFile); // Obtiene un flujo de audio a partir del archivo
                myAudioStream_.mark((int)audioFile.length()); //Marca la posición actual en el flujo de audio como el inicio del archivo.
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
    //Metodo que quita un CLip del array de sonidos libres, lo agrega al de sonidos en uso y lo devuelve
    private Clip checkForAudioCLip(){
        Clip c= myFreeSounds_.remove(myFreeSounds_.size() - 1);//Quitamos un sonido libre
        myUsedSounds_.add(c); //Lo agregamos a los sonidos en uso
        return c;
    }
    // Método para obtener un Clip de sonido que no este en uso
    public Clip getFreeClip(){
        if(!myFreeSounds_.isEmpty()) { //Si hay hueco para crear un nuevo sonido
            return checkForAudioCLip();
        } else {
            if(!myUsedSounds_.isEmpty())
            {
                Clip oldCLip=myUsedSounds_.get(0);
                returnClip(oldCLip);
                return checkForAudioCLip();
            }
            else return null;
        }
    }
    // Método para devolver un Clip de sonido que se haya usado
    public void returnClip(Clip c){

        if (myUsedSounds_.contains(c)) { //Si el sonido ha sido usado
                myUsedSounds_.remove(c); //Lo eliminamos de la lista de usados
            try {
                myAudioStream_.reset(); //Reestablecemos el flujo de audio
                if(!c.isOpen())
                    c.open(myAudioStream_); //Abrimos el clip (por si ha sido cerrado con un stopSound)
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(c.isOpen()) //Si se ha abierto correctamente lo agregamos a los sonidos que se pueden usar
                {
                    myFreeSounds_.add(c);
                }
            }
        }
    }

