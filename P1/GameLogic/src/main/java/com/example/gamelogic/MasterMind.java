package com.example.gamelogic;

import com.example.engine.Game;
import com.example.engine.Scene;

public class MasterMind implements Game {

    Scene actualScene;

    public MasterMind(){

    }
    @Override
    public void init() {
        actualScene=new GameScene();
        actualScene.init();
    }

    @Override
    public void render() {
        actualScene.render();
    }

    @Override
    public void update() {
        actualScene.update();
    }

    @Override
    public void resume() {

    }

    @Override
    public void setScene(Scene scene_) {
        scene_=actualScene;
    }

    @Override
    public Scene getScene() {
        return actualScene;
    }
}
