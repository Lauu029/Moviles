package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class EndScene implements IScene {

    private IEngine iEngine_;
    private int[] sol_;
    private ArrayList<IGameObject> iGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button button2_;
    private Button buttonDificulty_,  playAgainButton_;
    private IFont font_, font1_, font2_;
    private int tries_;
    private ISound myButtonSound_;
    private int[] totalPossibleColors_ = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};
    private boolean win_ = false;

    public EndScene(IEngine IEngine, int w, int h, boolean win, int[] sol, int intentos) {
        iEngine_ = IEngine;
        width_ = w;
        height_ = h;
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
        this.win_ = win;
        this.sol_ = sol;
        tries_ = intentos;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine_.getGraphics();
        if (win_)
            this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        else this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, false, true);
        graph.setFont(this.font_);
        font1_ =graph.newFont("Hexenkoetel-qZRv1.ttf",20,false,false);
        font2_ =graph.newFont("Hexenkoetel-qZRv1.ttf",30,false,false);
        myButtonSound_ = iEngine_.getAudio().newSound("buttonClicked.wav");
        this.playAgainButton_ = new Button("Volver Jugar", font1_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 20,
                /* SceneNames.GAME, GameManager.getInstance_().getLevel().getLevelDiff_(),*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                //GameScene gameScene = (GameScene) scene_;
                GameInit gameInit = new GameInit(GameManager.getInstance_().getLevel().getLevelDiff_());
                GameManager.getInstance_().setLevel(gameInit.getDifficulty());
                IEngine engine_ = GameManager.getInstance_().getIEngine();
                engine_.getAudio().playSound(myButtonSound_, 0);
                GameManager.getInstance_().changeScene(new GameScene(engine_, width_, height_));
            }
        });

        this.buttonDificulty_ = new Button("Elegir Dificultad", font1_, 0XFFFB839B
                , 150, 50, 35, this.width_ / 2 - (150 / 2), this.height_ / 2 + 90,
                /*SceneNames.LEVEL,*/ myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameInit gameInit = new GameInit(GameManager.getInstance_().getLevel().getLevelDiff_());
                GameManager.getInstance_().setLevel(gameInit.getDifficulty());
                GameManager.getInstance_().changeScene(new LevelScene(iEngine_, width_, height_));
                //scene_ = new LevelScene(engine_, sceneWidth, sceneHeight);
            }
        });
        addGameObject(playAgainButton_);
        addGameObject(buttonDificulty_);
    }


    public void addGameObject(IGameObject gm) {
        iGameObjects_.add(gm);
    }

    @Override
    public int getHeight_() {
        return height_;
    }

    @Override
    public int getWidth_() {
        return width_;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (IGameObject g : iGameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    @Override
    public void render() {
        IGraphics graph = iEngine_.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);

        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.iEngine_.getGraphics().setFont(font_);
        if (!win_) {
            graph.drawText("GAME OVER", width_ / 2, 50);
            graph.setFont(font1_);
            graph.drawText("Te has quedado sin intentos", width_ / 2, 120);
            graph.drawText("codigo:", width_ / 2, height_ / 2 - 70);
            drawCircles(graph);

        } else {
            graph.drawText("ENHORABUENA!!", width_ / 2, 50);
            graph.setFont(font1_);
            graph.drawText("Has averiguado el codigo en", width_ / 2, 120);
            graph.setFont(font2_);
            graph.drawText(tries_ + " intentos:", width_ / 2, height_ / 2 - 110);
            graph.setFont(font1_);
            graph.drawText("codigo:", width_ / 2, height_ / 2 - 70);
            drawCircles(graph);
        }

    }

    void drawCircles(IGraphics graph) {
        int offset = 20;
        int radius = 20;
        int totalCircleWidth = sol_.length * (radius * 2 + offset); // Ancho total de todos los círculos
        int x = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < sol_.length; i++) {
            graph.setColor(totalPossibleColors_[sol_[i]]);
            graph.drawCircle(x + i * (radius * 2 + offset) + radius, height_ / 2 + radius - 50, radius);
            if (GameManager.getInstance_().getDaltonic()) {
                graph.setColor(0xFF000000);
                graph.setFont(this.font1_);
                graph.drawText(sol_[i] + "", x + i * (radius * 2 + offset) + radius, height_ / 2 + (radius) - 53);
            }
        }
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < iGameObjects_.size(); i++) {
            iGameObjects_.get(i).update(time);
        }
    }
}
