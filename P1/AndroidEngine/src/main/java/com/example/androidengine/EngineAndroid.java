package com.example.androidengine;

import android.util.Log;
import android.view.SurfaceView;

import com.example.engine.Audio;
import com.example.engine.Graphics;
import com.example.engine.Input;
import com.example.engine.Scene;

public class EngineAndroid implements com.example.engine.Engine, Runnable {
    private Graphics myGraphics_;
    private SurfaceView myView_;
    private Thread renderThread;
    private boolean running;
    private Scene scene;
    private Audio audio;
    private Input input;

    public EngineAndroid(SurfaceView myView) {
        myView_ = myView;
        myGraphics_ = new GraphicsAndroid(myView_);
        running = false;
        audio= new AudioAndroid();
        input = new InputAndroid();
    }
    @Override
    public void resume() {
        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
        }
    }

    @Override
    public void pause() {
        if (this.running) {
            this.running = false;
            while (true) {
                try {
                    this.renderThread.join();
                    this.renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }

    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (this.running && this.myView_.getWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Informe de FPS
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.scene.update(elapsedTime);
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
            myGraphics_.render(scene);
        }
    }

    @Override
    public void setScene(Scene myScene) {
        this.scene = myScene;
    }

    @Override
    public Graphics getGraphics() {
        return myGraphics_;
    }

    @Override
    public Input getInput() {
        return this.input;
    }

    @Override
    public Audio getAudio() {
        return this.audio;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
