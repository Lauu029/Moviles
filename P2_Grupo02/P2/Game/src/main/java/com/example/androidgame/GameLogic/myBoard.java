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
    private GameManager gm_;
    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<GameObject> gameObjectsTable_ = new ArrayList<>();
    private Font font1_, font2_, font3_;
    private int hintsPos_;
    private boolean world_;

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

        gameTries_=new GameTry[tries_];
        int offset=100;
        for (int i = 0; i < tries_; i++) {
            gameTries_[i] = new GameTry(codeColors_,i,40,false);
            gameTries_[i].init();
            gameTries_[i].TranslateY(offset);
            offset+=50;
            gameObjectsTable_.add(gameTries_[i]);
        }
    }
    /*Avanza al siguiente intento y resetea la solución tempporal, avisa a todos los cículos para que
     * actualicen la información del intento actual de la partida*/
    void nexTry() {
        acutalTry_++;
        gm_.resetLevelSolution();
        for (int i = 0; i < this.tries_; i++) {
            for (int j = 0; j < 3; j++) {
                gameTries_[i].setGameTry(acutalTry_);
                Log.d("MainActivity", "actualtry: " + acutalTry_);
            }
        }
    }

    //Coloca las pistas para el siguiente intento dependiendo de la cantidad de colores y posiciones correctas
    public void setNewHints(int correctPositions, int correctColors) {
        for (GameTry t: gameTries_) {
            t.setNewHints(correctPositions,correctColors);
        }
    }

    @Override
    public void update(double time) {
        for (GameObject g : gameObjectsTable_) {
            g.update(time);
        }
    }

    //Coloca los círculos centrados en la pantalla dependiendo del espacio disponible y la cantidad de círculos que haya que poner
    private void setCirclesPositions() {
//        int offset = 4;
//        int width_ = gm_.getWidth();
//        int totalCircleWidth = usableColors_ * (this.circleRad_ * 2 + offset); // Ancho total de todos los círculos
//        int x_ = (width_ - totalCircleWidth) / 2;
//        for (int i = 0; i < usableColors_; i++) {
//            usableColorsCircles_[i].setPositions(x_ + i * (this.circleRad_ * 2 + offset) + this.circleRad_, yPositions_[12] + this.circleRad_);
//        }
    }

    @Override
    public void render(Graphics graph) {
        graph.setColor(AssetsManager.getInstance().getButtonColor());
        graph.fillRectangle(0, sceneHeight_-50, sceneWidth_, 50);
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
//        for (int i = 0; i < this.codeColors_; i++) {
//            if (!playerTries_[acutalTry_][i].hasColor()) {
//                playerTries_[acutalTry_][i].setColor(color, id);
//                playerTries_[acutalTry_][i].setImage("" + (id + 1));
//                this.gm_.putColorSolution(i, id);
//                break;
//            }
//        }
    }

}
