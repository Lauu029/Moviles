package com.example.androidgame.GameLogic.chronoMode;

import android.util.Log;

import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.EndScene;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

import java.util.ArrayList;

public class ChronoGameScene extends GameScene {
    private int index_;
    private double restingTime_;

    public ChronoGameScene(int nScene, double startTime) {
        super();
        index_=nScene;
        restingTime_=startTime;
    }

    //Inicializa los botones, el tablero y la solución
    public void init() {
        super.init();
        gameBoard_.disableCheats(false);
    }

    /*Comprueba si todas las casillas del intento actual se han llenado con algún color
     * -1 significa que no tienen color. Si está completa se comprueba si la solución es correcta,
     * si se ha ganado el juego o perdido por superar el numero de  intentos y si no se ha ganado
     * ni se ha acabado crea nuevas pistas en la clase tablero y avanza al siguiente intento*/
    public void update(double time) {
        restingTime_ -= time;
        if (scroll) {
            int speed = yFin - yIni;
            if ((gameBoard_.getUpTryPos() < upRenderPos_ && speed > 0) || (gameBoard_.getDownTryPos() > downRenderPos_ && speed < 0)) {
                gameBoard_.TranslateY(speed);
            }
            yIni = yFin;
        }
        checkSolution();
        super.update(time);
        if(restingTime_<=0){
            EndScene end = new EndScene(false, mySolution_.getSol(), 0);
            end.changeEndText("Se acabó el tiempo!!");
            SceneManager.getInstance().addScene(end, SceneNames.FINAL.ordinal());
        }
    }
    @Override
    public void render(){
        super.render();
        int minutes = (int)restingTime_/60;
        int seconds = (int)restingTime_-(minutes*60);
        iEngine_.getGraphics().setColor(0xFF000000);
        String minutesText = String.valueOf(minutes);
        String secondsText = String.valueOf(seconds);
        Log.d("Time",""+minutesText);
        Log.d("Time",""+secondsText);
        iEngine_.getGraphics().drawText(minutesText+" : "+secondsText, width_ / 2, 80);
    }
@Override
    protected void ChangeEndScene(boolean win, int try_) {
        EndChrono end = new EndChrono(win, mySolution_.getSol(),restingTime_, index_, gameBoard_.getTotalTries());
        SceneManager.getInstance().addScene(end, SceneNames.FINAL.ordinal());
    }

//    protected void checkSolution() {
//        int[] tempSol = gm_.getLevelSolution();
//        int i = 0;
//        boolean isComplete = true;
//        while (i < tempSol.length && isComplete) {
//            if (tempSol[i] == -1)
//                isComplete = false;
//            i++;
//        }
//        if (isComplete) {
//            mySolution_.check(tempSol);
//            int try_ = this.gameBoard_.getAcutalTry_();
//            if (mySolution_.getCorrectPos(try_) == this.lev_.getSolutionColors()) {
//                ChangeEndScene(true, try_);
//                LevelManager.getInstance().clearTries();
//
//            } else if (try_ == gameBoard_.getTotalTries() - 1) {
//                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
//                ChangeEndScene(false, try_);
//
//            }else{
//                gameBoard_.setNewHints(mySolution_.getCorrectPos(try_), mySolution_.getCorrectColor(try_));
//                gameBoard_.nexTry();
//            }
//        }
//    }
}
