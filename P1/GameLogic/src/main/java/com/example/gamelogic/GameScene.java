package com.example.gamelogic;

import com.example.engine.Engine;
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
        sol.createSolution(true,5,8);
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
       /* for(int i=0;i<gameObjects_.size();i++){
            gameObjects_.get(i).render();
        }*/

        Graphics graph=engine_.getGraphics();
        graph.clear(0xFFFFFFFF);
        graph.setcolor(0xFF23FD88);
        graph.drawRoundRectangle(80,80,200,100,25);
        graph.setcolor(0xFFED0F8D);

        graph.drawCircle(80,300,50);

    }


    @Override
    public void update(double time) {
        for(int i=0;i<gameObjects_.size();i++){
            gameObjects_.get(i).render();
        }
    }


}
