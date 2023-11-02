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
    private ButtonDaltonics button_dalt;
    private Board gameBoard;
    private IFont font;
    private Difficulty lev;
    private GameManager gm;

    public GameScene(IEngine IEngine, int w, int h) {
        this.IEngine_ = IEngine;
        this.width_ = w;
        this.height_ = h;
    }

    @Override
    public void init() {
        this.font = this.IEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm = GameManager.getInstance();
        this.lev = this.gm.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev.repeat_, lev.solutionColors_, lev.posibleColors_, lev.tries_);
        this.gameBoard = new Board(this.font, lev.solutionColors_, lev.tries_, lev.posibleColors_, lev.repeat_, width_, height_);
        addGameObject(gameBoard);
        gm.setBoard(this.gameBoard);
        this.button_dalt=new ButtonDaltonics(70,50,this.width_-70,1);
        addGameObject(button_dalt);
        IEngine_.getGraphics().setColor(0xFF000000);
        for (IGameObject g : IGameObjects_) {
            g.init();
        }
        ButtonImage but2=new ButtonImage("cruz.png",40,40, 0,0,SceneNames.LEVEL);
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
            mySolution_.compureba(tempSol);
            int try_ = this.gameBoard.getAcutalTry();
            if (mySolution_.getposCorrecta(try_) == this.lev.solutionColors_) {
                EndScene end = new EndScene(this.IEngine_, this.width_, this.height_, true, mySolution_.getSol(), try_);
                this.gm.changeScene(end);
            } else if (try_ == lev.tries_-1) {
                EndScene end = new EndScene(this.IEngine_, this.width_, this.height_, false, mySolution_.getSol(), try_);
                this.gm.changeScene(end);
            } else {
                gameBoard.setNewHints(mySolution_.getposCorrecta(try_), mySolution_.getColorCorrecto(try_));
                gameBoard.nexTry();
            }
        }
        for (int j = 0; j < IGameObjects_.size(); j++) {
            IGameObjects_.get(j).update(time);
        }
    }
}
