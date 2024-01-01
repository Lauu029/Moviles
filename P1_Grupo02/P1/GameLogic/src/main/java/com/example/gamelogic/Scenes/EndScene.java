package com.example.gamelogic.Scenes;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.ISound;
import com.example.gamelogic.Buttons.Button;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.GameInit;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SceneManager;

import java.util.ArrayList;

public class EndScene extends Scene {

    protected int[] sol_;

    protected IFont font_, font1_, font2_;
    protected int tries_;
    protected ISound myButtonSound_;
    protected boolean waitingForReward_;
    protected int[] totalPossibleColors_ = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};
    protected boolean win_ = false;

    public EndScene(boolean win, int[] sol, int intentos) {
        super();
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
        this.win_ = win;
        this.sol_ = sol;
        waitingForReward_ = false;
        tries_ = intentos;
    }

    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        if (win_)
            this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        else this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, false, true);
        graph.setFont(this.font_);
        font1_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        font2_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 30, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");
        initButtons();
    }

    protected void initButtons() {
        Button buttonDificulty_, buttonReward_, playAgainButton_, shareRecordButton_;
        IGraphics graph = iEngine_.getGraphics();
        playAgainButton_ = new Button("Volver Jugar", font1_, 0XD0FB839B, 150, 50, 35,
                this.width_ / 2 - 150 / 2, this.height_ / 2 - 50, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(GameManager.getInstance_().getLevel().getLevelDiff_());
                GameManager.getInstance_().setLevel(gameInit.getDifficulty());
                IEngine engine_ = GameManager.getInstance_().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
            }
        });

        buttonDificulty_ = new Button("Elegir Dificultad", font1_, 0XD0FB839B, 150, 50, 35,
                this.width_ / 2 - (150 / 2), this.height_ / 2 + 25, myButtonSound_,
                new ButtonClickListener() {
                    @Override
                    public void onClick() {
                        GameInit gameInit = new GameInit(GameManager.getInstance_().getLevel().getLevelDiff_());
                        GameManager.getInstance_().setLevel(gameInit.getDifficulty());

                        SceneManager.getInstance().setScene(SceneNames.DIFFICULTY.ordinal());
                    }
                });

        addGameObject(playAgainButton_);
        addGameObject(buttonDificulty_);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().setColor(0xFF000000);
        if (!win_) {
            iEngine_.getGraphics().drawText("GAME OVER", width_ / 2, 10);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("Te has quedado sin intentos", width_ / 2, 50);
        } else {
            iEngine_.getGraphics().drawText("ENHORABUENA!!", width_ / 2, 10);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("Has averiguado el codigo en", width_ / 2, 50);
            iEngine_.getGraphics().setFont(font2_);
            iEngine_.getGraphics().drawText(tries_ + " intentos:", width_ / 2, 80);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("codigo:", width_ / 2, 120);
            drawCircles(iEngine_.getGraphics());
        }
    }

    void drawCircles(IGraphics graph) {
        int offset = 20;
        int radius = 20;
        int totalCircleWidth = sol_.length * (radius * 2 + offset); // Ancho total de todos los círculos
        int x = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < sol_.length; i++) {
            graph.setColor(totalPossibleColors_[sol_[i]]);
            graph.drawCircle(x + i * (radius * 2 + offset) + radius, 150 + radius, radius);

            if (GameManager.getInstance_().getDaltonic()) {
                graph.setColor(0xFF000000);
                graph.setFont(this.font1_);
                graph.drawText(sol_[i] + "", x + i * (radius * 2 + offset) + radius, 150 + (radius));
            }
        }
    }
}
