package com.example.androidgame.GameLogic;



import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;

public class LevelScene implements IScene {
    private Engine iEngine_;

    private ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Font font_;
    private Sound myButtonSound_;
    private Sound myArrowSound_;
    public LevelScene(Engine IEngine, int w, int h) {
        iEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }

    @Override
    public void init() {
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();

        String[] names = {"Facil",
                "Medio",
                "Dificil",
                "Imposible"};

        LevelDifficulty[] diff = {LevelDifficulty.FACIL,
                LevelDifficulty.MEDIO,
                LevelDifficulty.DIFICIL,
                LevelDifficulty.IMPOSIBLE};
        int[] colors = {0XFFF6C0CF, 0XFFDDB5DF, 0XFFA9B2EC, 0xFF58B2E6};
        font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");
        for (int i = 0; i < 4; i++) {
            ButtonLevel but = new ButtonLevel(names[i], font_,
                    colors[i], 150, 50, 35, this.width_ / 2 - 150 / 2, 100 * i + 100, SceneNames.GAME, diff[i], myButtonSound_);

            this.addGameObject(but);
        }
        myArrowSound_=iEngine_.getAudio().newSound("arrowButton.wav");
        ButtonImage but2 = new ButtonImage("flecha.png", 40, 40, 0, 0, SceneNames.MENU,myArrowSound_);
        this.addGameObject(but2);
        //graph.generateScreenshot(0,0,100,100);
        //iEngine_.getMobile().shareImage(graph.processImage(),"mi imagen");
    }


    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
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

    @Override
    public void render() {
        Graphics graph = iEngine_.getGraphics();

        graph.clear(0xFFfff0f6);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(graph);
        }
        this.iEngine_.getGraphics().setFont(font_);
        graph.setColor(0xFF000000);
        graph.drawText("¿En que dificultad quieres jugar?", width_ / 2, 50);
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).update(time);
        }
    }
}
