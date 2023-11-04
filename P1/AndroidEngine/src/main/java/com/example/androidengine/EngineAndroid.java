package com.example.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;
import android.view.SurfaceView;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

public class EngineAndroid implements IEngine, Runnable {
    private GraphicsAndroid myGraphics; // Clase para graficos
    private SurfaceView myView; // Ventana de la aplicacion
    private Thread myRenderThread; // Hilo para renderizar
    private boolean running; // Indica si el motor está en funcionamiento
    private IScene myScene; // Escena actual
    private AudioAndroid myAudio; // Clase para audio
    private IInput myInput; // Clase para la entrada (input)
    private AssetManager myAssetManager; //Variable para gestión de assets
    private SoundPool mySoundPool; //Variable para gestión de clips de audio

    // Constructor de la clase
    public EngineAndroid(SurfaceView myView) {
        this.myView = myView; // Asigna la ventana proporcionada
        running = false; // Inicializa el motor como no en funcionamiento
        myInput = new InputAndroid(this.myView); // Inicializa la clase de entrada (input)
        myAssetManager = myView.getContext().getAssets(); //Obtiene la referencia a los assets
        myGraphics = new GraphicsAndroid(this.myView, myAssetManager); // Inicializa la clase de graficos
        myAudio = new AudioAndroid(myAssetManager, mySoundPool); // Inicializa la clase de audio
    }

    // Metodo para reanudar la ejecución del motor
    public void resume() {
        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            this.running = true; // Marca el motor como en funcionamiento
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.myRenderThread = new Thread(this);
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

    // Metodo que ejecuta el bucle principal de juego
    @Override
    public void run() {
        if (myRenderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha muy rápido, la vista podría todavía
        // no estar inicializada.Espera activa
        while (this.running && this.myView.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();

        // Bucle de juego principal.
        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //Tiempo entre frames
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            //Reescalado de la escena (por si ha aumentado o reducido su tamaño)
            myGraphics.resize(myScene.getWidth(), myScene.getHeight());
            //Reescalado del input dentro de la escena
            for (TouchEvent event : myInput.getTouchEvent()) {
                event.x -= myGraphics.getTranslateX();
                event.y -= myGraphics.getTranslateY();
                event.x /= myGraphics.getScale();
                event.y /= myGraphics.getScale();
            }
            //Gestión de eventos de input
            this.myScene.handleInput(myInput.getTouchEvent());
            myInput.myEventsClear();
            //Update de la escena
            this.myScene.update(elapsedTime);
            //Renderizado de la escena
            myGraphics.prepareFrame();
            myScene.render();
            myGraphics.endFrame();
        }
    }

    //Guardamos e inicializamos la escena que vamos a ejecutar
    @Override
    public void setScene(IScene myIScene) {
        this.myScene = myIScene;
        myScene.init();
    }

    //Devuelve el objeto que gestiona los gráficos
    @Override
    public IGraphics getGraphics() {
        return myGraphics;
    }

    //Devuelve el objeto que gestiona el input
    @Override
    public IInput getInput() {
        return this.myInput;
    }

    //Devuelve el objeto que gestiona el audio
    @Override
    public IAudio getAudio() {
        return this.myAudio;
    }

    //Devuelve la escena que se esta ejecutando
    @Override
    public IScene getScene() {
        return this.myScene;
    }
}
