package com.example.androidgame.GameLogic.Scenes;

import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;

import java.util.ArrayList;

public class WorldGameScene extends GameScene{
    private int actWorld;
    private int savedWorld;
    private int actLevel ;
    private int savedLevel;
    private ArrayList<ArrayList<Integer>> savedTries;
    ArrayList<Integer> savedSol;
    public WorldGameScene() {
        super();
    }
    public void init() {

        actWorld=LevelManager.getInstance().getActualWorld();
        savedWorld = LevelManager.getInstance().getSavedWorld();
        actLevel =LevelManager.getInstance().getActualLevel();
        savedLevel = LevelManager.getInstance().getSavedLevel();
        savedTries = LevelManager.getInstance().getTries();
        savedSol = LevelManager.getInstance().getCurrentSolution();
        super.init();
        saveSolution();
        LevelManager.getInstance().setTotalTries(gameBoard_.getTotalTries());

    }
    @Override
    protected void createSolution(){
        if (actWorld == savedWorld && actLevel == savedLevel && savedTries.size() != 0) {
            int[] sol = new int[savedSol.size()];
            for (int i = 0; i < savedSol.size(); i++) {
                sol[i] = savedSol.get(i);
            }
            mySolution_.setSolution(sol);
        } else {
            mySolution_.createSolution(lev_.isRepeat(), lev_.getSolutionColors(), lev_.getPosibleColors(), lev_.getTries());
        }

    }
    @Override
    public void update(double time) {
        super.update(time);
    }
    @Override
    public void render() {
        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        if (AssetsManager.getInstance().getBackgroundImage(true, true) != null)
            iEngine_.getGraphics().drawImage(AssetsManager.getInstance().getBackgroundImage(true, true),
                    0, 0, height_, width_);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }
    void saveSolution(){
        //Guardo mi solucion en el level manager
        ArrayList<Integer> mySol = new ArrayList<>();
        int[] array = mySolution_.getSol();
        for (int i = 0; i < mySolution_.getSol().length; i++) {
            int num = array[i];
            mySol.add(num);
        }
        LevelManager.getInstance().setCurrentSolution(mySol); //Guardamos la solucion de ete nivel

    }
    @Override
    protected void createGameBoard(){
        //En caso de que estemos restaurando una partida
        if (actWorld == savedWorld && actLevel == savedLevel && savedTries.size() != 0) {
            this.gameBoard_ = new Board(lev_.getSolutionColors(), LevelManager.getInstance().getTotalTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_, true);

            for (int i = 0; i < savedTries.size(); i++) {
                gameBoard_.getTryByIndex(i).setIdTries(savedTries.get(i));
                int[] gameTry = new int[savedTries.get(i).size()];
                for (int j = 0; j < gameTry.length; j++) {
                    gameTry[j] = savedTries.get(i).get(j);
                    gameBoard_.putColor(gameTry[j]);
                }
                mySolution_.check(gameTry);

                gameBoard_.setHints(mySolution_.getCorrectPos(i), mySolution_.getCorrectColor(i), i);
                gameBoard_.nexTry();
            }
        } else {
            this.gameBoard_ = new Board(lev_.getSolutionColors(), lev_.getTries(), lev_.getPosibleColors(), lev_.isRepeat(), width_, height_, true);
        }
    }
    @Override
    protected void changeSceneExit(){
        LevelManager.getInstance().clearTries();
        SceneManager.getInstance().addScene(new SaveDataScene(true), SceneNames.SAVE_SCENE.ordinal());
        //SceneManager.getInstance().addScene(new WorldScene(), SceneNames.WORLD.ordinal());
    }
    @Override
    protected void ChangeEndScene(boolean win, int try_) {
        WorldEndScene worldEnd = new WorldEndScene(win, mySolution_.getSol(), try_);
        SceneManager.getInstance().addScene(worldEnd, SceneNames.WORLD_FINAL.ordinal());
    }
    @Override
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

            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int j = 0; j < tempSol.length; j++) {
                tempArray.add(tempSol[j]);
            }
            LevelManager.getInstance().addTries(tempArray);

            mySolution_.check(tempSol);
            int try_ = this.gameBoard_.getAcutalTry_();
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
    }
}
