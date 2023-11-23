package com.example.androidgame.GameLogic;



import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;


import java.io.IOException;
import java.util.ArrayList;

public class MenuScene implements IScene {

    private Engine iEngine_;
    private ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button button_;
    private Font font_;
    private Font fontButton_;
    private Image myIcon_;
    private Sound myButtonSound_;

    public MenuScene(Engine IEngine, int w, int h) {
        iEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }

    public void init() {
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.button_ = new Button("Jugar", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20, SceneNames.LEVEL, myButtonSound_);

        addGameObject(button_);
        myIcon_ = graph.newImage("cerebro.png");

    }

    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
    }

    public int getHeight_() {
        return height_;
    }

    public int getWidth_() {
        return width_;
    }

    public void handleInput(ArrayList<TouchEvent> events) {
        for (GameObject g : gameObjects_) {
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;

        }
    }

    public void render() {
        Graphics graph = iEngine_.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);

        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        graph.drawText("MasterMind", width_ / 2, 100);
        graph.drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 140, 80, 80);
    }

    public void update(double time) {
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).update(time);
        }
    }
}