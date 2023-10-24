package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

import java.util.Vector;

public class Board implements IGameObject {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tableroLogico;
    //   private Circle[][] tableroRender;
    private Vector<Vector<Circle>> tableroRender;

    Board(int c, int i, int u, boolean rep) {

        this.colores = c;
        this.intentos = i;
        this.colores_usar = u;
        this.rep_color = rep;
        this.intentoActual = 0;
        this.tableroLogico = new int[this.intentos][this.colores];

        initTablero();
    }

    void putColor(int pos, int ficha) {
        tableroLogico[intentoActual][pos] = ficha;
    }

    void siguienteIntento() {
        intentoActual++;
    }

    void initTablero() {
        // this.tableroRender = new Circle[this.intentos][this.colores];

        int tempX = 50, tempY = 50, rad = 30;
        for (int i = 0; i < this.intentos; i++) {

            Vector<Circle> c = null;

            for (int j = 0; j < colores; j++) {
                System.out.print("Circulo creando\n");
                c.add(new Circle(rad, tempX, tempY));
                tempX += 2 * rad + 10;

            }
            tableroRender.add(c);
            tempX = 50;
            tempY += 2 * rad + 20;
        }
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        for (int i = 0; i < intentoActual; i++) {
            Vector<Circle> c = tableroRender.get(i);
            for (int j = 0; j < colores; j++) {
                c.get(j).render(graph);
                System.out.print("Dibujo un circulito\n");
            }
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
