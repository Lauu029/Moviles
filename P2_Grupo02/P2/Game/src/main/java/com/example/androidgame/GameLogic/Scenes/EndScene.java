package com.example.androidgame.GameLogic.Scenes;

import android.graphics.Bitmap;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.ImageProcessingCallback;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.GameInit;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Utils.RewardedAddBehaviour;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Theme;

import java.util.ArrayList;

public class EndScene extends Scene {

    protected int[] sol_;

    protected Font font_, font1_, font2_;
    protected int tries_;
    protected Sound myButtonSound_;
    protected boolean waitingForReward_;
    protected int[] totalPossibleColors_ = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};
    protected ArrayList<Image> images_ = new ArrayList<Image>();
    ;
    protected boolean win_ = false;
    protected ImageProcessingCallback callback;
    protected Theme tematica_;

    public EndScene(boolean win, int[] sol, int intentos) {
        super();
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
        this.win_ = win;
        this.sol_ = sol;
        waitingForReward_ = false;
        tries_ = intentos;
        callback = new ImageProcessingCallback() {
            @Override
            public void processImage(Bitmap bitmap) {
                iEngine_.getMobile().processImage(bitmap);
            }
        };
    }

    public void init() {
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
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
        Graphics graph = iEngine_.getGraphics();
        playAgainButton_ = new Button("Volver Jugar", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 - 50, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                Engine engine_ = GameManager.getInstance().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
            }
        });

        buttonDificulty_ = new Button("Elegir Dificultad", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 25,
                /*SceneNames.LEVEL,*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());

                SceneManager.getInstance().setScene(SceneNames.DIFFICULTY.ordinal());
            }
        });
        if (win_) {
            shareRecordButton_ = new Button("Compartir", font1_, AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                    150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 100,
                    myButtonSound_, new ButtonClickListener() {
                @Override
                public void onClick() {
                    graph.generateScreenshot(0, 0, width_, height_ / 3, callback);
                }
            });
            addGameObject(shareRecordButton_);
        }
        if (!win_) {
            buttonReward_ = new Button("+2 Intentos", font1_, AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                    150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 100,
                    myButtonSound_, null);
            buttonReward_.setAction(new ButtonClickListener() {
                @Override
                public void onClick() {
                    GameScene gs = (GameScene) SceneManager.getInstance().getScene(SceneNames.GAME.ordinal());
                    gs.addTriesToBoard(2);
                    iEngine_.getMobile().LoadRewardedAd();
                    waitingForReward_ = true;
                    buttonReward_.changeActive(false);
                }
            });
            addGameObject(buttonReward_);
        }


        RewardedAddBehaviour rewardedAddBehaviour = new RewardedAddBehaviour(false);
        iEngine_.getMobile().assignRewardPrice(rewardedAddBehaviour);
        tematica_ = AssetsManager.getInstance().getCirleTheme(false);
        if (tematica_.getName() != "DEFAULT") {
            for (int i = 0; i < sol_.length; i++) {

                Image im = graph.newImage(tematica_.getPathBolas() + ("" + (sol_[i] + 1)) + ".png");
                images_.add(im);

            }
        }
        addGameObject(playAgainButton_);
        addGameObject(buttonDificulty_);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getTextColor());
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

    void drawCircles(Graphics graph) {
        int offset = 20;
        int radius = 20;
        int totalCircleWidth = sol_.length * (radius * 2 + offset); // Ancho total de todos los círculos
        int x = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < sol_.length; i++) {
            if (tematica_.getName() != "DEFAULT") {
                graph.drawImage(images_.get(i), x + i * (radius * 2 + offset), 150, radius * 2, radius * 2);
            } else {
                graph.setColor(totalPossibleColors_[sol_[i]]);
                graph.drawCircle(x + i * (radius * 2 + offset) + radius, 150 + radius, radius);
            }

            if (GameManager.getInstance().getDaltonic()) {
                graph.setColor(0xFF000000);
                graph.setFont(this.font1_);
                graph.drawText(sol_[i] + "", x + i * (radius * 2 + offset) + radius, 150 + (radius));
            }
        }
    }
}
