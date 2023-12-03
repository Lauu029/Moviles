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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;

public class ShopScene extends Scene {
    private Sound myArrowSound_;
    private ButtonImage backButton;
    private Font font_;
    private String[] textShops_ = {"fondos", "codigos", "colores"};
    private String shopName;
    private ButtonImage previousShop_, nextShop_, poohBackground_;
    private int id;
    private Image coinsIcon_;
    private JSONObject shopConfig;

    ShopScene() {
        super();
    }

    @Override
    public void init() {

        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        InputStream file = iEngine_.getFileManager().getInputStream("Shop/Shops.json");
        shopConfig = readShopConfig(file);
        id = 0;
        shopName="";
        getShopTypeData(textShops_[id]);

        backButton = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().switchToPreviousScene();
            }
        });
        previousShop_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                iEngine_.getMainActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (id == 0) id = 2;
                        else
                            id = (id - 1) % 3;
                        getShopTypeData(textShops_[id]);
                        Log.d("MainActivity", String.valueOf(id));
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
                        id = (id + 1) % 3;
                        getShopTypeData(textShops_[id]);
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
        graph.drawText(shopName, xText, yText);
        graph.drawImage(coinsIcon_, width_ - 60, 50, 40, 40);
        graph.drawText(String.valueOf(GameManager.getInstance().getCoins()), width_ - 40, 100);

    }

    @Override
    public void update(double time) {
    }

    public JSONObject readShopConfig(InputStream file) {
        try {
            // Lee el contenido del InputStream proporcionado
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
            }
            reader.close();

            // Convierte la cadena JSON en un objeto JSONObject
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("ShopConfigReader", "Error al leer el archivo JSON desde InputStream");
            return null;
        }
    }

    private void getShopTypeData(String type) {
        if (shopConfig != null) {
            try {
                JSONObject config = shopConfig.getJSONObject(type);
                shopName = config.get("tipo").toString();;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error al procesar la configuración de la tienda");
            }
        }
    }
}
