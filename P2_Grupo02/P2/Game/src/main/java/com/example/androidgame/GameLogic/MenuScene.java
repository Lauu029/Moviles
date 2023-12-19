package com.example.androidgame.GameLogic;


import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.R;

import java.util.ArrayList;
import java.util.Optional;

public class MenuScene extends Scene {
    private Button playButton_;
    private Button storeButton_;
    private Button mundoButton_;
    private Font font_;
    private Font fontButton_;
    private Image myIcon_;
    private Sound myButtonSound_;
    boolean scroll;
    int yIni;
    int yFin;
    GameTry game;
    public MenuScene() {
        super();
    }
    @Override
    public void init() {

        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Partida Rapida", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 -80, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new DifficultyScene(),SceneNames.DIFFICULTY.ordinal());
            }
        });
        this.mundoButton_ = new Button("Explorar Mundos", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new WorldScene(), SceneNames.WORLD.ordinal());
            }
        });
        this.storeButton_ = new Button("Personalizar", fontButton_, AssetsManager.getInstance().getLineColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getButtonColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 120, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new ShopScene(), SceneNames.SHOP.ordinal());
            }
        });

        addGameObject(playButton_);
        addGameObject(storeButton_);
        addGameObject(mundoButton_);
        myIcon_ = graph.newImage("logo.png");
       // game=new GameTry(6,1,40,false);

//        game.init();
//        game.TranslateY(60);
//        addGameObject(game);

//        iEngine_.getMobile().createNotification(R.drawable.logo);
    }
    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getLineColor());
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 30);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 220, 80, 80);
    }
    @Override
    public void update(double time) {
        if(scroll){
            int speed=yFin-yIni;
            game.TranslateY(speed/50);
        }

        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).update(time);
        }
    }
    @Override

    public void handleInput(ArrayList<TouchEvent> events) {
        for(TouchEvent event:events){
            if(event.type== TouchEvent.TouchEventType.TOUCH_DOWN){
                yIni=event.y;
            }
            else if(event.type==TouchEvent.TouchEventType.TOUCH_DRAG){
                scroll=true;
                yFin=event.y;
            }
            else if(event.type==TouchEvent.TouchEventType.TOUCH_UP){
                scroll=false;
            }
        }
        for (GameObject g : gameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }


}