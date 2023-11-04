package com.example.gamelogic;


import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class LevelScene implements IScene {

    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;

    private IFont font;
    private ISound myButtonSound_;

    public LevelScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = IEngine_.getGraphics();

        String[] names = {"Facil",
                "Medio",
                "Dificil",
                "Imposible"};

        LevelDifficulty[] diff = {LevelDifficulty.FACIL,
                LevelDifficulty.MEDIO,
                LevelDifficulty.DIFICIL,
                LevelDifficulty.IMPOSIBLE};
        int[] colors = {0XFFF6C0CF, 0XFFDDB5DF, 0XFFA9B2EC, 0xFF58B2E6};
        font = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = IEngine_.getAudio().newSound("buttonClicked.wav");
        for (int i = 0; i < 4; i++) {
            ButtonLevel but = new ButtonLevel(names[i], font,
                    colors[i], 150, 50, 35, this.width_ / 2 - 150 / 2, 100 * i + 100, SceneNames.GAME, diff[i], myButtonSound_);

            this.addGameObject(but);
        }
        ButtonImage but2 = new ButtonImage("flecha.png", 40, 40, 0, 0, SceneNames.MENU);
        this.addGameObject(but2);
    }


    public void addGameObject(IGameObject gm) {
        IGameObjects_.add(gm);
    }

    @Override
    public int getHeight() {
        return height_;
    }

    @Override
    public int getWidth() {
        return width_;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (IGameObject g : IGameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    @Override
    public void render() {
        IGraphics graph = IEngine_.getGraphics();

        graph.clear(0xFFfff0f6);
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }
        this.IEngine_.getGraphics().setFont(font);
        graph.setColor(0xFF000000);
        graph.drawText("¿En que dificultad quieres jugar?", width_ / 2, 50);
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }
}
