package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class EndScene implements IScene {

    private IEngine IEngine_;
    private int[] sol_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width, height;
    private Button button2;
    private ButtonLevel button1;
    private IFont font;
    private IFont font1, font2;
    private int tries;
    private int[] totalPossibleColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};
    private boolean win = false;

    public EndScene(IEngine IEngine, int w, int h, boolean win, int[] sol, int intentos) {
        IEngine_ = IEngine;
        width = w;
        height = h;
        System.out.print("Scene Width: " + width + " Scene Height: " + height + "\n");
        this.win = win;
        sol_ = sol;
        tries = intentos;
    }

    @Override
    public void init() {
        //creacion de la solucion
        IGraphics graph = IEngine_.getGraphics();
        if (win)
            this.font = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, true, true);
        else this.font = graph.newFont("Hexenkoetel-qZRv1.ttf", 40, false, true);
        graph.setFont(this.font);
        font1 = graph.newFont("Hexenkoetel-qZRv1.ttf", 20, false, false);
        font2 = graph.newFont("Hexenkoetel-qZRv1.ttf", 30, false, false);
        this.button1 = new ButtonLevel("Volver Jugar", font1, 0XFFFB839B
                , 150, 50, 35, this.width / 2 - 150 / 2, this.height / 2 + 20, SceneNames.GAME, GameManager.getInstance().getLevel().getLevelDiff());
        this.button2 = new Button("Elegir Dificultad", font1, 0XFFFB839B
                , 150, 50, 35, this.width / 2 - (150 / 2), this.height / 2 + 90, SceneNames.LEVEL);

        addGameObject(button1);
        addGameObject(button2);
    }


    public void addGameObject(IGameObject gm) {
        IGameObjects_.add(gm);
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
        for (IGameObject g : IGameObjects_)
            for (TouchEvent event : events)
                if (g.handleInput(event))
                    return;
    }

    @Override
    public void render() {
        IGraphics graph = IEngine_.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFfff0f6);

        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.IEngine_.getGraphics().setFont(font);
        if (!win) {
            graph.drawText("GAME OVER", width / 2, 50);
            graph.setFont(font1);
            graph.drawText("Te has quedado sin intentos", width / 2, 120);
            graph.drawText("codigo:", width / 2, height / 2 - 70);
            drawCircles(graph);

        } else {
            graph.drawText("ENHORABUENA!!", width / 2, 50);
            graph.setFont(font1);
            graph.drawText("Has averiguado el codigo en", width / 2, 120);
            graph.setFont(font2);
            graph.drawText(tries + " intentos:", width / 2, height / 2 - 110);
            graph.setFont(font1);
            graph.drawText("codigo:", width / 2, height / 2 - 70);
            drawCircles(graph);
        }

    }

    void drawCircles(IGraphics graph) {
        int offset = 20;
        int radius = 20;
        int totalCircleWidth = sol_.length * (radius * 2 + offset); // Ancho total de todos los círculos
        int x = (width - totalCircleWidth) / 2;
        for (int i = 0; i < sol_.length; i++) {
            graph.setColor(totalPossibleColors[sol_[i]]);
            graph.drawCircle(x + i * (radius * 2 + offset) + radius, height / 2 + radius - 50, radius);
            if (GameManager.getInstance().getDaltonic()) {
                graph.setColor(0xFF000000);
                graph.setFont(this.font1);
                graph.drawText(sol_[i] + "", x + i * (radius * 2 + offset) + radius, height / 2 + (radius) - 53);
            }
        }
    }

    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }
}
