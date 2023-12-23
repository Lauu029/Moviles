package com.example.androidgame.GameLogic;


import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Scenes.GameScene;
import com.example.androidgame.GameLogic.Scenes.Scene;
import com.example.androidgame.GameLogic.Scenes.SceneNames;

public class DifficultyScene extends Scene {
    private Font font_;
    private Sound myButtonSound_;
    private Sound myArrowSound_;

    public DifficultyScene() {
        super();
    }

    @Override
    public void init() {
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();

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
            Button but = new Button(names[i], font_,AssetsManager.getInstance().getButtonColor() ,//colors[i]
                    AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),150, 50, 35, this.width_ / 2 - 150 / 2,
                    100 * i + 100, myButtonSound_, new ButtonClickListener() {
                @Override
                public void onClick() {
                    GameInit gameInit = new GameInit(diff[finalI]);
                    GameManager.getInstance().setLevel(gameInit.getDifficulty());
                    SceneManager.getInstance().addScene(new GameScene(false), SceneNames.GAME.ordinal());
                }
            });

            this.addGameObject(but);
        }
        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        ButtonImage returnButton_ = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        });
        this.addGameObject(returnButton_);
        //graph.generateScreenshot(0,0,100,100);
        //iEngine_.getMobile().shareImage(graph.processImage(),"mi imagen");
    }

    @Override
    public void render() {
        super.render();
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().setColor(0xFF000000);
        iEngine_.getGraphics().drawText("¿En que dificultad quieres jugar?", width_ / 2, 50);
    }
}
