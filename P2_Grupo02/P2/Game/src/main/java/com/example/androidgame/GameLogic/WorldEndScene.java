package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;

public class WorldEndScene extends EndScene{
    public WorldEndScene(boolean win, int[] sol, int intentos) {
        super(win, sol, intentos);
    }
    @Override
    protected void initButtons(){
        if(win_)WinButtons();
        else GameOver();


    }
    void GameOver(){


        Graphics graph = iEngine_.getGraphics();

        Button moreTryesButton_ = new Button("+2 intentos", font2_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                180, 60, 15, this.width_ / 2 - (180 / 2), this.height_ / 2 -100,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                iEngine_.getMobile().LoadRewardedAd();
            }
        });
        addGameObject(moreTryesButton_);

        Button retryButton = new Button("Volver a intentar", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 +20,
                /* SceneNames.GAME, GameManager.getInstance_().getLevel().getLevelDiff_(),*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                Engine engine_ = GameManager.getInstance().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                SceneManager.getInstance().addScene(new GameScene(false));
            }
        });
        addGameObject(retryButton);

        Button menuButton = new Button("Menu", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - (150 / 2), this.height_ / 2+100 ,
                /*SceneNames.LEVEL,*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new MenuScene());

            }
        });
        addGameObject(menuButton);


    }
    void WinButtons(){


        Graphics graph = iEngine_.getGraphics();

        Button shareRecordButton_ = new Button("Compartir", font2_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                180, 60, 15, this.width_ / 2 - (180 / 2), this.height_ / 2 -80,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                graph.generateScreenshot(0, 0, width_, height_ / 2 - 40, callback);
            }
        });
        addGameObject(shareRecordButton_);

        Button nextLevelButton = new Button("Siguiente Nivel", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 +20,
                /* SceneNames.GAME, GameManager.getInstance_().getLevel().getLevelDiff_(),*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                GameInit gameInit = new GameInit(GameManager.getInstance().getLevel().getLevelDiff_());
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                Engine engine_ = GameManager.getInstance().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                SceneManager.getInstance().addScene(new GameScene(false));
            }
        });
        addGameObject(nextLevelButton);

        Button menuButton = new Button("Menu", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - (150 / 2), this.height_ / 2+100 ,
                /*SceneNames.LEVEL,*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().addScene(new MenuScene());

            }
        });
        addGameObject(menuButton);
        //carga de imagenes
        tematica_=AssetsManager.getInstance().getCirleTheme(true);
        if(tematica_.getName()!= "DEFAULT"){
            for(int i=0;i<sol_.length;i++){
                Log.d("OREO", tematica_.getPath()+(""+(i+1))+".png");
                Image im=graph.newImage(tematica_.getPath()+(""+(sol_[i]+1))+".png");
                images_.add(im);

            }
        }

    }
}
