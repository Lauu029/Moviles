package com.example.gamelogic;


import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Circles.SolutionCircle;
import com.example.gamelogic.Managers.GameManager;

import java.util.ArrayList;

public class Board extends GameObject {
    //Relativo a la dificultad del nivel
    private int codeColors_, usableColors_, tries_;
    private boolean canRepeat_;
    private boolean daltonics_;
    private int acutalTry_;
    private int limitUp, limitDown;
    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth_, sceneHeight_;
    private ArrayList<GameTry> gameTries_;
    private SolutionCircle[] usableColorsCircles_;
    private GameManager gm_;
    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<GameObject> gameObjectsTable_ = new ArrayList<>();

    private IFont font1_, font2_, font3_;

    //Colores totales que puede llegar a haber en una partida
    private int[] totalPossibleColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB,0xFFFF00FF};
    private int upTryPos_,downTryPos_,upRenderPos_,downRenderPos_;
    public Board(int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH) {
        gm_ = GameManager.getInstance_();
        this.font1_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, false, false);
        this.codeColors_ = codeColors_;
        this.tries_ = tries_;
        this.usableColors_ = usableColors;
        this.canRepeat_ = canRepeat_;
        this.acutalTry_ = 0;
        this.sceneWidth_ = scW;
        this.sceneHeight_ = scH;
        this.daltonics_ = false;
        usableColorsCircles_ = new SolutionCircle[usableColors_];
        limitUp = sceneHeight_ / 6 + 10;
        limitDown = sceneHeight_ - 60;
        gameTries_ = new ArrayList<>();
        int offset = limitUp;
        upRenderPos_=55;
        downRenderPos_=sceneHeight_ - 50;

        upTryPos_=offset;
        for (int i = 0; i < tries_; i++) {
            GameTry g = new GameTry(codeColors_, i, 40, limitUp, limitDown);
            g.init();
            g.TranslateY(offset);
            offset += 50;
            gameTries_.add(g);
            gameObjectsTable_.add(g);
        }
        downTryPos_=offset-10;
        int circleRad_ = 20;
        offset = 4;
        int totalCircleWidth = usableColors_ * (circleRad_ * 2 + offset); // Ancho total de todos los círculos
        int x_ = (sceneWidth_ - totalCircleWidth) / 2;
        for (int i = 0; i < usableColors_; i++) {
            usableColorsCircles_[i] = new SolutionCircle(Integer.toString(i), this.font1_, 20, 0, 0, -1);
            usableColorsCircles_[i].setPositions(x_ + i * (circleRad_ * 2 + offset) + circleRad_, sceneHeight_ - 45 + circleRad_);
            usableColorsCircles_[i].setColor_(totalPossibleColors[i]);
            usableColorsCircles_[i].setIdColor_(i);
            gameObjectsTable_.add(usableColorsCircles_[i]);
        }
    }

    /*Avanza al siguiente intento y resetea la solución tempporal, avisa a todos los cículos para que
     * actualicen la información del intento actual de la partida*/
    public void nexTry() {
        acutalTry_++;
        gm_.resetLevelSolution();
        for (int i = 0; i < this.tries_; i++) {
            gameTries_.get(i).setGameTry(acutalTry_);
        }
    }

    //Coloca las pistas para el siguiente intento dependiendo de la cantidad de colores y posiciones correctas
    public void setNewHints(int correctPositions, int correctColors) {
        gameTries_.get(acutalTry_).setNewHints(correctPositions, correctColors);
    }
    public void setHints(int correctPositions, int correctColors, int myTry) {
        gameTries_.get(myTry).setNewHints(correctPositions, correctColors);
    }
    public int getUpTryPos(){
        return upTryPos_;
    }
    public int getDownTryPos(){
        return downTryPos_;
    }
    public int getdownRenderPos(){
        return limitDown;
    }
    public int getupRenderPos(){
        return limitUp;
    }
    @Override
    public void update(double time) {
        for (int i = 0; i < gameTries_.size(); i++) {
            gameTries_.get(i).update(time);
        }
        for (int i = 0; i < usableColorsCircles_.length; i++) {
            usableColorsCircles_[i].update(time);
        }
    }

    @Override
    public void render(IGraphics graph) {
        for (int i = 0; i < gameTries_.size(); i++)
            gameTries_.get(i).render(graph);
        graph.setColor(0XD0FB839B);
        graph.fillRectangle(0, sceneHeight_ - 50, sceneWidth_, 50);
        graph.setFont(font2_);
        graph.setColor(0XFF222222);
        graph.drawText("Averigua el codigo", sceneWidth_ / 2, 10);
        graph.setFont(font3_);
        graph.drawText("Te quedan " + (this.tries_ - acutalTry_) + " intentos", sceneWidth_ / 2, 50);
        for (int i = 0; i < usableColorsCircles_.length; i++)
            usableColorsCircles_[i].render(graph);
    }

    @Override
    public void init() {
        font2_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, true, false);
        font3_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 17, false, false);
    }


    @Override
    public boolean handleInput(TouchEvent event) {
        for (GameObject g : gameObjectsTable_)
            if (g.handleInput(event))
                return true;
        return false;
    }

    public void TranslateY(int transY) {
        for (int i = 0; i < gameTries_.size(); i++) {
            gameTries_.get(i).TranslateY(transY);
        }
        upTryPos_+=transY;
        downTryPos_+=transY;
    }
    public GameTry getTryByIndex(int index){
        return gameTries_.get(index);
    }
    public int getAcutalTry_() {
        return acutalTry_;
    }

    public void putNewColor(int id, int color) {
        gameTries_.get(acutalTry_).putNewColor(id, color);
    }
    public void putColor(int id) {
        gameTries_.get(acutalTry_).putNewColor(id, totalPossibleColors[id]);
    }

    public int getTotalTries(){
        return gameTries_.size();
    }
    public void changeDaltonics(boolean dalt) {
        for (GameTry g: gameTries_) {
            g.changeDaltonics(dalt);
        }
        for(SolutionCircle s : usableColorsCircles_){
            s.setDaltonics_(dalt);
        }
    }
}
