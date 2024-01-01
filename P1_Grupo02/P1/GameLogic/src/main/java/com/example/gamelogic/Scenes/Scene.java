package com.example.gamelogic.Scenes;

import java.util.ArrayList;
import com.example.engine.IEngine;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.GameObject;
public class Scene implements IScene {
    protected IEngine iEngine_;
    protected int width_, height_;
    protected ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    protected boolean isGameScene=false;

    protected Scene() {
        iEngine_ = GameManager.getInstance_().getIEngine();
        width_ = GameManager.getInstance_().getwidth();
        height_ = GameManager.getInstance_().getHeight_();
    }

    @Override
    public void render() {
        iEngine_.getGraphics().clear(0xFFFFF0F6);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).update(time);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public int getHeight_() {
        return height_;
    }

    @Override
    public int getWidth_() {
        return width_;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (GameObject g : gameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
    }

    public void setGameScene(){
        this.isGameScene=true;
    }

}