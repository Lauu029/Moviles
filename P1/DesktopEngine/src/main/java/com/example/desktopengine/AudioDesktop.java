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
    private ArrayList<Clip> myFreeSounds_;  // Lista de sonidos libres
    private ArrayList<Clip> myUsedSounds_;  // Lista de sonidos en uso
    // Metodo para crear un nuevo objeto de sonido (SoundDesktop)
    int maxSounds_;
    public AudioDesktop(int maxSounds){
        maxSounds_=maxSounds;
        myFreeSounds_= new ArrayList<Clip>(); //inicializa la lista que contiene los sonidos disponibles
        myUsedSounds_= new ArrayList<Clip>(); //inicializa la lista donde esten los sonidos en uso

        for(int i=0;i<maxSounds_;i++){
            myFreeSounds_.add(new Clip() {
                @Override
                public void open(AudioFormat audioFormat, byte[] bytes, int i, int i1) throws LineUnavailableException {

                }

                @Override
                public void open(AudioInputStream audioInputStream) throws LineUnavailableException, IOException {

                }

                @Override
                public int getFrameLength() {
                    return 0;
                }

                @Override
                public long getMicrosecondLength() {
                    return 0;
                }

                @Override
                public void setFramePosition(int i) {

                }

                @Override
                public void setMicrosecondPosition(long l) {

                }

                @Override
                public void setLoopPoints(int i, int i1) {

                }

                @Override
                public void loop(int i) {

                }

                @Override
                public void drain() {

                }

                @Override
                public void flush() {

                }

                @Override
                public void start() {

                }

                @Override
                public void stop() {

                }

                @Override
                public boolean isRunning() {
                    return false;
                }

                @Override
                public boolean isActive() {
                    return false;
                }

                @Override
                public AudioFormat getFormat() {
                    return null;
                }

                @Override
                public int getBufferSize() {
                    return 0;
                }

                @Override
                public int available() {
                    return 0;
                }

                @Override
                public int getFramePosition() {
                    return 0;
                }

                @Override
                public long getLongFramePosition() {
                    return 0;
                }

                @Override
                public long getMicrosecondPosition() {
                    return 0;
                }

                @Override
                public float getLevel() {
                    return 0;
                }

                @Override
                public Line.Info getLineInfo() {
                    return null;
                }

                @Override
                public void open() throws LineUnavailableException {

                }

                @Override
                public void close() {

                }

                @Override
                public boolean isOpen() {
                    return false;
                }

                @Override
                public Control[] getControls() {
                    return new Control[0];
                }

                @Override
                public boolean isControlSupported(Control.Type type) {
                    return false;
                }

                @Override
                public Control getControl(Control.Type type) {
                    return null;
                }

                @Override
                public void addLineListener(LineListener lineListener) {

                }

                @Override
                public void removeLineListener(LineListener lineListener) {

                }
            });
        }
    }
    public Clip getFreeClip(){
        if(!myFreeSounds_.isEmpty()) { //Si hay hueco para crear un nuevo sonido
            return myFreeSounds_.remove(myFreeSounds_.size() - 1); //Quitamos el hueco disponible
        } else {
            throw new IllegalStateException("No hay objetos disponibles en el pool.");
        }
    }
    private void returnClip(Clip c) {
        if (myUsedSounds_.contains(c)) {
            myUsedSounds_.remove(c);
            myUsedSounds_.add(c);
        } else {
            throw new IllegalArgumentException("El objeto no está en uso.");
        }
    }
    @Override
    public SoundDesktop newSound(String file, String id) {
        SoundDesktop sound= new SoundDesktop(file,id);
        return sound;

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

        if (clip != null && myUsedSounds_.contains(clip)) { // Verifica si el clip no es nulo y esta en uso
            myUsedSounds_.remove(clip);
            myFreeSounds_.add(clip);
            clip.stop(); // Detiene la reproducción del sonido
            clip.close(); // Cierra el clip para liberar recursos
        }else {
            throw new IllegalArgumentException("El sonido no se puede pausar, no está en uso.");
        }
    }
}
