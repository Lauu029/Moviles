package com.example.androidgame.GameLogic;


import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MenuScene extends Scene {
    private Button playButton_;
    private Button storeButton_;
    private Button mundoButton_;
    private Font font_;
    private Font fontButton_;
    private Image myIcon_;
    private Sound myButtonSound_;

    public MenuScene() {
        super();
    }

    public void init() {

        ObjectMapper objectMapper = new ObjectMapper();

        // Lee el archivo JSON y conviértelo en un objeto JsonNode
        JsonNode jsonNode = null;
        Log.d("MainActivity","Leyendo json");
        InputStream readFile;
        readFile=iEngine_.getFileManager().getInputStream("Levels/world1/level_1_01.json");
        try {
            jsonNode = objectMapper.readTree(readFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Asigna valores a las variables de tu programa desde el JSON

        int variable2 = jsonNode.get("codeSize").asInt();
        Log.d("MainActivity",String.valueOf(variable2));
        try {
            readFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //creacion de la solucion
        Graphics graph = iEngine_.getGraphics();
        this.font_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font_);

        fontButton_ = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound_ = iEngine_.getAudio().newSound("menuButton.wav");

        this.playButton_ = new Button("Partida Rapida", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 -80, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance().changeScene(new LevelScene());
            }
        });
        this.mundoButton_ = new Button("Explorar Mundos", fontButton_,AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 , myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance().changeScene(new NivelScene());
            }
        });
        this.storeButton_ = new Button("Personalizar", fontButton_, 0XFFbf5061,
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor()
                , 150, 50, 35, this.width_ / 2 - 150 / 2, this.height_ / 2 + 120, myButtonSound_, new ButtonClickListener() {
            @Override
            public void onClick() {
                GameManager.getInstance().changeScene(new ShopScene());
            }
        });

        addGameObject(playButton_);
        addGameObject(storeButton_);
        addGameObject(mundoButton_);
        myIcon_ = graph.newImage("logo.png");
//        WorkRequest workRequest = new OneTimeWorkRequest.Builder(TimedWorker.class)
//                .setInitialDelay(20, TimeUnit.SECONDS)
//                .setInputData(inputData) // Corregido el nombre del método
//                .build();
        iEngine_.getMobile().createNotification(R.drawable.logo);
    }

    public void render() {
        super.render();
        iEngine_.getGraphics().setColor(AssetsManager.getInstance().getBackgroundColor());
        this.iEngine_.getGraphics().setFont(font_);
        iEngine_.getGraphics().drawText("MasterMind", width_ / 2, 30);
        iEngine_.getGraphics().drawImage(myIcon_, this.width_ / 2 - 80 / 2, this.height_ / 2 - 220, 80, 80);
    }

}