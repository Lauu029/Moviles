package com.example.androidgame.GameLogic.Scenes;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.IScene;
import com.example.androidengine.Image;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.GameObject;

import java.util.ArrayList;

public class Scene implements IScene {
    protected Engine iEngine_;
    protected int width_, height_;
    protected ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    protected boolean world_ = false;
    protected boolean isGameScene=false;
    protected Image backgroundImage_ = null;

    protected Scene() {
        iEngine_ = GameManager.getInstance().getIEngine();
        width_ = GameManager.getInstance().getWidth();
        height_ = GameManager.getInstance().getHeight();
    }

    @Override
    public void render() {
        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        if (AssetsManager.getInstance().getBackgroundImage(world_, isGameScene) != null)
            iEngine_.getGraphics().drawImage(AssetsManager.getInstance().getBackgroundImage(world_, isGameScene),
                    0, 0, height_, width_);
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
