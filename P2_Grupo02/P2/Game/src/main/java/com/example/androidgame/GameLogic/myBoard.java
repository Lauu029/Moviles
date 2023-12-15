package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;

public class myBoard extends GameObject {
    //Relativo a la dificultad del nivel
    private int codeColors_, usableColors_, tries_;
    private boolean canRepeat_;
    private boolean daltonics_;
    private int acutalTry_;

    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth_, sceneHeight_;
    private GameTry[] gameTries_;
    private SolutionCircle[] usableColorsCircles_;
    private GameManager gm_;
    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<GameObject> gameObjectsTable_ = new ArrayList<>();
    private Font font1_, font2_, font3_;
    private int hintsPos_;
    private boolean world_;
    //Colores totales que puede llegar a haber en una partida
    private int[] totalPossibleColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};

    myBoard(int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH, boolean world) {
        gm_ = GameManager.getInstance();
        world_ = world;
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
        gameTries_ = new GameTry[tries_];
        int offset = 100;
        for (int i = 0; i < tries_; i++) {
            gameTries_[i] = new GameTry(codeColors_, i, 40, false);
            gameTries_[i].init();
            gameTries_[i].TranslateY(offset);
            offset += 50;
            gameObjectsTable_.add(gameTries_[i]);
        }
        int circleRad_ = 20;
        offset = 4;
        int totalCircleWidth = usableColors_ * (circleRad_ * 2 + offset); // Ancho total de todos los círculos
        int x_ = (sceneWidth_ - totalCircleWidth) / 2;
        for (int i = 0; i < usableColors_; i++) {
            Log.d("MainActivity", "Color added");
            usableColorsCircles_[i] = new SolutionCircle(Integer.toString(i), this.font1_, 20, 0, 0, -1, world_);
            usableColorsCircles_[i].setPositions(x_ + i * (circleRad_ * 2 + offset) + circleRad_, sceneHeight_ - 45 + circleRad_);
            usableColorsCircles_[i].setColor_(totalPossibleColors[i]);
            usableColorsCircles_[i].setImage("" + (i + 1));
            usableColorsCircles_[i].setIdColor_(i);
            gameObjectsTable_.add(usableColorsCircles_[i]);
        }
    }

    /*Avanza al siguiente intento y resetea la solución tempporal, avisa a todos los cículos para que
     * actualicen la información del intento actual de la partida*/
    void nexTry() {
        acutalTry_++;
        gm_.resetLevelSolution();
        for (int i = 0; i < this.tries_; i++) {
            gameTries_[i].setGameTry(acutalTry_);
        }
    }

    //Coloca las pistas para el siguiente intento dependiendo de la cantidad de colores y posiciones correctas
    public void setNewHints(int correctPositions, int correctColors) {
        gameTries_[acutalTry_].setNewHints(correctPositions, correctColors);
    }

    @Override
    public void update(double time) {
        for (GameObject g : gameObjectsTable_) {
            g.update(time);
        }
    }

    @Override
    public void render(Graphics graph) {
        graph.setColor(AssetsManager.getInstance().getButtonColor());
        graph.fillRectangle(0, sceneHeight_ - 50, sceneWidth_, 50);
        graph.setFont(font2_);
        graph.setColor(AssetsManager.getInstance().getLineColor());
        graph.drawText("Averigua el codigo", sceneWidth_ / 2, 10);
        graph.setFont(font3_);
        graph.drawText("Te quedan " + (this.tries_ - acutalTry_) + " intentos", sceneWidth_ / 2, 50);
        for (GameObject g : gameObjectsTable_) {
            g.render(graph);
        }
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

    public int getAcutalTry_() {
        return acutalTry_;
    }

    public void putNewColor(int id, int color) {
        gameTries_[acutalTry_].putNewColor(id, color);
    }

}
