package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import java.util.ArrayList;

public class WorldScene extends Scene {
    private Font font_;
    private int niveles_=0;
    private int numberLevel_=0;
    int columnas_=3;
    private Sound myButtonSound_;
    ArrayList<ButtonMundo>botones_;
    private Image backaground_;
    @Override
    public void init() {
        numberLevel_=LevelManager.getInstance().actualWorld_;
        Theme tema=LevelManager.getInstance().tema_;
        ArrayList<Difficulty>diff=LevelManager.getInstance().diff_;
        niveles_=LevelManager.getInstance().niveles_;
        Graphics graph=iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);
        int widthScene=GameManager.getInstance().getwidth();
        int heightScene=GameManager.getInstance().getHeight_();

        int wButton=(widthScene)/(columnas_+1);
        int margen=(widthScene-(wButton*columnas_))/(columnas_+1);
        int x=0;
        int y=0;
         myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");
         AssetsManager.getInstance().setWorldThem(tema);
         String imagePath=AssetsManager.getInstance().getBackgrounTheme(true).getBackground();
         backaground_=iEngine_.getGraphics().newImage(imagePath);
        for(int i=0;i<niveles_;i++){
            if(x>=columnas_){x=0;y++;}
            int finalI = i;
            gameObjects_.add(new ButtonMundo(""+i,font_,AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                    wButton,wButton,25,(x*(wButton+margen))+margen,y*(wButton+margen)+100,
                    myButtonSound_,false,new ButtonClickListener() {
                @Override
                public void onClick() {
                    LevelManager.getInstance().actualLevel_ =finalI;
                    GameManager.getInstance().setLevel(diff.get(finalI));
                    SceneManager.getInstance().addScene(new GameScene(true));
                }}
               ));
            x++;
        }
        Sound myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        ButtonImage returnButton_ = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().switchToPreviousScene();
            }
        });
        this.addGameObject(returnButton_);

        Button previous_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 15, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {


            }
        });
        this.addGameObject(previous_);
        Button next_ = new ButtonImage("FlechasDcha.png", 35, 35, width_ / 2 + 100, 15, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

            }
        });
        this.addGameObject(next_);
    }
    public void render() {

        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        iEngine_.getGraphics().drawImage(backaground_,0,0,GameManager.getInstance().getHeight_(),GameManager.getInstance().getwidth());
        this.iEngine_.getGraphics().setFont(font_);
        this.iEngine_.getGraphics().setColor(0xFF000000);
        iEngine_.getGraphics().drawText("Mundo "+numberLevel_, width_ / 2, 30);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
    }
}
