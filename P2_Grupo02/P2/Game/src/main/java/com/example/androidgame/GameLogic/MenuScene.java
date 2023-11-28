package com.example.androidgame.GameLogic;


import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkRequest;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.R;


import java.io.Console;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MenuScene extends Scene {
    private Button playButton_;
    private Button storeButton_;
    private Button mundoButton_;
    private Font font_;
    private Font fontButton_;
    private Image myIcon_;
    private Sound myButtonSound_;

    public MenuScene() {
        super();
    }

    public void init() {
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Partida Rapida", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 -80, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new LevelScene());
            }
        });
        this.mundoButton_ = new Button("Explorar Mundos", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new NivelScene());
            }
        });
        this.storeButton_ = new Button("Personalizar", fontButton_, 0XFFbf5061
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 120, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new ShopScene());
            }
        });

        addGameObject(playButton_);
        addGameObject(storeButton_);
        addGameObject(mundoButton_);
        myIcon_ = graph.newImage("cerebro.png");
//        WorkRequest workRequest = new OneTimeWorkRequest.Builder(TimedWorker.class)
//                .setInitialDelay(20, TimeUnit.SECONDS)
//                .setInputData(inputData) // Corregido el nombre del método
//                .build();
        iEngine_.getMobile().createNotification(R.drawable.ic_launcher_foreground);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 30);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 220, 80, 80);
    }

}