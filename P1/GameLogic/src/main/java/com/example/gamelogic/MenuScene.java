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

    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button button;
    private IFont font;
    private IFont font_button;
    private IImage myIcon_;
    private ISound myButtonSound_;

    public MenuScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }
    @Override
    public void init()  {
        //creacion de la solucion
        IGraphics graph = IEngine_.getGraphics();
        this.font=graph.newFont("Hexenkoetel-qZRv1.ttf",40,true,true);
        graph.setFont(this.font);
        font_button=graph.newFont("Hexenkoetel-qZRv1.ttf",20,false,false);
        myButtonSound_=IEngine_.getAudio().newSound("buttonClicked.wav");
        this.button = new Button("Jugar",font_button,0XFFFB839B
                ,150,50, 35,this.width_/2-150/2, this.height_/2+20,SceneNames.LEVEL,myButtonSound_);

        addGameObject(button);

        try {
            myIcon_=graph.newImage("cerebro.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        for (IGameObject g : IGameObjects_) {
            for (TouchEvent event: events) {
                if(g.handleInput(event))
                    return;
            }

        }
    }

    @Override
    public void render() {
        IGraphics graph = IEngine_.getGraphics();


        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);



        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.IEngine_.getGraphics().setFont(font);
        graph.drawText("MasterMind", width_/2, 100);
        graph.drawImage(myIcon_,this.width_/2-80/2, this.height_/2-140,80,80);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }

}
