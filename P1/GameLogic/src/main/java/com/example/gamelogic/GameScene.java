package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.awt.Color;
import java.util.ArrayList;

public class GameScene implements IScene {
    Solution mySolution_;
    IEngine IEngine_;
    ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    Button button;
    IFont font;

    public GameScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
    }

    @Override
    public void init() {
        //creacion de la solucion
        mySolution_ = new Solution();
        mySolution_.createSolution(true, 5, 8, 8);
        mySolution_.imprimeSol();
        int[] miArray = {1, 2, 3, 4, 0};

        mySolution_.compureba(miArray);
        mySolution_.imprime();
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
        this.button = new Button("Boton chuli", this.font,
                 0xFFFFE906 ,150,50, 5,this.width_/2, this.height_/2);
        addGameObject(button);
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
        graph.setColor(0xFF000000);
        graph.fillRectangle(0, 0, width_, height_);

        graph.setStrokeWidth(15);
        graph.setColor(0xFF23FD88);
        graph.drawRoundRectangle(80, 80, 200, 100, 25);
        this.button.render(graph);
        graph.setColor(0xFFED0F8D);
        graph.drawText("MasterMind", width_/2, 700, 100, this.font);
        graph.drawCircle(80, 300, 50);

        graph.fillRectangle(0, 0, 100, 100);
        graph.resize(width_, height_);

    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }


}
