package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Board implements IGameObject {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tableroLogico;
    //   private Circle[][] tableroRender;

    Board(int c, int i, int u, boolean rep) {

        this.colores = c;
        this.intentos = i;
        this.colores_usar = u;
        this.rep_color = rep;
        this.intentoActual = 0;
        this.tableroLogico = new int[this.intentos][this.colores];

    }

    void putColor(int pos, int ficha) {
        tableroLogico[intentoActual][pos] = ficha;
    }

    void siguienteIntento() {
        intentoActual++;
    }


    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        int tempX = 50, tempY = 50, rad = 30;
        for (int i = 0; i < intentos; i++) {
            for (int j = 0; j < colores; j++) {
                graph.setColor(0Xff808080);
                graph.drawCircle(tempX, tempY, rad);
                System.out.print("Circulo dibuujo " + j + "\n");
                tempX += 2 * rad + 10;
            }
            tempX = 50;
            tempY += 2 * rad + 20;
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
