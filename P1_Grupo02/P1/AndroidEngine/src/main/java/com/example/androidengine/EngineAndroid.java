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
    private GraphicsAndroid myGraphics_; // Clase para graficos
    private SurfaceView myView_; // Ventana de la aplicacion
    private Thread myRenderThread_; // Hilo para renderizar
    private boolean running_; // Indica si el motor está en funcionamiento
    private IScene myScene_; // Escena actual
    private AudioAndroid myAudio; // Clase para audio
    private IInput myInput_; // Clase para la entrada (input)
    private AssetManager myAssetManager_; //Variable para gestión de assets
    private SoundPool mySoundPool_; //Variable para gestión de clips de audio

    // Constructor de la clase
    public EngineAndroid(SurfaceView myView) {
        this.myView_ = myView; // Asigna la ventana proporcionada
        running_ = false; // Inicializa el motor como no en funcionamiento
        myInput_ = new InputAndroid(this.myView_); // Inicializa la clase de entrada (input)
        myAssetManager_ = myView.getContext().getAssets(); //Obtiene la referencia a los assets
        myGraphics_ = new GraphicsAndroid(this.myView_, myAssetManager_); // Inicializa la clase de graficos
        myAudio = new AudioAndroid(myAssetManager_, mySoundPool_); // Inicializa la clase de audio
    }

    // Metodo para reanudar la ejecución del motor
    public void resume() {
        if (!this.running_) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            this.running_ = true; // Marca el motor como en funcionamiento
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.myRenderThread_ = new Thread(this);
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

    // Metodo que ejecuta el bucle principal de juego
    @Override
    public void run() {
        if (myRenderThread_ != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha muy rápido, la vista podría todavía
        // no estar inicializada.Espera activa
        while (this.running_ && this.myView_.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();

        // Bucle de juego principal.
        while (running_) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            //Tiempo entre frames
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            //Reescalado de la escena (por si ha aumentado o reducido su tamaño)
            myGraphics_.resize(myScene_.getWidth_(), myScene_.getHeight_());
            //Reescalado del input dentro de la escena
            for (TouchEvent event : myInput_.getTouchEvent()) {
                event.x -= myGraphics_.getTranslateX_();
                event.y -= myGraphics_.getTranslateY_();
                event.x /= myGraphics_.getScale_();
                event.y /= myGraphics_.getScale_();
            }
            //Gestión de eventos de input
            this.myScene_.handleInput(myInput_.getTouchEvent());
            myInput_.myEventsClear();
            //Update de la escena
            this.myScene_.update(elapsedTime);
            //Renderizado de la escena
            myGraphics_.prepareFrame();
            myScene_.render();
            myGraphics_.endFrame();
        }
    }

    //Guardamos e inicializamos la escena que vamos a ejecutar
    @Override
    public void setScene(IScene myIScene) {
        this.myScene_ = myIScene;
    }

    //Devuelve el objeto que gestiona los gráficos
    @Override
    public IGraphics getGraphics() {
        return myGraphics_;
    }

    //Devuelve el objeto que gestiona el input
    @Override
    public IInput getInput() {
        return this.myInput_;
    }

    //Devuelve el objeto que gestiona el audio
    @Override
    public IAudio getAudio() {
        return this.myAudio;
    }

    //Devuelve la escena que se esta ejecutando
    @Override
    public IScene getScene() {
        return this.myScene_;
    }
}
