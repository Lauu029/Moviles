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
    private Solution mySolution_;
    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button button;
    private Board gameBoard;
    private IFont font;
    private IFont font_;

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

        this.gameBoard = new Board(4, 6, 4, false,width_,height_);

        addGameObject(gameBoard);

        mySolution_.compureba(miArray);
        mySolution_.imprime();

        /*this.font_= new IFont() {
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
        };*/
        IEngine_.getGraphics().setColor(0xFF000000);
//        this.font_=IEngine_.getGraphics().newFont("orangeJuice.ttf",20,false,false);
//        this.IEngine_.getGraphics().setFont(font_);
//        this.button = new Button("Boton chuli", this.font_,
//                0xFF000000 ,150,50, 5,10, 10);
//        addGameObject(button);
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
        if (events.size() != 0) System.out.println("inpuuut");
        for (IGameObject g : IGameObjects_) {
            for (TouchEvent event : events) {
                if (g.handleInput(event))
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
      //  gameBoard.render(graph);
        for (IGameObject g : IGameObjects_) {
            g.render(graph);
        }

//        graph.setStrokeWidth(15);
//        graph.setColor(0xFF23FD88);
//        graph.drawRoundRectangle(80, 80, 200, 100, 25);
//        this.button.render(graph); //POR QUE NO SE VE?
//        graph.setColor(0xFFED0F8D);
//        //graph.drawText("MasterMind", width_/2, 700, 100, this.font);
//        graph.drawCircle(80, 300, 50);
//        this.IEngine_.getGraphics().setFont(font_);
//        graph.drawText("HOLAAAAAAAAAAAAAAAA",0,10);
//        graph.fillRectangle(0, 0, 100, 100);
       // graph.resize(width_, height_);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }


}
