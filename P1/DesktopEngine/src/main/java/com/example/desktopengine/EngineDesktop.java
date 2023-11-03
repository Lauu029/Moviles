package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import javax.swing.JFrame;

public class EngineDesktop implements IEngine, Runnable {
    private JFrame myView_; // Ventana de la aplicacion
    private Thread myRenderThread_; // Hilo para renderizar
    private boolean running_; // Indica si el motor está en funcionamiento
    private IScene myScene_; // Escena actual
    private GraphicsDesktop myGraphics_; // Clase para graficos
    private AudioDesktop myAudio_; // Clase para audio
    private IInput myInput_; // Clase para la entrada (input)
    private int maxDesktopSounds_;
    // Constructor de la clase
    public EngineDesktop(JFrame myView) {
        maxDesktopSounds_=10; //Número máximo de sonidos que pueden sonar de forma simultánea para desktop
        myView_ = myView; // Asigna la ventana proporcionada
        running_ = false; // Inicializa el motor como no en funcionamiento
        myGraphics_ = new GraphicsDesktop(myView_); // Inicializa la clase de graficos
        myAudio_ = new AudioDesktop(maxDesktopSounds_); // Inicializa la clase de audio
        myInput_ = new InputDesktop(myView_); // Inicializa la clase de entrada (input)
        System.out.print("Window Width: " + myView_.getWidth() + " Window Height: " + myView_.getHeight() + "\n"); // Imprime las dimensiones de la ventana
    }

    // Metodo para reanudar la ejecución del motor
    public void resume() {
        if (!this.running_) {
            this.running_ = true; // Marca el motor como en funcionamiento

            this.myRenderThread_ = new Thread(this); // Crea un nuevo hilo para renderizar
            this.myRenderThread_.start(); // Inicia el hilo
        }
    }

    // Metodo para pausar la ejecución del motor
    public void pause() {
        if (this.running_) {
            this.running_ = false; // Marca el motor como no en funcionamiento

            while (true) {
                try {
                    this.myRenderThread_.join(); // Espera a que el hilo de renderizacion termine
                    this.myRenderThread_ = null;
                    break;
                } catch (InterruptedException ie) {

                }
            }
        }
    }

    @Override
    public void run() {
        if (myRenderThread_ != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }

        // Espera a que la ventana esté inicializada
        while (this.running_ && this.myView_.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime;
        int frames = 0;

        // Bucle principal del juego
        while (running_) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            // Escalado del canvas y del input
            myGraphics_.resize(myScene_.getWidth(), myScene_.getHeight());

            for (TouchEvent event : myInput_.getTouchEvent()) {
                event.x -= myGraphics_.getTranslateX_();
                event.y -= myGraphics_.getTranslateY_();
                event.x /= myGraphics_.getScale_();
                event.y /= myGraphics_.getScale_();
            }

            // Manejo de entrada por parte de la escena
            myScene_.handleInput(myInput_.getTouchEvent());

            // Limpieza de eventos de entrada
            myInput_.myEventsClear();

            // Actualizacion de la escena
            this.myScene_.update(elapsedTime);

            // Preparación del frame
            myGraphics_.prepareFrame();

            // Renderizacion de la escena
            myScene_.render();

            // pintado del frame
            myGraphics_.endFrame();

            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                frames = 0;
                informePrevio = currentTime;
            }
        }
    }

    @Override
    public void setScene(IScene myIScene) {
        this.myScene_ = myIScene; // Establece la escena actual
        myScene_.init(); // Inicializa la escena
    }

    @Override
    public IGraphics getGraphics() {
        return this.myGraphics_; // Devuelve la clase de graficos
    }

    @Override
    public IInput getInput() {
        return this.myInput_; // Devuelve la clase de entrada (input)
    }

    @Override
    public IAudio getAudio() {
        return this.myAudio_; // Devuelve la clase de audio
    }

    @Override
    public IScene getScene() {
        return this.myScene_; // Devuelve la escena actual
    }
}
