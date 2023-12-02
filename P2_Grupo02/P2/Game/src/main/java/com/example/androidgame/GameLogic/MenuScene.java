package com.example.androidgame.GameLogic;


import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.R;

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
                SceneManager.getInstance().addScene(new DifficultyScene());
            }
        });
        this.mundoButton_ = new Button("Explorar Mundos", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new WorldScene());
            }
        });
        this.storeButton_ = new Button("Personalizar", fontButton_, AssetsManager.getInstance().getLineColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getButtonColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 120, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new ShopScene());
            }
        });

        addGameObject(playButton_);
        addGameObject(storeButton_);
        addGameObject(mundoButton_);
        myIcon_ = graph.newImage("logo.png");

        iEngine_.getMobile().createNotification(R.drawable.logo);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getBackgroundColor());
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 30);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 220, 80, 80);
    }

}