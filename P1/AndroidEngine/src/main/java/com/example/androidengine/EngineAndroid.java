package com.example.androidengine;

import android.view.SurfaceView;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;

public class EngineAndroid implements IEngine, Runnable {
    private IGraphics myIGraphics_;
    private SurfaceView myView_;
    private Thread renderThread;
    private boolean running;
    private IScene IScene;
    private IAudio IAudio;
    private IInput IInput;

    public EngineAndroid(SurfaceView myView) {
        myView_ = myView;
        myIGraphics_ = new GraphicsAndroid(myView_);
        running = false;
        IAudio = new AudioAndroid();
        IInput = new InputAndroid();
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
            this.IScene.update(elapsedTime);
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
            myIGraphics_.prepareFrame();
            IScene.render();
            myIGraphics_.endFrame();
        }
    }

    @Override
    public void setScene(IScene myIScene) {
        this.IScene = myIScene;
    }

    @Override
    public IGraphics getGraphics() {
        return myIGraphics_;
    }

    @Override
    public IInput getInput() {
        return this.IInput;
    }

    @Override
    public IAudio getAudio() {
        return this.IAudio;
    }

    @Override
    public IScene getScene() {
        return this.IScene;
    }
}
