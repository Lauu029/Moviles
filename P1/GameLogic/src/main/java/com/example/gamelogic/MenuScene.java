package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.io.IOException;
import java.util.ArrayList;

public class MenuScene implements IScene {

    IEngine IEngine_;
    ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    Button button;
    IFont font;
    IImage oreo_;
    private IImage image_;

    public MenuScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
    }


    @Override
    public void init()  {
        //creacion de la solucion
        IGraphics graph = IEngine_.getGraphics();
        this.font=graph.newFont("orangeJuice.ttf",40,false,false);
        graph.setFont(this.font);

        this.button = new Button("JUGAR", this.font,0XFFDDB5DF
                ,150,50, 5,this.width_/2-150/2, this.height_/2-25);
        button.getIEngine(IEngine_);
        addGameObject(button);

        try {
            oreo_=graph.newImage("oreooo.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // this.image_=IEngine_.getGraphics().newImage("hola.png");
    }

    @Override
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
        if (events.size() != 0) System.out.println("inpuuut");
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
        graph.clear(0xFFe3fcf3);

        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }
        graph.setColor(0xFF58B2E6);
        graph.drawText("MasterMind", width_/2, 100);
        graph.drawImage(oreo_,200,200,45,60);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }

}
