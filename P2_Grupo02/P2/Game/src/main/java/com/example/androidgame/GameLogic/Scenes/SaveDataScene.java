package com.example.androidgame.GameLogic.Scenes;

import com.example.androidengine.Font;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Buttons.Button;
import com.example.androidgame.GameLogic.Buttons.ButtonClickListener;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;
import com.example.androidgame.GameLogic.Utils.SaveData;

public class SaveDataScene extends Scene {
    private Font font_;
    private Button save, dontSave;
    private Sound myButtonSound_;
    private int previousScene;

    public SaveDataScene(boolean world) {
        super();
        if (world)
            previousScene = SceneNames.WORLD.ordinal();
        else previousScene = SceneNames.DIFFICULTY.ordinal();
    }

    @Override
    public void init() {
        this.font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");
        this.save = new Button("Si", font_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(),
                70, 50, 35, this.width_ / 2 - 100 , this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance().saveGameData();
                SceneManager.getInstance().setScene(previousScene);
            }
        });
        addGameObject(save);
        this.dontSave = new Button("No", font_, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor()
                , 70, 50, 35, this.width_ / 2 + 50 , this.height_ / 2, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(previousScene);
            }
        });
        addGameObject(dontSave);
    }

    @Override
    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getLineColor());
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("¿Guardar progreso?", width_ / 2, height_/2-50);
    }
}
