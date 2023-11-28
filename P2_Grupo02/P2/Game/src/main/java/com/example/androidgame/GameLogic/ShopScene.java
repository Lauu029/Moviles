package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;

public class ShopScene extends Scene
{
    private Sound myArrowSound_;
    private ButtonImage backButton;
    private Font font_;
    ShopScene(){
        super();
    }
    @Override
    public void init() {
        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        backButton = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new MenuScene());
            }
        });
        this.addGameObject(backButton);
        font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
    }
    @Override
    public void render() {
        super.render();
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().setColor(0xFF000000);
        iEngine_.getGraphics().drawText("Tienda compra cosas gasta dinero", width_ / 2, 50);
    }

    @Override
    public void update(double time) {
    }

}
