package com.example.gamelogic;


import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class LevelScene implements IScene {

    private IEngine iEngine;
    private ArrayList<IGameObject> iGameObjects = new ArrayList<>();
    private int width, height;

    private IFont font;
    private ISound myButtonSound;

    public LevelScene(IEngine IEngine, int w, int h) {
        iEngine = IEngine;
        width = w;
        height = h;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = iEngine.getGraphics();

        String[] names = {"Facil",
                "Medio",
                "Dificil",
                "Imposible"};

        LevelDifficulty[] diff = {LevelDifficulty.FACIL,
                LevelDifficulty.MEDIO,
                LevelDifficulty.DIFICIL,
                LevelDifficulty.IMPOSIBLE};
        int[] colors = {0XFFF6C0CF, 0XFFDDB5DF, 0XFFA9B2EC, 0xFF58B2E6};
        font = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        myButtonSound = iEngine.getAudio().newSound("buttonClicked.wav");
        for (int i = 0; i < 4; i++) {
            ButtonLevel but = new ButtonLevel(names[i], font,
                    colors[i], 150, 50, 35, this.width / 2 - 150 / 2, 100 * i + 100, SceneNames.GAME, diff[i], myButtonSound);

            this.addGameObject(but);
        }
        ButtonImage but2 = new ButtonImage("flecha.png", 40, 40, 0, 0, SceneNames.MENU);
        this.addGameObject(but2);
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
        for (IGameObject g : iGameObjects)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    @Override
    public void render() {
        IGraphics graph = iEngine.getGraphics();

        graph.clear(0xFFfff0f6);
        for (int i = 0; i < iGameObjects.size(); i++) {
            iGameObjects.get(i).render(graph);
        }
        this.iEngine.getGraphics().setFont(font);
        graph.setColor(0xFF000000);
        graph.drawText("¿En que dificultad quieres jugar?", width / 2, 50);
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < iGameObjects.size(); i++) {
            iGameObjects.get(i).update(time);
        }
    }
}
