package com.example.androidgame.GameLogic.chronoMode;

import android.graphics.Bitmap;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.ImageProcessingCallback;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.GameInit;
import com.example.androidgame.GameLogic.LevelDifficulty;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Scenes.EndScene;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;
import com.example.androidgame.GameLogic.Theme;
import com.example.androidgame.GameLogic.Utils.RewardedAddBehaviour;

import java.util.ArrayList;

public class EndChrono extends EndScene {
    private int indexScene_;
    private double restingTime_;
    private LevelDifficulty[] levels = {LevelDifficulty.FACIL, LevelDifficulty.MEDIO, LevelDifficulty.DIFICIL, LevelDifficulty.IMPOSIBLE};

    public EndChrono(boolean win, int[] sol, double time, int nScenes, int tries) {
        super(win, sol, tries);
        restingTime_ = time;
        indexScene_ = nScenes;
    }

    @Override
    public void init() {
        //creacion de la solucion
        super.init();
    }

    @Override
    protected void initButtons() {
        Button nextGameButton_, playAgainButton_, shareRecordButton_;
        Graphics graph = iEngine_.getGraphics();
        playAgainButton_ = new Button("Volver Jugar", font1_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 - 50, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(levels[0]);
                GameManager.getInstance().setLevel(gameInit.getDifficulty());
                Engine engine_ = GameManager.getInstance().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                SceneManager.getInstance().addScene(new ChronoGameScene(0, restingTime_), SceneNames.GAME.ordinal());
            }
        });
        if (win_) {
            nextGameButton_ = new Button("Siguiente pantalla", font1_, AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                    , 150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 25,
                    myButtonSound_, new ButtonClickListener() {
                @Override
                public void onClick() {
                    GameInit gameInit = new GameInit(levels[indexScene_ + 1]);
                    GameManager.getInstance().setLevel(gameInit.getDifficulty());

                    SceneManager.getInstance().addScene(new ChronoGameScene(indexScene_ + 1, restingTime_), SceneNames.GAME.ordinal());
                }
            });
        addGameObject(nextGameButton_);
        }
        addGameObject(playAgainButton_);
        tematica_ = AssetsManager.getInstance().getCirleTheme(false);
        if (!win_) {
            if (tematica_.getName() != "DEFAULT") {
                for (int i = 0; i < sol_.length; i++) {

                    Image im = graph.newImage(tematica_.getPathBolas() + ("" + (sol_[i] + 1)) + ".png");
                    images_.add(im);
                }
            }
        }
    }

    @Override
    public void render() {
        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        if (AssetsManager.getInstance().getBackgroundImage(false, false) != null)
            iEngine_.getGraphics().drawImage(AssetsManager.getInstance().getBackgroundImage(false, false),
                    0, 0, height_, width_);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
        iEngine_.getGraphics().setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getTextColor());
        if (!win_) {
            iEngine_.getGraphics().drawText("GAME OVER", width_ / 2, 10);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText(endText_, width_ / 2, 50);
        } else {
            iEngine_.getGraphics().drawText("ENHORABUENA!!", width_ / 2, 10);
            iEngine_.getGraphics().setFont(font1_);
            iEngine_.getGraphics().drawText("Has averiguado el codigo en", width_ / 2, 50);
            iEngine_.getGraphics().setFont(font2_);
            iEngine_.getGraphics().drawText(tries_ + " intentos:", width_ / 2, 80);
        }
    }
}
