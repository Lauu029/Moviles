package com.example.gamelogic;



import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.awt.Color;
import java.util.ArrayList;

public class LevelScene implements IScene {

    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;

    private IFont font;

    public LevelScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
    }

    @Override
    public void init() {
        //creacion de la solucion

        this.font= new IFont() {
            @Override
            public void setBold(boolean bold) {

            }

            @Override
            public boolean isBold() {
                return false;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public void setSize(int size) {

            }
        };
        String [] nombres={"FACIL" ,
                "MEDIO" ,
                "DIFICIL" ,
                "IMPOSIBLE"};
        int [] colores={0xFF58B2E6,0XFFA9B2EC,0XFFDDB5DF,0XFFF6C0CF};
        for(int i=0;i< 4;i++){
            Button but=new Button(nombres[i], this.font,
                    colores[i] ,150,50, 5,this.width_/2-150/2,90*i+80);
            but.getIEngine(IEngine_);
            this.addGameObject(but);
        }

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
        graph.setColor(0xFFe3fcf3);
        graph.fillRectangle(0, 0, width_, height_);
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }


    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }


}
