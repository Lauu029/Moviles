package com.example.gamelogic.Scenes;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.gamelogic.Buttons.Button;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.IGameObject;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Managers.SceneManager;

import java.io.IOException;
import java.util.ArrayList;

public class SaveScene extends Scene{
    private Button saveButton_,exitButton_;
    private IFont font_;
    private ISound myButtonSound_;

    public SaveScene() {
        super();
    }
    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        this.saveButton_ = new Button("Si", font_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2-100, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                //try{
                //GameManager.getInstance_().saveGameProgress();
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());

                //}catch (IOException e){}
            }
        });
        this.exitButton_ = new Button("No", font_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 -20, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.GAME.ordinal());
            }
        });

        addGameObject(saveButton_);
        addGameObject(exitButton_);
    }
    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("¿Quieres salir?", width_ / 2, 100);

    }
}

