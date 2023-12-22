package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;

public class ShopScene extends Scene {
    private Sound myArrowSound_, shopingSound_;
    private ButtonImage backButton_;
    private Font font_;
    private String[] textShops_ = {"fondos", "codigos", "colores"};
    private String[] shopName_;
    private ButtonImage previousShop_, nextShop_;
    private ArrayList<ShopItem>[] totalShopItems_;
    private ButtonClickListener[] nullButtonFunctions;
    private int id_;
    private int rectangleColor, fontColor;
    private Image coinsIcon_;
    private JSONObject shopConfig;
    private Image blockedImage_;
    private ButtonImage noneButton_;
    private boolean[] itemsLoaded_ = {false, false, false};

    ShopScene() {
        super();
    }

    @Override
    public void init() {
        Log.d("MainActivity", "Me leo la tienda");
        myArrowSound_ = iEngine_.getAudio().newSound("arrowButton.wav");
        shopingSound_ = iEngine_.getAudio().newSound("cashPurchase.wav");
        blockedImage_ = iEngine_.getGraphics().newImage("lock.png");
        noneButton_ = new ButtonImage("Shop/NudeButton.png", 100, 100, 25, 130,
                myArrowSound_, null);
        rectangleColor = AssetsManager.getInstance().getButtonColor();
        fontColor = AssetsManager.getInstance().getTextColor();
        gameObjects_.add(noneButton_);
        totalShopItems_ = (ArrayList<ShopItem>[]) new ArrayList[3];
        InputStream file = iEngine_.getFileManager().getInputStream("Shop/Shops.json");
        shopConfig = readShopConfig(file);
        initializeNudeButtonFunctions();
        id_ = 0;
        noneButton_.setAction(nullButtonFunctions[id_]);
        shopName_ = new String[textShops_.length];
        getShopTypeData(textShops_[id_]);
        itemsLoaded_[id_] = true;

        backButton_ = new ButtonImage("flecha.png", 40, 40, 0, 0, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().setScene(SceneNames.MENU.ordinal());
            }
        });
        previousShop_ = new ButtonImage("FlechasIzq.png", 35, 35, width_ / 2 - 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                Log.d("MainActivity", "id: " + id_ + " size");
                for (int i = 0; i < totalShopItems_[id_].size(); i++) {
                    Log.d("MainActivity", "id= " + id_ + " pointer: " + i);
                    totalShopItems_[id_].get(i).changeActive(false);
                }
                if (id_ == 0) id_ = 2;
                else
                    id_ = (id_ - 1) % 3;
                noneButton_.setAction(nullButtonFunctions[id_]);
                if (!itemsLoaded_[id_]) {
                    getShopTypeData(textShops_[id_]);
                    itemsLoaded_[id_] = true;
                } else {
                    for (int i = 0; i < totalShopItems_[id_].size(); i++) {
                        totalShopItems_[id_].get(i).changeActive(true);
                    }
                }
                Log.d("MainActivity", String.valueOf(id_));
            }
        });
        //previousShop_.changeActive(false);
        nextShop_ = new ButtonImage("FlechasDcha.png", 35, 35, width_ / 2 + 120, 5, myArrowSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                for (int i = 0; i < totalShopItems_[id_].size(); i++) {
                    totalShopItems_[id_].get(i).changeActive(false);
                }
                id_ = (id_ + 1) % 3;
                noneButton_.setAction(nullButtonFunctions[id_]);
                if (!itemsLoaded_[id_]) {
                    getShopTypeData(textShops_[id_]);
                    itemsLoaded_[id_] = true;
                } else {
                    for (int i = 0; i < totalShopItems_[id_].size(); i++) {
                        totalShopItems_[id_].get(i).changeActive(true);
                    }
                }
            }
        });

        this.addGameObject(backButton_);
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
        graph.setColor(rectangleColor);
        graph.fillRoundRectangle(width_ / 2 - 80, 5, 190, 35, 10);
        graph.setColor(fontColor);
        graph.setFont(this.font_);
        graph.drawText(shopName_[id_], xText, yText);
        graph.drawImage(coinsIcon_, width_ - 60, 50, 40, 40);
        graph.drawText(String.valueOf(GameManager.getInstance().getCoins()), width_ - 40, 100);
    }

    @Override
    public void update(double time) {
        if (this.rectangleColor != AssetsManager.getInstance().getButtonColor())
            this.rectangleColor = AssetsManager.getInstance().getButtonColor();
        if (this.fontColor != AssetsManager.getInstance().getTextColor())
            this.fontColor = AssetsManager.getInstance().getTextColor();
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
                shopName_[id_] = config.get("tipo").toString();
                String path = config.get("ruta").toString();
                String ext = config.get("extension").toString();
                JSONArray buttonsArray = config.getJSONArray("ButtonsImages");
                int yPos = 130;
                int xPos = 25 + 130;
                int step = 1;
                ArrayList<ShopItem> shopItems_ = new ArrayList<>();

                Log.d("MainActivity", "Tamaño array: " + shopItems_.size());

                for (int i = 0; i < buttonsArray.length(); i++) {
                    String nombre = buttonsArray.getString(i);
                    ShopItem item  = new ShopItem(path + nombre + "Button" + ext, 100, 100, xPos,
                            yPos, shopingSound_, shopName_[id_],nombre, 1,blockedImage_);
                    //Registramos ese item
                    ShopManager.getInstance().registerShopItem(shopName_[id_],nombre);

                    item.setAction(new ButtonClickListener() {
                        @Override
                        public void onClick() {
                            //Si no esta comprado ya y tiene dinero para comprarlo o ya esta comprado
                            if (item.buyItem() || !ShopManager.getInstance().itemIsLocked(shopName_[id_],nombre)) {
                                Theme theme = new Theme("PURCHASED", "", "", "");
                                switch (shopName_[id_]) {
                                    case "Fondos":
                                        theme.setBackground(path + nombre + ext);
                                        AssetsManager.getInstance().setBackgroundTheme(theme);
                                        break;
                                    case "Codigos":
                                        theme.setPathBolas(path + nombre + "/");
                                        AssetsManager.getInstance().setCirleTheme(theme, false);
                                        break;
                                    case "Colores":
                                        AssetsManager.getInstance().addNewPalette(nombre);
                                        break;
                                }

                            }
                        }
                    });
                    xPos += 130;
                    shopItems_.add(item);
                    addGameObject(item);
                    step = (step + 1) % 3;
                    if (step == 0) {
                        yPos = yPos * 2 + 20;
                        xPos = 25;
                    }
                    Log.d("MainActivity", "Button " + nombre + " loaded");
                }
                totalShopItems_[id_] = shopItems_;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error al procesar la configuración de la tienda");
            }
        }
    }

    private void initializeNudeButtonFunctions() {
        nullButtonFunctions = new ButtonClickListener[3];
        nullButtonFunctions[0] = new ButtonClickListener() {
            @Override
            public void onClick() {
                AssetsManager.getInstance().setDefaultBackground();
            }
        };
        nullButtonFunctions[1] = new ButtonClickListener() {
            @Override
            public void onClick() {
                AssetsManager.getInstance().setDefaultCodes();
            }
        };
        nullButtonFunctions[2] = new ButtonClickListener() {
            @Override
            public void onClick() {
                AssetsManager.getInstance().setDefaultPalette();
            }
        };
    }
}
