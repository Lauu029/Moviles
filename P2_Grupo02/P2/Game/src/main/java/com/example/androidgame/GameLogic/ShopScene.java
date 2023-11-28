package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;

public class ShopScene implements IScene
{
    private Engine iEngine_;
    private int width_, height_;
    private ArrayList<GameObject> gameObjects_ = new ArrayList<>();
    private Sound myArrowSound_;
    private ButtonImage backButton;
    private Font font_;
    ShopScene(){
        iEngine_=GameManager.getInstance_().getIEngine();
        width_=GameManager.getInstance_().getwidth();
        height_=GameManager.getInstance_().getHeight_();
    }
    @Override
    public void init() {
        Graphics graph = iEngine_.getGraphics();
        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        backButton = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new MenuScene());
            }
        });
        this.addGameObject(backButton);
        font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
    }
    @Override
    public void render() {
        Graphics graph = iEngine_.getGraphics();

        graph.clear(0xFFfff0f6);
        for (int i = 0; i < gameObjects_.size(); i++) {
            gameObjects_.get(i).render(graph);
        }
        this.iEngine_.getGraphics().setFont(font_);
        graph.setColor(0xFF000000);
        graph.drawText("Tienda compra cosas gasta dinero", width_ / 2, 50);
    }

    @Override
    public void update(double time) {

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
        for (GameObject g : gameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    public void addGameObject(GameObject gm) {
        gameObjects_.add(gm);
    }
}
