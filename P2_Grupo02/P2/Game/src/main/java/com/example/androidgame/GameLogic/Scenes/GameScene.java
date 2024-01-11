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
import com.example.androidgame.GameLogic.GameInit;
import com.example.androidgame.GameLogic.LevelDifficulty;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.GameObject;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Solution;

import java.util.ArrayList;

public class GameScene extends Scene {
    protected Solution mySolution_;
    protected ButtonColorBlind buttonColorBlind_;
    protected Board gameBoard_;
    protected Font font_;
    protected Difficulty lev_;
    protected GameManager gm_;
    protected Sound myCrossSound_;
    protected boolean scroll = false;
    protected int yIni;
    protected int yFin;
    protected int upTryPos_, downTryPos_, upRenderPos_, downRenderPos_;
    protected boolean contrarreloj_;
    protected float timerInicial_;
    protected float levelDuration_;
    protected LevelDifficulty[] diff = {LevelDifficulty.FACIL,
            LevelDifficulty.MEDIO,
            LevelDifficulty.DIFICIL,
            LevelDifficulty.IMPOSIBLE};
    public GameScene() {
        super();
    }

    //Inicializa los botones, el tablero y la solución
    public void init() {
        this.font_ = this.iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance();
        this.lev_ = this.gm_.getLevel();
        mySolution_ = new Solution();
        contrarreloj_=GameManager.getInstance().getContrarreloj();
        createSolution();
        createGameBoard();

        addGameObject(gameBoard_);
        gm_.setBoard(this.gameBoard_);
        if(contrarreloj_)
        {
            timerInicial_=gm_.getTimeLeft();
            levelDuration_=0.0f;
            Log.d("CONTRARRELOJ","TIEMPO PARA ESTE NIVEL EN SEGUNDOS: "+timerInicial_);
        }

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
                if(!contrarreloj_)
                    changeSceneExit();
            }
        });
        this.addGameObject(exitLevel_);
        upTryPos_ = gameBoard_.getUpTryPos();
        downTryPos_ = gameBoard_.getDownTryPos();
        downRenderPos_ = gameBoard_.getdownRenderPos();
        upRenderPos_ = gameBoard_.getupRenderPos();
    }
    protected void createSolution(){
        mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());
        Log.d("CONTRARRELOJ","La solucion es: ");
        int [] tempSol=mySolution_.getSol();
        for(int i=0; i<tempSol.length; i++)
        {
            int n=tempSol[i];
            String s= String.valueOf(n);
            Log.d("CONTRARRELOJ",s);
        }
    }
    protected void createGameBoard(){
        this.gameBoard_ = new Board(lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_, false);
    }

    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    public void update(double time) {
        if(contrarreloj_)
        {   levelDuration_+=time;
            if(timerInicial_<=0)
            {
                Log.d("CONTRARRELOJ","No queda mas tiempo: "+levelDuration_);
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        }
        if (scroll) {
            int speed = yFin - yIni;
            if ((gameBoard_.getUpTryPos() < upRenderPos_ && speed > 0) || (gameBoard_.getDownTryPos() > downRenderPos_ && speed < 0)) {
                gameBoard_.TranslateY(speed);
            }
            yIni = yFin;
        }
        checkSolution();
        super.update(time);
    }
    public void render(){
        super.render();

        float totalSecs=timerInicial_-levelDuration_;
        float minutes = (totalSecs % 3600) / 60;
        float seconds = totalSecs % 60;
        if(minutes<10)
        {
            iEngine_.getGraphics().drawText("0"+Math.round(minutes), (width_ / 2)-10, height_/2-220);
        }
        else iEngine_.getGraphics().drawText(String.valueOf(Math.round(minutes)), (width_ / 2)-10, height_/2-220);
        iEngine_.getGraphics().drawText(":", (width_ / 2), height_/2-220);
        if(seconds<10)
        {
            iEngine_.getGraphics().drawText("0"+Math.round(seconds), (width_ / 2)+10, height_/2-220);
        }
        else iEngine_.getGraphics().drawText(String.valueOf(Math.round(seconds)), (width_ / 2)+10, height_/2-220);
        //iEngine_.getGraphics().drawText("Lives: "+GameManager.getInstance().getCurrentLives(),(width_ / 2),height_/2-240);
    }
    protected void ChangeEndScene(boolean win, int try_) {

        EndScene end = new EndScene(win, mySolution_.getSol(), try_);
        if(!win) GameManager.getInstance().lostLife();
        SceneManager.getInstance().addScene(end, SceneNames.FINAL.ordinal());
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
    protected void changeSceneExit(){
        SceneManager.getInstance().setScene(SceneNames.DIFFICULTY.ordinal());
    }
    public void addTriesToBoard(int numTries) {
        gameBoard_.addNewTries(numTries);
        LevelManager.getInstance().setTotalTries(gameBoard_.getTotalTries());
    }
    protected void checkSolution(){
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
            if(!contrarreloj_)
            {
                if (mySolution_.getCorrectPos(try_) == this.lev_.getSolutionColors()) {
                    ChangeEndScene(true, try_);
                    LevelManager.getInstance().clearTries();

                } else if (try_ == gameBoard_.getTotalTries() - 1) {
                    gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                    ChangeEndScene(false, try_);

                } else {
                    gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                    gameBoard_.nexTry();
                }
            }
            else
            {
                if (mySolution_.getCorrectPos(try_) == this.lev_.getSolutionColors()) {
                    LevelManager.getInstance().clearTries();
                    GameManager.getInstance().passedContrarreloj();
                    if(GameManager.getInstance().getNivelesContrarreloj()==GameManager.getInstance().getPasadosContrarreloj())
                    {
                        Log.d("CONTRARRELOJ","Has pasado todos los nveles en contrarreloj!");
                        gm_.setBestTimeInSecs(gm_.getContrarrelojDuration()-gm_.getTimeLeft());
                        SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
                    }
                    else //Le pasamos a un nivel mas dificil
                    {
                        Log.d("CONTRARRELOJ","Pasas a un nivel mas dificil");
                        Log.d("CONTRARRELOJ","Has tardado: "+levelDuration_+" segundos");

                        gm_.setTimePassedInLevel(levelDuration_);
                        GameInit gameInit = new GameInit(diff[GameManager.getInstance().getPasadosContrarreloj()]);
                        GameManager.getInstance().setLevel(gameInit.getDifficulty());
                        SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());


                    }

                } else if (try_ == gameBoard_.getTotalTries() - 1) {
                    gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                    //Le pasamos a un nivel de la misma dificultad
                    LevelManager.getInstance().clearTries();
                    Log.d("CONTRARRELOJ","Pasas a un nivel igual de dificil");
                    Log.d("CONTRARRELOJ","Has tardado: "+levelDuration_+" segundos");

                    gm_.setTimePassedInLevel(levelDuration_);

                    GameInit gameInit = new GameInit(diff[GameManager.getInstance().getPasadosContrarreloj()]);
                    GameManager.getInstance().setLevel(gameInit.getDifficulty());
                    SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());

                } else {
                    gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                    gameBoard_.nexTry();
                }
            }

        }
    }
}
