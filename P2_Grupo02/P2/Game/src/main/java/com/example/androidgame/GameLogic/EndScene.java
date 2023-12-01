package com.example.androidgame.GameLogic;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.ImageProcessingCallback;
import com.example.androidengine.Sound;

import java.util.ArrayList;

public class EndScene extends Scene {

    private int[] sol_;
    private Button buttonDificulty_, buttonReward_, playAgainButton_,shareRecordButton_;
    private Font font_, font1_, font2_;
    private int tries_;
    private Sound myButtonSound_;
    private int[] totalPossibleColors_ = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};
    ArrayList<Image>images_ = new ArrayList<Image>();;
    private boolean win_ = false;
    private ImageProcessingCallback callback;
    EnumTheme tematica_;
    public EndScene( boolean win, int[] sol, int intentos) {
       super();
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
        this.win_ = win;
        this.sol_ = sol;
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
        this.playAgainButton_ = new Button("Volver Jugar", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20,
                /* SceneNames.GAME, GameManager.getInstance_().getLevel().getLevelDiff_(),*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                //GameScene gameScene = (GameScene) scene_;
                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                Engine engine_ = GameManager.getInstance().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                GameManager.getInstance().changeScene(new GameScene());
            }
        });

        this.buttonDificulty_ = new Button("Elegir Dificultad", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 90,
                /*SceneNames.LEVEL,*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                GameManager.getInstance().changeScene(new LevelScene());
                //scene_ = new LevelScene(engine_, sceneWidth, sceneHeight);
            }
        });
        this.buttonReward_ = new Button("Nuevas pistas", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 160,
                 myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                iEngine_.getMobile().LoadRewardedAd();
            }
        });
        this.shareRecordButton_ = new Button("Compartir", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 230,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                graph.generateScreenshot(0, 0, width_, height_ / 2 - 40, callback);
            }
        });
         tematica_=AssetsManager.getInstance().getCirleTheme();
        if(tematica_!= EnumTheme.DEFAULT){
            for(int i=0;i<sol_.length;i++){
                Log.d("OREO", tematica_.getPath()+(""+(i+1))+".png");
                Image im=graph.newImage(tematica_.getPath()+(""+(sol_[i]+1))+".png");
                images_.add(im);

            }
        }
        addGameObject(playAgainButton_);
        addGameObject(buttonDificulty_);
        addGameObject(buttonReward_);
        addGameObject(shareRecordButton_);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        if (!win_) {
            iEngine_.getGraphics().drawText("GAME OVER", width_ / 2, 50);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("Te has quedado sin intentos", width_ / 2, 120);
            iEngine_.getGraphics().drawText("codigo:", width_ / 2, height_ / 2 - 70);
            drawCircles(iEngine_.getGraphics());

        } else {
            iEngine_.getGraphics().drawText("ENHORABUENA!!", width_ / 2, 50);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("Has averiguado el codigo en", width_ / 2, 120);
            iEngine_.getGraphics().setFont(font2_);
            iEngine_.getGraphics().drawText(tries_ + " intentos:", width_ / 2, height_ / 2 - 110);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("codigo:", width_ / 2, height_ / 2 - 70);
            drawCircles(iEngine_.getGraphics());
        }

    }

    void drawCircles(Graphics graph) {
        int offset = 20;
        int radius = 20;
        int totalCircleWidth = sol_.length * (radius * 2 + offset); // Ancho total de todos los círculos
        int x = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < sol_.length; i++) {
            if(tematica_!= EnumTheme.DEFAULT){
                graph.drawImage(images_.get(i),x + i * (radius * 2 + offset),height_ / 2 - 50,radius*2,radius*2);
            }
            else{
                graph.setColor(totalPossibleColors_[sol_[i]]);
                graph.drawCircle(x + i * (radius * 2 + offset) + radius, height_ / 2 + radius - 50, radius);
            }

            if (GameManager.getInstance().getDaltonic()) {
                graph.setColor(0xFF000000);
                graph.setFont(this.font1_);
                graph.drawText(sol_[i] + "", x + i * (radius * 2 + offset) + radius, height_ / 2 + (radius) - 53);
            }
        }
    }
}
