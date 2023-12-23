package com.example.androidgame.GameLogic.Scenes;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Buttons.ButtonColorBlind;
import com.example.androidgame.GameLogic.Buttons.ButtonImage;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidgame.GameLogic.GameObject;
import com.example.androidgame.GameLogic.SceneManager;
import com.example.androidgame.GameLogic.Solution;

import java.util.ArrayList;

public class GameScene extends Scene {
    private Solution mySolution_;
    private ButtonColorBlind buttonColorBlind_;
    private Board gameBoard_;
    private Font font_;
    private Difficulty lev_;
    private GameManager gm_;
    private Sound myCrossSound_;
    boolean scroll = false;
    int yIni;
    int yFin;
    boolean canGetReward_;
    Image backaground_;
    private int upTryPos_,downTryPos_,upRenderPos_,downRenderPos_;
    public GameScene(boolean world) {
        super();
        world_ = world;
        canGetReward_ = true;

    }

    //Inicializa los botones, el tablero y la solución
    public void init() {
        this.font_ = this.iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance();
        this.lev_ = this.gm_.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());
        this.gameBoard_ = new Board(lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_, world_);
        addGameObject(gameBoard_);
        gm_.setBoard(this.gameBoard_);
        Sound buttonSound = GameManager.getInstance().getIEngine().getAudio().newSound("colorBlindButton.wav");
        this.buttonColorBlind_ = new ButtonColorBlind("eye_open.png", "eye_closed.png",
                40, 40, this.width_ - 45, 0, buttonSound, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance().changeDaltonicsMode();
            }
        });
        addGameObject(buttonColorBlind_);
        iEngine_.getGraphics().setColor(0xFF000000);
        for (GameObject g : gameObjects_) {
            g.init();
        }
        myCrossSound_ = iEngine_.getAudio().newSound("crossButton.wav");
        ButtonImage exitLevel_ = new ButtonImage("cruz.png", 40, 40, 5, 0, /*SceneNames.LEVEL,*/
                myCrossSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                int idScene;
                if (!world_)
                    idScene = SceneNames.DIFFICULTY.ordinal();
                else
                    idScene = SceneNames.WORLD.ordinal();
                SceneManager.getInstance().setScene(idScene);


            }
        });
        this.addGameObject(exitLevel_);
        upTryPos_=gameBoard_.getUpTryPos();
        downTryPos_=gameBoard_.getDownTryPos();
        downRenderPos_=gameBoard_.getdownRenderPos();
        upRenderPos_=gameBoard_.getupRenderPos();



    }

    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    public void update(double time) {
        if (scroll) {
            int speed = yFin - yIni;
            if((gameBoard_.getUpTryPos()<upRenderPos_&&speed>0)||(gameBoard_.getDownTryPos()>downRenderPos_&&speed<0)){

                gameBoard_.TranslateY(speed);

            }
            yIni = yFin;
        }
        int[] tempSol = gm_.getLevelSolution();
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
                ChangeEndScene(true, try_);

            } else if (try_ == gameBoard_.getTotalTries() - 1) {
                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                ChangeEndScene(false, try_);
            } else {
                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                gameBoard_.nexTry();
            }
        }
        super.update(time);
    }

    protected void ChangeEndScene(boolean win, int try_) {
        Log.d("Cojones","Es mundo: "+world_);
        if (!world_) {
            if (win) canGetReward_ = false;
            EndScene end = new EndScene(win, mySolution_.getSol_(), try_);
            SceneManager.getInstance().addScene(end, SceneNames.FINAL.ordinal());
        } else {
            WorldEndScene worldEnd = new WorldEndScene(win, mySolution_.getSol_(), try_);
            SceneManager.getInstance().addScene(worldEnd, SceneNames.WORLD_FINAL.ordinal());
        }
    }

    @Override
    public void render() {
        super.render();
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        super.handleInput(events);
        for (TouchEvent event : events) {
            if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
                if (!scroll)
                    yIni = event.y;
            } else if (event.type == TouchEvent.TouchEventType.TOUCH_DRAG) {
                scroll = true;
                yFin = event.y;
            } else if (event.type == TouchEvent.TouchEventType.TOUCH_UP) {
                scroll = false;
            }
        }
    }
    public void addTriesToBoard(int numTries) {
        gameBoard_.addNewTries(numTries);
        canGetReward_ = false;
    }
}