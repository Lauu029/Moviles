package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Board implements IGameObject {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tablero;
    //   private Circle[][] tableroRender;
    private int sceneWidth, sceneHeight;
    private int[] possibleColors;
    private int[] totalColors = new int[]{0xFFFF0000, 0xFF008000, 0xFF0000FF, 0xFFFFFF00,
            0xFF00FFFF, 0xFFFF00FF, 0xFFFFA500, 0xFF800080, 0xFFA52A2A};

    Board(int c, int i, int u, boolean rep, int scW, int scH) {
        this.colores = c;
        this.intentos = i;
        this.colores_usar = u;
        this.rep_color = rep;
        this.intentoActual = 0;
        this.sceneWidth = scW;
        this.sceneHeight = scH;
        this.tablero = new int[this.intentos][this.colores];
        possibleColors = new int[this.colores_usar];
        for (int j = 0; j < colores_usar; j++) {
            possibleColors[j] = totalColors[j];
        }
    }

    void putColor(int pos, int ficha) {
        tablero[intentoActual][pos] = ficha;
    }

    void siguienteIntento() {
        intentoActual++;
    }


    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        int tempX = 50, tempY = 50, rad = 25;
        for (int i = 0; i < intentos; i++) {
            for (int j = 0; j < colores; j++) {
                if (tablero[i][j] == 0)
                    graph.setColor(0Xff808080);
                else
                    graph.setColor(possibleColors[tablero[intentos][colores]]);
                graph.drawCircle(tempX, tempY, rad);
                tempX += 2 * rad + 10;
            }
            tempX = 50;
            tempY += 2 * rad + 20;
        }
        //dibujo las posibles respuestas
        tempY = sceneHeight - 100;
        int offset = colores_usar * rad + (colores_usar - 1) * 10;
        tempX = (sceneWidth - offset) / 2;
        for (int i = 0; i < colores_usar; i++) {
            graph.setColor(possibleColors[i]);
            graph.drawCircle(tempX, tempY, rad);
            tempX += 2 * rad + 10;
        }
    }


    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return false;
    }
}
