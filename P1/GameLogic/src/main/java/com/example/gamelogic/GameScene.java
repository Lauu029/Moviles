package com.example.gamelogic;

import com.example.engine.Engine;
import com.example.engine.Font;
import com.example.engine.GameObject;
import com.example.engine.Graphics;

import java.util.ArrayList;

public class GameScene implements com.example.engine.Scene {
    Solution sol;
    Engine engine_;
    ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    public GameScene(Engine engine){
        engine_=engine;
    }
    @Override
    public void init() {
        //creacion de la solucion
        sol=new Solution();
        sol.createSolution(true,5,8,8);
        sol.imprimeSol();
        int[] miArray = {1,2, 3, 4, 0};

        sol.compureba(miArray);
        sol.imprime();

    }

    @Override
    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
    }

    @Override
    public void render() {
        Graphics graph=engine_.getGraphics();
        graph.clear(0xFFFFFFFF);

       for(int i=0;i<gameObjects_.size();i++){
            gameObjects_.get(i).render(graph);
        }


        graph.setStrokeWidth(15);
        graph.setcolor(0xFF23FD88);
        graph.drawRoundRectangle(80,80,200,100,25);
        graph.setcolor(0xFFED0F8D);
        Font fuente= new Font() {
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
        graph.drawText("MasterMind",200, 700,100, fuente);
        graph.drawCircle(80,300,50);

    }


    @Override
    public void update(double time) {
        for(int i=0;i<gameObjects_.size();i++){
            gameObjects_.get(i).update(time);
        }
    }


}
