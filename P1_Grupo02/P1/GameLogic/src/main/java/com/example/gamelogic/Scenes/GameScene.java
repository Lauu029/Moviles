package com.example.gamelogic.Scenes;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.ISound;
import com.example.gamelogic.Board;
import com.example.gamelogic.Buttons.Button;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.Buttons.ButtonColorBlind;
import com.example.gamelogic.Buttons.ButtonImage;
import com.example.gamelogic.Difficulty;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SceneManager;
import com.example.gamelogic.GameObject;
import com.example.gamelogic.Solution;

import java.util.ArrayList;

public class GameScene extends Scene {
    private Solution mySolution_;
    private ButtonColorBlind buttonColorBlind_;
    private Board gameBoard_;
    private IFont font_;
    private Difficulty lev_;
    private GameManager gm_;
    private ISound myCrossSound_;
    private Button cheatButton_;
    private int timesPressed;
    public GameScene() {
        super();
    }
    private double timeSinceFirst;
    boolean canCheat;
    //Inicializa los botones, el tablero y la solución
    @Override
    public void init() {
        canCheat=true;
        timesPressed=0;
        this.font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance_();
        this.lev_ = this.gm_.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());

        int [] print=mySolution_.getSol();
        for (int i=0; i<print.length; i++){
            System.out.println(print[i]);
        }

        gm_.setFinalSolution(mySolution_.getSol());
        this.gameBoard_ = new Board( lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_);
        addGameObject(gameBoard_);
        gm_.setBoard_(this.gameBoard_);
        ISound buttonSound= GameManager.getInstance_().getIEngine().getAudio().newSound("daltonicsButton.wav");
        this.buttonColorBlind_ =new ButtonColorBlind("eye_open.png","eye_closed.png",
                40, 40, this.width_ - 45, 0,buttonSound, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeDaltonicsMode();
            }
        });
        addGameObject(buttonColorBlind_);
        iEngine_.getGraphics().setColor(0xFF000000);
        for (GameObject g : gameObjects_) {
            g.init();
        }
        myCrossSound_=iEngine_.getAudio().newSound("crossButton.wav");
        ButtonImage exitLevel_=new ButtonImage("cruz.png",  40, 40, 5, 0,
                myCrossSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new SaveScene(),SceneNames.SAVE.ordinal());
            }
        });
        this.addGameObject(exitLevel_);
        this.cheatButton_ = new Button("Cheat",font_, 0XFFFB839B
                , 100, 50, 35, this.width_-100, this.height_-150, buttonSound, new ButtonClickListener() {
            @Override
            public void onClick() {
                timesPressed++;

                if(timeSinceFirst<=1)
                {
                    System.out.println("Puede pulsar aun");
                    if(timesPressed>=3)
                    {
                        System.out.println("Cheat");
                        canCheat=true;
                        cheat();
                        timeSinceFirst=0.0;
                        timesPressed=0;
                    }
                    else{
                        canCheat=false;
                    }
                }
                else
                {
                    System.out.println("Ya no puede pulsar");
                    canCheat=false;
                    timeSinceFirst=0.0;
                    timesPressed=0;
                }
            }
        });

        addGameObject(cheatButton_);
    }

    @Override
    public void render() {
        super.render();
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }
    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    @Override
    public void update(double time) {
        if(timesPressed>=1)
        {
            timeSinceFirst+=time;
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
            EndScene end = new EndScene(win, mySolution_.getSol(), try_);
            SceneManager.getInstance().addScene(end, SceneNames.FINAL.ordinal());
    }
    void cheat() {

        int[] tempSol = gm_.getLevelSolution();
        int j = 0;
        boolean empty = true;
        while (j < tempSol.length && empty) {
            if (tempSol[j] != -1)
                empty = false;
            j++;
        }
        if(empty && canCheat)
        {
            gm_.resetLevelSolution();
            int[] finalSolution = this.mySolution_.getSol();
            for (int i = 0; i < finalSolution.length - 1; i++) {
                gameBoard_.putColor(finalSolution[i]);
            }
        }
    }
}
