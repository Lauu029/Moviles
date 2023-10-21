package com.example.desktopengine;

import com.example.engine.Audio;
import com.example.engine.Graphics;
import com.example.engine.Input;
import com.example.engine.Scene;

import javax.swing.JFrame;

public class EngineDesktop implements com.example.engine.Engine, Runnable {
    private JFrame myView_;
    private Thread renderThread;
    private boolean running_=false;
    private Scene myScene_;
    private Graphics myGraphics_;
    public EngineDesktop(JFrame myView)
    {
        myView_=myView;
        myGraphics_= new GraphicsDesktop(myView_);

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
            this.myScene_.update(elapsedTime);//elapsedTime
            //render();
            myGraphics_.prepareFrame();
            myScene_.render();
            myGraphics_.endFrame();
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
    public void setScene(Scene myScene) {
        this.myScene_=myScene;
    }
    @Override
    public Graphics getGraphics() {
        return this.myGraphics_;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public Audio getAudio() {
        return null;
    }

    @Override
    public Scene getScene() {
        return this.myScene_;
    }
}
