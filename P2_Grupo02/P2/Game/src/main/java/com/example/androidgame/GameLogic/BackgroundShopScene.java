package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Sound;

public class BackgroundShopScene extends Scene {
    private Sound myArrowSound_;
    private ButtonImage backButton;
    private Font font_;
    private ButtonImage previousShop_, nextShop_;

    BackgroundShopScene() {
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
        previousShop_ = new ButtonImage("FlechasIzq.png", 35, 35, width_/2-120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new ColorsShopScene());
            }
        });
        nextShop_ = new ButtonImage("FlechasDcha.png", 35, 35, width_/2+120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance_().changeScene(new CodesShopScene());
            }
        });
        this.addGameObject(backButton);
        this.addGameObject(previousShop_);
        this.addGameObject(nextShop_);
        font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
    }

    @Override
    public void render() {
        super.render();
        Graphics graph= iEngine_.getGraphics();
        graph.setFont(font_);
        int xText, yText;
        xText = width_/2-80 + 190 / 2;
        yText = 5 + 35 / 2;
        //graph.fillRoundRectangle(this.posX_ - 2, this.posY_ - 2, this.width_ + 4, this.height_ + 4, this.arc_);
        graph.setColor(0XFFFB839B);
        graph.fillRoundRectangle(width_/2-80, 5, 190,35, 10);
        graph.setColor(0XFF222222);
        graph.setFont(this.font_);
        graph.drawText("Fondos", xText, yText);
    }

    @Override
    public void update(double time) {
    }

}
