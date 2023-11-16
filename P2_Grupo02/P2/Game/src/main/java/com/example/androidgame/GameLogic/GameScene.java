package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;

public class GameScene implements IScene {
    private Solution mySolution_;
    private Engine iEngine_;
    private ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    private int width_, height_;
    private ButtonDaltonics buttonDaltonics_;
    private Board gameBoard_;
    private Font font_;
    private Difficulty lev_;
    private GameManager gm_;
    private Sound myCrossSound_;
    public GameScene(Engine IEngine, int w, int h) {
        this.iEngine_ = IEngine;
        this.width_ = w;
        this.height_ = h;
    }
    //Inicializa los botones, el tablero y la solución
    public void init() {
        this.font_ = this.iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance_();
        this.lev_ = this.gm_.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());
        this.gameBoard_ = new Board( lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_);
        addGameObject(gameBoard_);
        gm_.setBoard_(this.gameBoard_);
        this.buttonDaltonics_ =new ButtonDaltonics(70,50,this.width_ -70,1);
        addGameObject(buttonDaltonics_);
        iEngine_.getGraphics().setColor(0xFF000000);
        for (GameObject g : gameObjects_) {
            g.init();
        }
        myCrossSound_=iEngine_.getAudio().newSound("crossButton.wav");
        ButtonImage but2=new ButtonImage("cruz.png",40,40, 0,0,SceneNames.LEVEL,myCrossSound_);
        this.addGameObject(but2);
    }

    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
    }

    public int getHeight_() {
        return height_;
    }

    public int getWidth_() {
        return width_;
    }

    public void handleInput(ArrayList<TouchEvent> events) {
        for (GameObject g : gameObjects_) {
            for (TouchEvent event : events) {
                if (g.handleInput(event))
                    return;
            }
        }
    }

    public void render() {
        Graphics graph = iEngine_.getGraphics();
        graph.clear(0xFFfff0f6);
        for (GameObject g : gameObjects_) {
            g.render(graph);
        }
    }
    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    public void update(double time) {
        int[] tempSol = gm_.getLevelSolution_();
        int i = 0;
        boolean isComplete = true;
        while (i < tempSol.length && isComplete) {
            if (tempSol[i] == -1)
                isComplete = false;
            i++;
        }
        if (isComplete) {
            mySolution_.check(tempSol);
            int try_ = this.gameBoard_.getAcutalTry_();
            if (mySolution_.getCorrectPos(try_) == this.lev_.getSolutionColors()) {
                EndScene end = new EndScene(this.iEngine_, this.width_, this.height_, true, mySolution_.getSol_(), try_);
                this.gm_.changeScene(end);
            } else if (try_ == lev_.getTries() -1) {
                EndScene end = new EndScene(this.iEngine_, this.width_, this.height_, false, mySolution_.getSol_(), try_);
                this.gm_.changeScene(end);
            } else {
                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                gameBoard_.nexTry();
            }
        }
        for (int j = 0; j < gameObjects_.size(); j++) {
            gameObjects_.get(j).update(time);
        }
    }
}
