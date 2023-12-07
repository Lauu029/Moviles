package com.example.androidgame.GameLogic;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;

public class ShopScene extends Scene {
    private Sound myArrowSound_, shopingSound;
    private ButtonImage backButton;
    private Font font_;
    private String[] textShops_ = {"fondos", "codigos", "colores"};
    private String[] shopName;
    private ButtonImage previousShop_, nextShop_, poohBackground_;

    private ArrayList<ArrayList<ButtonImage>> shopItems2 = new ArrayList<>();
    private int id;
    private Image coinsIcon_;
    private JSONObject shopConfig;
    private Image blockedImage;
    private ButtonImage noneButton;
    private boolean[] itemsLoaded = {false, false, false};

    ShopScene() {
        super();
    }

    @Override
    public void init() {

        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        shopingSound = iEngine_.getAudio().newSound("cashPurchase.wav");
        blockedImage = iEngine_.getGraphics().newImage("lock.png");
        noneButton = new ButtonImage("Shop/NudeButton.png", 100, 100, 25, 130,
                myArrowSound_, null);
        gameObjects_.add(noneButton);
        InputStream file = iEngine_.getFileManager().getInputStream("Shop/Shops.json");
        shopConfig = readShopConfig(file);
        id = 0;
        shopName = new String[textShops_.length];
        getShopTypeData(textShops_[id]);
        itemsLoaded[id] = true;
        backButton = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().switchToPreviousScene();
            }
        });
        previousShop_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {

                for (int i = 0; i < shopItems2.get(id).size(); i++) {
                    shopItems2.get(id).get(i).changeActive(false);
                }
                if (id == 0) id = 2;
                else
                    id = (id - 1) % 3;
                if (!itemsLoaded[id]) {
                    getShopTypeData(textShops_[id]);
                    itemsLoaded[id] = true;
                } else {
                    Log.d("MainActivity", "Ya se cargaron los recursos anteriormente de " + textShops_[id]);
                    for (int i = 0; i < shopItems2.get(id).size(); i++) {
                        shopItems2.get(id).get(i).changeActive(true);
                    }
                }
                Log.d("MainActivity", String.valueOf(id));
            }
        });
        nextShop_ = new ButtonImage("FlechasDcha.png", 35, 35, width_ / 2 + 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                for (int i = 0; i < shopItems2.get(id).size(); i++) {
                    shopItems2.get(id).get(i).changeActive(false);
                }
                id = (id + 1) % 3;
                if (!itemsLoaded[id]) {
                    getShopTypeData(textShops_[id]);
                    itemsLoaded[id] = true;
                } else {
                    Log.d("MainActivity", "Ya se cargaron los recursos anteriormente de " + textShops_[id]);
                    for (int i = 0; i < shopItems2.get(id).size(); i++) {
                        shopItems2.get(id).get(i).changeActive(true);
                    }
                }
            }
        });

        this.addGameObject(backButton);
        this.addGameObject(previousShop_);
        this.addGameObject(nextShop_);
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
        graph.drawText(shopName[id], xText, yText);
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
                shopName[id] = config.get("tipo").toString();
                String path = config.get("ruta").toString();
                String ext = config.get("extension").toString();
                JSONArray buttonsArray = config.getJSONArray("ButtonsImages");
                int yPos = 130;
                int xPos = 25 + 130;
                int step = 1;
                ArrayList<ButtonImage> shopItems_ = new ArrayList<>();

                Log.d("MainActivity", "TAmaño array: " + shopItems_.size());
                for (int i = 0; i < buttonsArray.length(); i++) {
                    String nombre = buttonsArray.getString(i);

                    ButtonImage img = new ButtonImage(path + nombre + "Button" + ext, 100, 100, xPos,
                            yPos, shopingSound, new ButtonClickListener() {
                        @Override
                        public void onClick() {
                            Theme theme = new Theme("PURCHASED", "", "", "");
                            switch (shopName[id]) {
                                case "Fondos":
                                    theme.setBackground(path + nombre + ext);
                                    AssetsManager.getInstance().setBackgroundTheme(theme);
                                    break;
                                case "Codigos":
                                    theme.setPathBolas(path + nombre+"/");
                                    AssetsManager.getInstance().setCirleTheme(theme, false);
                                    break;
                                case "Colores":
                                    break;
                            }
                        }
                    });
                    img.addOverlayImage(blockedImage);
                    xPos += 130;
                    shopItems_.add(img);
                    addGameObject(img);
                    step = (step + 1) % 3;
                    if (step == 0) {
                        yPos = yPos * 2 + 20;
                        xPos=25;
                    }
                    Log.d("MainActivity", "Button " + nombre + " loaded");
                }
                shopItems2.add(shopItems_);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error al procesar la configuración de la tienda");
            }
        }
    }
}
