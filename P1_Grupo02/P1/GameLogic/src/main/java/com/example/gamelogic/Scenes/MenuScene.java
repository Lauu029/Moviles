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
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SceneManager;
import com.example.gamelogic.IGameObject;

import java.util.ArrayList;

public class MenuScene extends Scene {
    private ArrayList<IGameObject> GameObjects_ = new ArrayList<>();
    private Button playButton_;
    private IFont font_;
    private IFont fontButton_;
    private IImage myIcon_;
    private ISound myButtonSound_;
    public MenuScene() {
        super();
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Jugar", fontButton_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new LevelScene(), SceneNames.DIFFICULTY.ordinal());
            }
        });

        addGameObject(playButton_);

        myIcon_ = graph.newImage("cerebro.png");


    }

    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 100);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 140, 80, 80);
    }

}
