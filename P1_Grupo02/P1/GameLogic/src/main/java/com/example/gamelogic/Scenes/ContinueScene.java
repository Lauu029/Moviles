package com.example.gamelogic.Scenes;

import com.example.engine.ISound;
import com.example.gamelogic.Board;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.Buttons.ButtonColorBlind;
import com.example.gamelogic.Buttons.ButtonImage;
import com.example.gamelogic.Difficulty;
import com.example.gamelogic.GameObject;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SaveManager;
import com.example.gamelogic.Managers.SceneManager;
import com.example.gamelogic.Solution;

public class ContinueScene extends GameScene {
    @Override
    public void init() {
        this.font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance_();
        this.lev_ = new Difficulty();
        lev_.setRepeat( SaveManager.getInstance().isRepeat());
        lev_.setSolutionColors( SaveManager.getInstance().getSolutionColors());
        lev_.setTries( SaveManager.getInstance().getTries());
        lev_.setPosibleColors( SaveManager.getInstance().getPosiblecolors());
        gm_.setLevel(lev_);


        mySolution_ = new Solution();

            mySolution_.setSolution(SaveManager.getInstance().getSolution());

        this.gameBoard_ = new Board( lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_);
        addGameObject(gameBoard_);

            int[][]mat=SaveManager.getInstance().getmatrizJuego();
            for(int i=0;i< mat.length;i++){
                for(int j=0;j<mat[i].length;j++){
                    gameBoard_.putColor(mat[i][j]);
                }
                mySolution_.check(mat[i]);
                gameBoard_.setNewHints(mySolution_.getCorrectPos(gameBoard_.getAcutalTry_()), mySolution_.getCorrectColor(gameBoard_.getAcutalTry_()));
                gameBoard_.nexTry();
            }


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
                SaveManager.getInstance().setSolution(mySolution_.getSol());
                SaveManager.getInstance().setmatrizJuego(gameBoard_.getBoard());
                SaveManager.getInstance().setSaved(true);
                SceneManager.getInstance().setScene(SceneNames.DIFFICULTY.ordinal());

            }
        });
        this.addGameObject(exitLevel_);
    }

}
