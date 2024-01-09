package com.example.androidgame.GameLogic.Scenes;


import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.SensorHandler;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.GameInit;
import com.example.androidgame.GameLogic.GameObject;
import com.example.androidgame.GameLogic.GameTry;
import com.example.androidgame.GameLogic.LevelDifficulty;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.DifficultyScene;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;
import com.example.androidgame.GameLogic.Scenes.ShopScene;
import com.example.androidgame.GameLogic.Scenes.WorldScene;
import com.example.androidgame.GameLogic.Theme;

import java.util.ArrayList;

public class MenuScene extends Scene {
    private Button playButton_,contrarrelojButton_,skipLevelButton_;
    private Button storeButton_;
    private Button mundoButton_;
    private Font font_;
    private Font fontButton_;
    private Image myIcon_;
    private Sound myButtonSound_,maracaSound_;
    private float lastShakeTime=0.0f;
    private  final float SHAKE_THRESHOLD = 10f;
    float timeRecord;
    SensorHandler sensor_;
    public MenuScene() {
        super();
    }
    @Override
    public void init() {
        sensor_=new SensorHandler(iEngine_.getMainActivity());
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");
        maracaSound_ = iEngine_.getAudio().newSound("maraca.mp3");

        this.playButton_ = new Button("Partida Rapida", fontButton_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 -80, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                sensor_.onResume();
                SceneManager.getInstance().addScene(new DifficultyScene(), SceneNames.DIFFICULTY.ordinal());
            }
        });
        this.mundoButton_ = new Button("Explorar Mundos", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                sensor_.onResume();
                SceneManager.getInstance().addScene(new WorldScene(), SceneNames.WORLD.ordinal());
            }
        });
        this.storeButton_ = new Button("Personalizar", fontButton_, AssetsManager.getInstance().getLineColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getButtonColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 120, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                sensor_.onResume();
                SceneManager.getInstance().addScene(new ShopScene(), SceneNames.SHOP.ordinal());
            }
        });
        this.contrarrelojButton_ = new Button("Contrarreloj", fontButton_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 60, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                sensor_.onResume();
                GameManager.getInstance().setContrarreloj(true);
                GameManager.getInstance().reset();
                Log.d("CONTRARRELOJ","Has entrado en modo contrarreloj");
                GameInit gameInit = new GameInit(LevelDifficulty.FACIL);
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
            }
        });
        this.skipLevelButton_= new Button("Skip", fontButton_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(), 50, 50, 0,
                this.width_ - 50, this.height_ / 2, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                sensor_.onResume();
                GameManager.getInstance().setShortcut(true);
                //SceneManager.getInstance().addScene(new WorldScene(), SceneNames.WORLD.ordinal());
                LevelManager.getInstance().changeShortcutTheme();
                Theme tema=LevelManager.getInstance().getTema();
                AssetsManager.getInstance().setWorldTheme(tema);
                LevelManager.getInstance().setActualLevel(LevelManager.getInstance().getPassedLevel());
                ArrayList<Difficulty> diff = LevelManager.getInstance().getDiff();
                GameManager.getInstance().setLevel(diff.get(LevelManager.getInstance().getPassedLevel()));
                SceneManager.getInstance().addScene(new WorldGameScene(), SceneNames.WORLD_SCENE.ordinal());
            }
        });
        addGameObject(playButton_);
        addGameObject(storeButton_);
        addGameObject(mundoButton_);
        addGameObject(contrarrelojButton_);
        addGameObject(skipLevelButton_);
        myIcon_ = graph.newImage("logo.png");

    }
    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getLineColor());
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 30);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 220, 80, 80);

        timeRecord=GameManager.getInstance().getBestTimeInSecs();

        float seconds=timeRecord%60;
        float minutes=(timeRecord%3600)/60;
        if(minutes<10)
        {
            iEngine_.getGraphics().drawText("0"+Math.round(minutes),width_ / 2-30, 170 );
        }
        else iEngine_.getGraphics().drawText(String.valueOf(Math.round(minutes)), width_ / 2-30, 170);
        iEngine_.getGraphics().drawText(":",width_ / 2, 170 );
        if(seconds<10)
        {
            iEngine_.getGraphics().drawText("0"+Math.round(seconds), width_ / 2+30, 170);
        }
        else iEngine_.getGraphics().drawText(String.valueOf(Math.round(seconds)),width_ / 2+30, 170 );
    }
    @Override
    public void update(double time) {
        float[]axis=sensor_.getAxis();

        // Calcular la aceleración total
        float acceleration = axis[0]* axis[0] + axis[1] * axis[1] + axis[2]* axis[2];
        lastShakeTime+=time;

        // Verificar si se ha producido un "shake"
        if (acceleration > (SHAKE_THRESHOLD*SHAKE_THRESHOLD)) {
            if ( lastShakeTime > 0.75) {
               lastShakeTime = 0;
                GameManager.getInstance().getIEngine().getAudio().playSound(maracaSound_, 0);
            }
        }

        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).update(time);
        }
    }
    @Override

    public void handleInput(ArrayList<TouchEvent> events) {
        for (GameObject g : gameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }
}