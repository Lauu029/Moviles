package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;

public class GameScene extends Scene {
    private Solution mySolution_;
    private ButtonColorBlind buttonColorBlind_;
    private Board gameBoard_;
    private Font font_;
    private Difficulty lev_;
    private GameManager gm_;
    private Sound myCrossSound_;
    boolean world_=false;
    Image backaground_;
    public GameScene(boolean world) {
        super();
        world_=world;

    }
    //Inicializa los botones, el tablero y la solución
    public void init() {

        if(AssetsManager.getInstance().getBackgrounTheme(world_).getName()!="DEFAULT"){
            String imagePath=AssetsManager.getInstance().getBackgrounTheme(world_).getPath()+"foreground.jpg";
            backaground_=iEngine_.getGraphics().newImage(imagePath);
        }

        this.font_ = this.iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        this.gm_ = GameManager.getInstance();
        this.lev_ = this.gm_.getLevel();
        mySolution_ = new Solution();
        mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());
        this.gameBoard_ = new Board( lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_,world_);
        addGameObject(gameBoard_);
        gm_.setBoard_(this.gameBoard_);
        Sound buttonSound= GameManager.getInstance().getIEngine().getAudio().newSound("colorBlindButton.wav");
        this.buttonColorBlind_ =new ButtonColorBlind("eye_open.png","eye_closed.png",
                40, 40, this.width_ - 45, 0,buttonSound, new ButtonClickListener() {
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
        ButtonImage exitLevel_=new ButtonImage("cruz.png",  40, 40, 5, 0, /*SceneNames.LEVEL,*/
                myCrossSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().switchToPreviousScene();
            }
        });
        this.addGameObject(exitLevel_);
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
                ChangeEndScene(true,try_);

            } else if (try_ == lev_.getTries() -1) {
                ChangeEndScene(false,try_);
            } else {
                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
                gameBoard_.nexTry();
            }
        }
        super.update(time);
    }
    protected void ChangeEndScene(boolean win,int try_){
        if(!world_){
            EndScene end = new EndScene( win, mySolution_.getSol_(), try_);
            SceneManager.getInstance().addScene(end);
        }
        else{
            WorldEndScene worldEnd=new WorldEndScene(win, mySolution_.getSol_(), try_);
            SceneManager.getInstance().addScene(worldEnd);
        }

    }
    @Override
    public void render(){

        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        if(backaground_!=null)
        iEngine_.getGraphics().drawImage(backaground_,0,0,GameManager.getInstance().getHeight_(),GameManager.getInstance().getwidth());
        if (GameManager.getInstance().getBackgroundImage() != null) {
            iEngine_.getGraphics().drawImage(GameManager.getInstance().getBackgroundImage(), 0, 0, height_, width_);
        }
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }
}
