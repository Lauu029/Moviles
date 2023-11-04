package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class GameScene implements IScene {
    private Solution mySolution_;
    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private ButtonDaltonics buttonDaltonics;
    private Board gameBoard;
    private IFont font;
    private Difficulty lev;
    private GameManager gm;

    public GameScene(IEngine IEngine, int w, int h) {
        this.IEngine_ = IEngine;
        this.width_ = w;
        this.height_ = h;
    }
    //Inicializa los botones, el tablero y la solución
    @Override
    public void init() {
        this.font = this.IEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm = GameManager.getInstance();
        this.lev = this.gm.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev.isRepeat(), lev.getSolutionColors(), lev.getPosibleColors(), lev.getTries());
        this.gameBoard = new Board( lev.getSolutionColors(), lev.getTries(), lev.getPosibleColors(), lev.isRepeat(), width_, height_);
        addGameObject(gameBoard);
        gm.setBoard(this.gameBoard);
        this.buttonDaltonics =new ButtonDaltonics(70,50,this.width_-70,1);
        addGameObject(buttonDaltonics);
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
        graph.clear(0xFFfff0f6);
        for (IGameObject g : IGameObjects_) {
            g.render(graph);
        }
    }
    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
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
            mySolution_.check(tempSol);
            int try_ = this.gameBoard.getAcutalTry();
            if (mySolution_.getCorrectPos(try_) == this.lev.getSolutionColors()) {
                EndScene end = new EndScene(this.IEngine_, this.width_, this.height_, true, mySolution_.getSol(), try_);
                this.gm.changeScene(end);
            } else if (try_ == lev.getTries() -1) {
                EndScene end = new EndScene(this.IEngine_, this.width_, this.height_, false, mySolution_.getSol(), try_);
                this.gm.changeScene(end);
            } else {
                gameBoard.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                gameBoard.nexTry();
            }
        }
        for (int j = 0; j < IGameObjects_.size(); j++) {
            IGameObjects_.get(j).update(time);
        }
    }
}
