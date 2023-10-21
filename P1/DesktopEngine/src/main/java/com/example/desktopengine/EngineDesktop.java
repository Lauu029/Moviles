package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;

import javax.swing.JFrame;

public class EngineDesktop implements IEngine, Runnable {
    private JFrame myView_;
    private Thread renderThread;
    private boolean running_=false;
    private IScene myIScene_;
    private IGraphics myIGraphics_;
    public EngineDesktop(JFrame myView)
    {
        myView_=myView;
        myIGraphics_ = new GraphicsDesktop(myView_);

    }

    @Override
    public void resume() {
        if (!this.running_) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running_ = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.

            this.renderThread = new Thread(this);
            this.renderThread.start();

        }
    }
    @Override
    public void pause() {
        if (this.running_) {
            this.running_ = false;
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
        while (this.running_ && this.myView_.getWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while (running_) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Informe de FPS
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.myIScene_.update(elapsedTime);//elapsedTime
            //render();
            myIGraphics_.prepareFrame();
            myIScene_.render();
            myIGraphics_.endFrame();
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }

        }
    }

    public void render(){
        //this.myGraphics_.render(myScene_);
    }

    @Override
    public void setScene(IScene myIScene) {
        this.myIScene_ = myIScene;
    }
    @Override
    public IGraphics getGraphics() {
        return this.myIGraphics_;
    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IAudio getAudio() {
        return null;
    }

    @Override
    public IScene getScene() {
        return this.myIScene_;
    }
}
