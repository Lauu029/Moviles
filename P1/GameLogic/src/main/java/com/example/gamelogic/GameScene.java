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
    private GameManager gm;

    public GameScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
    }

    @Override
    public void init() {

        this.gm = GameManager.getInstance();
        Difficulty lev = this.gm.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev.repeat_, lev.solutionColors_, lev.posibleColors_, lev.tries_);
        mySolution_.imprimeSol();
        int[] miArray = {1, 2, 3, 4, 0};
        this.gameBoard = new Board(lev.solutionColors_, lev.tries_, lev.posibleColors_, lev.repeat_, width_, height_);

        addGameObject(gameBoard);

        mySolution_.compureba(miArray);
        mySolution_.imprime();
        IEngine_.getGraphics().setColor(0xFF000000);
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
        graph.clear(0xFFe3fcf3);
        for (IGameObject g : IGameObjects_) {
            g.render(graph);
        }
    }

    @Override
    public void update(double time) {
        int[] tempSol = gm.getLevelSolution();
        int i = 0;
        boolean isComplete = true;
        while (i < tempSol.length && isComplete) {
            if (tempSol[i] == -1)
                isComplete = false;
            i++;
        }
        if (isComplete) {
            System.out.print("Me Compruebo\n");
            mySolution_.compureba(tempSol);
            gameBoard.nexTry();
        }
        for (int j = 0; j < IGameObjects_.size(); j++) {
            IGameObjects_.get(j).update(time);
        }
    }
}
