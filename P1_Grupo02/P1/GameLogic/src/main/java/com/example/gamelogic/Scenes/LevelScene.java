package com.example.gamelogic.Scenes;


import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Buttons.Button;
import com.example.gamelogic.Buttons.ButtonClickListener;
import com.example.gamelogic.Buttons.ButtonImage;
import com.example.gamelogic.GameInit;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.GameObject;
import com.example.gamelogic.LevelDifficulty;
import com.example.gamelogic.Managers.SceneManager;

import java.util.ArrayList;

public class LevelScene extends Scene {
    private ArrayList<GameObject> iGameObjects_ = new ArrayList<>();

    private IFont font_;
    private ISound myButtonSound_;
    private ISound myArrowSound_;
    public LevelScene() {
        super();
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();

        String[] names = {"Facil",
                "Medio",
                "Dificil",
                "Imposible"};

        LevelDifficulty[] diff = {LevelDifficulty.FACIL,
                LevelDifficulty.MEDIO,
                LevelDifficulty.DIFICIL,
                LevelDifficulty.IMPOSIBLE};
        int[] colors = {0XFFF6C0CF, 0XFFDDB5DF, 0XFFA9B2EC, 0xFF58B2E6};
        font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            final LevelDifficulty difficulty_=diff[i];
            Button but = new Button(names[i], font_, colors[i], 150, 50, 35, this.width_ / 2 - 150 / 2,
                    100 * i + 100, myButtonSound_, new ButtonClickListener() {
                @Override
                public void onClick() {
                    GameInit gameInit = new GameInit(difficulty_);
                    GameManager.getInstance_().setLevel(gameInit.getDifficulty());
                    SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
                }
            });

            this.addGameObject(but);
        }
        myArrowSound_=iEngine_.getAudio().newSound("arrowButton.wav");
        ButtonImage returnButton_ = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        });
        this.addGameObject(returnButton_);
    }


    public void addGameObject(GameObject gm) {
        iGameObjects_.add(gm);
    }

    @Override
    public int getHeight_() {
        return height_;
    }

    @Override
    public int getWidth_() {
        return width_;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (GameObject g : iGameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    @Override
    public void render() {
        IGraphics graph = iEngine_.getGraphics();

        graph.clear(0xFFfff0f6);
        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).render(graph);
        }
        this.iEngine_.getGraphics().setFont(font_);
        graph.setColor(0xFF000000);
        graph.drawText("¿En que dificultad quieres jugar?", width_ / 2, 50);
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).update(time);
        }
    }
}
