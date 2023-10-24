package com.example.androidengine;

import android.content.res.AssetManager;
import android.view.SurfaceView;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

public class EngineAndroid implements IEngine, Runnable {
    private IGraphics myGraphics_;
    private SurfaceView myView_;
    private Thread myRenderThread_;
    private boolean running_;
    private IScene myScene_;
    private IAudio myAudio_;
    private IInput myInput_;
    AssetManager asset;

    public EngineAndroid(SurfaceView myView) {
        myView_ = myView;

        running_ = false;
        myAudio_ = new AudioAndroid();
        myInput_ = new InputAndroid(myView_);
        asset=myView.getContext().getAssets();
        myGraphics_ = new GraphicsAndroid(myView_,asset);

    }
    @Override
    public void resume() {
        if (!this.running_) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running_ = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.myRenderThread_ = new Thread(this);
            this.myRenderThread_.start();
        }
    }

    @Override
    public void pause() {
        if (this.running_) {
            this.running_ = false;
            while (true) {
                try {
                    this.myRenderThread_.join();
                    this.myRenderThread_ = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }

    @Override
    public void run() {
        if (myRenderThread_ != Thread.currentThread()) {
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

            myGraphics_.resize(myScene_.getWidth(),myScene_.getHeight());
            for(TouchEvent event:myInput_.getTouchEvent()){
                event.x-=myGraphics_.getTranslateX_();
                event.y-=myGraphics_.getTranslateY_();
                event.x/=myGraphics_.getScale_();
                event.y/=myGraphics_.getScale_();
            }
            this.myScene_.handleInput(myInput_.getTouchEvent());
            myInput_.myEventsClear();
            this.myScene_.update(elapsedTime);
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
            myGraphics_.prepareFrame();
            myScene_.render();
            myGraphics_.endFrame();
        }
    }

    @Override
    public void setScene(IScene myIScene) {
        this.myScene_ = myIScene;
        myScene_.init();
    }

    @Override
    public IGraphics getGraphics() {
        return myGraphics_;
    }

    @Override
    public IInput getInput() {
        return this.myInput_;
    }

    @Override
    public IAudio getAudio() {
        return this.myAudio_;
    }

    @Override
    public IScene getScene() {
        return this.myScene_;
    }
}
