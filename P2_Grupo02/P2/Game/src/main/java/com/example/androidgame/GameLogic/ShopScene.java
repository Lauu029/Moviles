package com.example.androidgame.GameLogic;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.R;

import java.util.ArrayList;

public class ShopScene extends Scene {
    private Sound myArrowSound_;
    private ButtonImage backButton;
    private Font font_;
    private ArrayList<String> textShops_= new ArrayList<>();
    private ButtonImage previousShop_, nextShop_, poohBackground_;
    private int id;
    private Image coinsIcon_;
    private ArrayList<SurfaceView> surfaceShops_ = new ArrayList<>();
    private Context context_;

    ShopScene() {
        super();
    }

    @Override
    public void init() {
        context_ = iEngine_.getMainActivity().getBaseContext();
        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        surfaceShops_.add(iEngine_.getMainActivity().findViewById(R.id.backgroundShop));
        surfaceShops_.add(iEngine_.getMainActivity().findViewById(R.id.colorShop));
        surfaceShops_.add(iEngine_.getMainActivity().findViewById(R.id.codeShop));
        textShops_.add("Fondos");
        textShops_.add("Colores");
        textShops_.add("Codigos");
        id = 0;
        iEngine_.getMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                surfaceShops_.get(id).setVisibility(View.VISIBLE);

                //iEngine_.getMainActivity().setContentView(backgroundShop_);
            }
        });

        backButton = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                iEngine_.getMainActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        surfaceShops_.get(id).setVisibility(View.INVISIBLE);
                    }
                });
                SceneManager.getInstance().switchToPreviousScene();

            }
        });
        previousShop_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                iEngine_.getMainActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        surfaceShops_.get(id).setVisibility(View.INVISIBLE);
                        if(id==0)id=2;
                        else
                            id = (id - 1)%3;
                        surfaceShops_.get(id).setVisibility(View.VISIBLE);
                        Log.d("MainActivity",String.valueOf(id));
                    }
                });
            }
        });
        nextShop_ = new ButtonImage("FlechasDcha.png", 35, 35, width_ / 2 + 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                iEngine_.getMainActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        surfaceShops_.get(id).setVisibility(View.INVISIBLE);
                        id = (id+ 1) % 3;
                        surfaceShops_.get(id).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        poohBackground_ = new ButtonImage("poohBoton.png", 100, 100, width_ / 2, height_ / 2, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                if (GameManager.getInstance().getCoins() >= 20) {
                    GameManager.getInstance().setBackgroundImage(iEngine_.getGraphics().newImage("pooh.png"));
                    GameManager.getInstance().addCoins(-20);
                    poohBackground_.deleteOverlayImage();
                }
            }
        });
        poohBackground_.addOverlayImage("lock.png");
        this.addGameObject(backButton);
        this.addGameObject(previousShop_);
        this.addGameObject(nextShop_);
        this.addGameObject(poohBackground_);
        coinsIcon_ = iEngine_.getGraphics().newImage("coin.png");
        font_ = iEngine_.getGraphics().newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
    }

    @Override
    public void render() {
        super.render();
        Graphics graph = iEngine_.getGraphics();
        graph.setFont(font_);
        int xText, yText;
        xText = width_ / 2 - 80 + 190 / 2;
        yText = 5 + 35 / 2;
        //graph.fillRoundRectangle(this.posX_ - 2, this.posY_ - 2, this.width_ + 4, this.height_ + 4, this.arc_);
        graph.setColor(0XFFFB839B);
        graph.fillRoundRectangle(width_ / 2 - 80, 5, 190, 35, 10);
        graph.setColor(0XFF222222);
        graph.setFont(this.font_);
        graph.drawText(textShops_.get(id), xText, yText);
        graph.drawImage(coinsIcon_, width_ - 60, 50, 40, 40);
        graph.drawText(String.valueOf(GameManager.getInstance().getCoins()), width_ - 40, 100);

    }

    @Override
    public void update(double time) {
    }

}
