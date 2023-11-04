package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;
import java.util.ArrayList;

public class MenuScene implements IScene {

    private IEngine iEngine;
    private ArrayList<IGameObject> iGameObjects = new ArrayList<>();
    private int width, height;
    private Button button;
    private IFont font;

    private IFont fontButton;
    private IImage myIcon;
    private ISound myButtonSound;

    public MenuScene(IEngine IEngine, int w, int h) {
        iEngine = IEngine;
        width = w;
        height = h;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine.getGraphics();
        this.font = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        graph.setFont(this.font);

        fontButton = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound = iEngine.getAudio().newSound("buttonClicked.wav");
        this.button = new Button("Jugar", fontButton, 0XFFFB839B
                , 150, 50, 35, this.width / 2 - 150 / 2, this.height / 2 + 20, SceneNames.LEVEL, myButtonSound);

        addGameObject(button);

        try {
            myIcon = graph.newImage("cerebro.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addGameObject(IGameObject gm) {
        iGameObjects.add(gm);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (IGameObject g : iGameObjects) {
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;

        }
    }

    @Override
    public void render() {
        IGraphics graph = iEngine.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);

        for (int i = 0; i < iGameObjects.size(); i++) {
            iGameObjects.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.iEngine.getGraphics().setFont(font);
        graph.drawText("MasterMind", width / 2, 100);
        graph.drawImage(myIcon, this.width / 2 - 80 / 2, this.height / 2 - 140, 80, 80);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < iGameObjects.size(); i++) {
            iGameObjects.get(i).update(time);
        }
    }

}
