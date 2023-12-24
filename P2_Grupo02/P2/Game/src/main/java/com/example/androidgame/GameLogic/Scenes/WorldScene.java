package com.example.androidgame.GameLogic.Scenes;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Buttons.ButtonImage;
import com.example.androidgame.GameLogic.Buttons.ButtonMundo;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.GameObject;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Theme;

import java.util.ArrayList;

public class WorldScene extends Scene {
    private Font font_;
    private int niveles_ = 0;
    private int numberLevel_ = 0;
    int columnas_ = 3;
    private Sound myButtonSound_;
    private Image backaground_;
    private int numWorlds_ = 0;
    int actualWorld_ = 0;
    int passedLevel_ = 0;
    int passedWorld_ = 0;
    protected ArrayList<GameObject> buttonObjects_ = new ArrayList<>();

    @Override
    public void init() {
        buttonObjects_.clear();
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        Sound myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        ButtonImage returnButton_ = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        });
        this.addGameObject(returnButton_);

        Button previous_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 15, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                actualWorld_ = (actualWorld_ - 1 + numWorlds_) % numWorlds_;
                buttonObjects_.clear();
                loadWorld();
            }
        });
        this.addGameObject(previous_);
        Button next_ = new ButtonImage("FlechasDcha.png", 35, 35, width_ / 2 + 100, 15, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                actualWorld_ = (actualWorld_ + 1 + numWorlds_) % numWorlds_;
                Log.d("MAIN", String.valueOf(actualWorld_));
                buttonObjects_.clear();
                loadWorld();
            }
        });
        this.addGameObject(next_);
        numWorlds_ = LevelManager.getInstance().getNumWorlds();
        loadWorld();
    }

    public void loadWorld() {
        LevelManager.getInstance().setActualWorld(actualWorld_);
        LevelManager.getInstance().setNewWorld();
        int widthScene = GameManager.getInstance().getWidth();

        int wButton = (widthScene) / (columnas_ + 1);
        int margen = (widthScene - (wButton * columnas_)) / (columnas_ + 1);
        int x = 0;
        int y = 0;
        myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");

        Theme tema = LevelManager.getInstance().getTema();
        ArrayList<Difficulty> diff = LevelManager.getInstance().getDiff();
        niveles_ = LevelManager.getInstance().getNiveles();
        AssetsManager.getInstance().setWorldTheme(tema);
        String imagePath = AssetsManager.getInstance().getBackgrounTheme(true).getBackground();
        backaground_ = iEngine_.getGraphics().newImage(imagePath);
        passedWorld_ = LevelManager.getInstance().getPassedWorld();
        passedLevel_ = LevelManager.getInstance().getPassedLevel();
        for (int i = 0; i < niveles_; i++) {
            if (x >= columnas_) {
                x = 0;
                y++;
            }
            int finalI = i;
            boolean blocked = true;
            if (actualWorld_ < passedWorld_) blocked = false;
            else if (actualWorld_ == passedWorld_ && i <= passedLevel_) blocked = false;
            boolean finalBlocked = blocked;
            buttonObjects_.add(new ButtonMundo("" + i, font_, AssetsManager.getInstance().getButtonColor(),
                    AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                    wButton, wButton, 25, (x * (wButton + margen)) + margen, y * (wButton + margen) + 100,
                    myButtonSound_, blocked, new ButtonClickListener() {
                @Override
                public void onClick() {
                    if (!finalBlocked) {
                        LevelManager.getInstance().setActualLevel(finalI);
                        GameManager.getInstance().setLevel(diff.get(finalI));
                        SceneManager.getInstance().addScene(new GameScene(true), SceneNames.GAME.ordinal());
                    }
                }
            }));
            x++;
        }
    }

    public void render() {

        iEngine_.getGraphics().clear(AssetsManager.getInstance().getBackgroundColor());
        iEngine_.getGraphics().drawImage(backaground_, 0, 0, GameManager.getInstance().getHeight(),
                GameManager.getInstance().getWidth());
        this.iEngine_.getGraphics().setFont(font_);
        this.iEngine_.getGraphics().setColor(0xFF000000);
        iEngine_.getGraphics().drawText("Mundo " + actualWorld_, width_ / 2, 30);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(iEngine_.getGraphics());
        }
        for (int i = 0; i < buttonObjects_.size(); i++) {
            buttonObjects_.get(i).render(iEngine_.getGraphics());
        }
    }

    @Override
    public void update(double time) {
        super.update(time);
        for (int i = 0; i < buttonObjects_.size(); i++) {
            buttonObjects_.get(i).update(time);
        }
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        super.handleInput(events);
        for (GameObject g : buttonObjects_)
            for (TouchEvent event : events) {
                if (g.handleInput(event))
                    return;
            }
    }
}
