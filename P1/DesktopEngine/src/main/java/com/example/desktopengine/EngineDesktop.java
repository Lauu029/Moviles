package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import javax.swing.JFrame;

public class EngineDesktop implements IEngine, Runnable {
    private JFrame myView; // Ventana de la aplicacion
    private Thread myRenderThread; // Hilo para renderizar
    private boolean running; // Indica si el motor está en funcionamiento
    private IScene myScene; // Escena actual
    private GraphicsDesktop myGraphics; // Clase para graficos
    private AudioDesktop myAudio; // Clase para audio
    private IInput myInput; // Clase para la entrada (input)

    // Constructor de la clase
    public EngineDesktop(JFrame myView) {
        this.myView = myView; // Asigna la ventana proporcionada
        running = false; // Inicializa el motor como no en funcionamiento
        myGraphics = new GraphicsDesktop(this.myView); // Inicializa la clase de graficos
        myAudio = new AudioDesktop(); // Inicializa la clase de audio
        myInput = new InputDesktop(this.myView); // Inicializa la clase de entrada (input)
        System.out.print("Window Width: " + this.myView.getWidth() + " Window Height: " + this.myView.getHeight() + "\n"); // Imprime las dimensiones de la ventana
    }

    // Metodo para reanudar la ejecución del motor
    public void resume() {
        if (!this.running) {
            this.running = true; // Marca el motor como en funcionamiento
            this.myRenderThread = new Thread(this); // Crea un nuevo hilo para renderizar
            this.myRenderThread.start(); // Inicia el hilo
        }
    }

    // Metodo para pausar la ejecución del motor
    public void pause() {
        if (this.running) {
            this.running = false; // Marca el motor como no en funcionamiento

            while (true) {
                try {
                    this.myRenderThread.join(); // Espera a que el hilo de renderizacion termine
                    this.myRenderThread = null;
                    break;
                } catch (InterruptedException ie) {

                }
            }
        }
    }

    @Override
    public void run() {
        if (myRenderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }

        // Espera a que la ventana esté inicializada
        while (this.running && this.myView.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime;
        int frames = 0;

        // Bucle principal del juego
        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            // Escalado del canvas y del input
            myGraphics.resize(myScene.getWidth(), myScene.getHeight());

            for (TouchEvent event : myInput.getTouchEvent()) {
                event.x -= myGraphics.getTranslateX();
                event.y -= myGraphics.getTranslateY();
                event.x /= myGraphics.getScale();
                event.y /= myGraphics.getScale();
            }

            // Manejo de entrada por parte de la escena
            myScene.handleInput(myInput.getTouchEvent());

            // Limpieza de eventos de entrada
            myInput.myEventsClear();

            // Actualizacion de la escena
            this.myScene.update(elapsedTime);

            // Preparación del frame
            myGraphics.prepareFrame();

            // Renderizacion de la escena
            myScene.render();

            // pintado del frame
            myGraphics.endFrame();

            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                frames = 0;
                informePrevio = currentTime;
            }
        }
    }

    @Override
    public void setScene(IScene myIScene) {
        this.myScene = myIScene; // Establece la escena actual
        myScene.init(); // Inicializa la escena
    }

    @Override
    public IGraphics getGraphics() {
        return this.myGraphics; // Devuelve la clase de graficos
    }

    @Override
    public IInput getInput() {
        return this.myInput; // Devuelve la clase de entrada (input)
    }

    @Override
    public IAudio getAudio() {
        return this.myAudio; // Devuelve la clase de audio
    }

    @Override
    public IScene getScene() {
        return this.myScene; // Devuelve la escena actual
    }
}
