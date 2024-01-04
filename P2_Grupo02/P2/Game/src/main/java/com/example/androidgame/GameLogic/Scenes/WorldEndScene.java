package com.example.androidgame.GameLogic.Scenes;

import android.util.Log;

import com.example.androidengine.Engine;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Utils.RewardedAddBehaviour;

import java.util.ArrayList;

public class WorldEndScene extends EndScene {
    private Image coinImage_;
    public WorldEndScene(boolean win, int[] sol, int intentos) {
        super(win, sol, intentos);
    }

    @Override
    protected void initButtons() {
        if (win_) {
            GameManager.getInstance().addCoins(10);
            WinButtons();}
        else GameOver();
        iEngine_.getMobile().assignRewardPrice(new RewardedAddBehaviour());
    }

    void GameOver() {
        Graphics graph = iEngine_.getGraphics();
        ArrayList<ArrayList<Integer>> tries= (ArrayList<ArrayList<Integer>>) LevelManager.getInstance().getTries().clone();
        LevelManager.getInstance().clearTries();
        Button moreTriesButton_ = new Button("+2 intentos", font2_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                180, 60, 15, this.width_ / 2 - (180 / 2), this.height_ / 2 - 100,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameScene gs = (GameScene) SceneManager.getInstance().getScene(SceneNames.GAME.ordinal());
                LevelManager.getInstance().setTries(tries);
                gs.addTriesToBoard(2);

                iEngine_.getMobile().LoadRewardedAd();
                waitingForReward_=true;
            }
        });
        addGameObject(moreTriesButton_);

        Button retryButton = new Button("Volver a intentar", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                GameManager.getInstance().setLevel(LevelManager.getInstance().getDiff()
                        .get(LevelManager.getInstance().getActualLevel()));
                SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
            }
        });
        addGameObject(retryButton);
        tematica_ = AssetsManager.getInstance().getCirleTheme(true);
        if (tematica_.getName() != "DEFAULT") {
            for (int i = 0; i < sol_.length; i++) {
                Image im = graph.newImage(tematica_.getPathBolas() + ("" + (sol_[i] + 1)) + ".png");
                images_.add(im);
            }
        }
        Button menuButton = new Button("Menu", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 100,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

               SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        });
        addGameObject(menuButton);
    }

    void WinButtons() {
        Graphics graph = iEngine_.getGraphics();
        coinImage_=graph.newImage("coin.png");
        Button shareRecordButton_ = new Button("Compartir", font2_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                180, 60, 15, this.width_ / 2 - (180 / 2), this.height_ / 2 ,
                myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                graph.generateScreenshot(0, 0, width_, height_ / 3, callback);
            }
        });
        addGameObject(shareRecordButton_);
        Difficulty difNextLevel = LevelManager.getInstance().getNextLevelDifficulty();

        if (LevelManager.getInstance().getActualWorld() == LevelManager.getInstance().getPassedWorld() && LevelManager.getInstance().getActualLevel() >= LevelManager.getInstance().getPassedLevel())
            LevelManager.getInstance().nextPassedLevel();
        if (difNextLevel != null) {
            Button nextLevelButton = new Button("Siguiente Nivel", font1_, AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                    , 150, 40, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 100,
                    myButtonSound_, new ButtonClickListener() {
                @Override
                public void onClick() {
                    GameManager.getInstance().setLevel(difNextLevel);
                    Engine engine_ = GameManager.getInstance().getIEngine();
                    engine_.getAudio().playSound(myButtonSound_, 0);
                    SceneManager.getInstance().addScene(new GameScene(), SceneNames.GAME.ordinal());
                }
            });
            addGameObject(nextLevelButton);
        }
        Button menuButton = new Button("Menu", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 40, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 180,
                 myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());

            }
        });
        addGameObject(menuButton);
        //carga de imagenes
        tematica_ = AssetsManager.getInstance().getCirleTheme(true);
        if (tematica_.getName() != "DEFAULT") {
            for (int i = 0; i < sol_.length; i++) {
                Image im = graph.newImage(tematica_.getPathBolas() + ("" + (sol_[i] + 1)) + ".png");
                images_.add(im);
            }
        }
    }
    public void render() {
        super.render();
        if(win_){
            Graphics graph=GameManager.getInstance().getIEngine().getGraphics();
            graph.drawImage(coinImage_,this.width_ / 2-120,this.height_ / 2-80,60,60);
            graph.setFont(font2_);
            graph.setColor(0xFF000000);
            graph.drawText("+10 - Total:"+GameManager.getInstance().getCoins(),this.width_ / 2+45,this.height_ / 2-60);
        }
    }


}
