package com.example.gamelogic.Scenes;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Buttons.Button;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.Buttons.ButtonText;
import com.example.gamelogic.Managers.AudioManager;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SceneManager;
import com.example.gamelogic.IGameObject;

import java.awt.Color;
import java.util.ArrayList;

public class MenuScene extends Scene {
    private ArrayList<IGameObject> GameObjects_ = new ArrayList<>();
    private Button playButton_,tituloButton;
    private IFont font_;
    private IFont fontButton_;
    private IImage myIcon_;
    private ISound myButtonSound_,menuMusic_;
    private boolean nuevoAspecto,mute;
    public MenuScene() {
        super();
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        nuevoAspecto=false;
        mute=false;
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Jugar", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new LevelScene(), SceneNames.DIFFICULTY.ordinal());
                AudioManager.getInstance_().pauseSceneMusic(SceneNames.MENU);
            }
        });

        addGameObject(playButton_);

        myIcon_ = graph.newImage("cerebro.png");

        this.tituloButton = new ButtonText("MasterMind", fontButton_,0XFF222222,
                50, 50, (width_ / 2)-25, (height_/2)-200, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                nuevoAspecto=!nuevoAspecto;
                GameManager.getInstance_().setNuevoAspecto(nuevoAspecto);
                System.out.println("Pulsado texto menu: "+nuevoAspecto);
                mute=!mute;
                iEngine_.getAudio().muteAllSound(mute);
                GameManager.getInstance_().setEasyMode(true);
            }
        });
        addGameObject(tituloButton);
        menuMusic_ = iEngine_.getAudio().newSound("musicaMenu.wav");
        AudioManager.getInstance_().setAudioEngine(iEngine_.getAudio());
        AudioManager.getInstance_().addSceneMusic(SceneNames.MENU,menuMusic_);
        AudioManager.getInstance_().playSceneMusic(SceneNames.MENU);
    }
    @Override
    public void restart(){
        AudioManager.getInstance_().unPauseSceneMusic(SceneNames.MENU);
    }

    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        //iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 100);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 140, 80, 80);
    }

}
