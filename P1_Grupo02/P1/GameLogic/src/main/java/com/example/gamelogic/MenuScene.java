package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;
import java.util.ArrayList;

public class MenuScene implements IScene {

    private IEngine iEngine_;
    private ArrayList<IGameObject> iGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button playButton_;
    private IFont font_;
    private IFont fontButton_;
    private IImage myIcon_;
    private ISound myButtonSound_;
    public MenuScene(IEngine IEngine, int w, int h) {
        iEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Jugar", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new LevelScene(iEngine_, width_, height_));
            }
        });

        addGameObject(playButton_);

        myIcon_ = graph.newImage("cerebro.png");


    }


    public void addGameObject(IGameObject gm) {
        iGameObjects_.add(gm);
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
        for (IGameObject g : iGameObjects_) {
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;

        }
    }

    @Override
    public void render() {
        IGraphics graph = iEngine_.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);

        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        graph.drawText("MasterMind", width_ / 2, 100);
        graph.drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 140, 80, 80);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).update(time);
        }
    }

}
