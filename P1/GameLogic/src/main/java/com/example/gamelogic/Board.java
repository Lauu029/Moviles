package com.example.gamelogic;

import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

public class Board implements IGameObject {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tablero;
    private int[] posicionesVerticales;
    //   private Circle[][] tableroRender;
    private int sceneWidth, sceneHeight;
    private Circle[] possibleColors;
    private Circle[][] circles;
    private int[] totalColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE6E6FA, 0xFF00CED1, 0xFFD1E231};
    private int radios = 25;
    private int espaciosTotales = 12;
    private int divisiones;


    Board(int c, int i, int u, boolean rep, int scW, int scH) {
        this.colores = c;
        this.intentos = i;
        this.colores_usar = u;
        this.rep_color = rep;
        this.intentoActual = 0;
        this.sceneWidth = scW;
        this.sceneHeight = scH;
        this.divisiones = this.sceneHeight / this.espaciosTotales;
        this.tablero = new int[this.intentos][this.colores];
        createCircles();
    }

    void putColor(int pos, int ficha) {
        tablero[intentoActual][pos] = ficha;
    }

    void createCircles() {
        circles = new Circle[intentos][colores];
        for (int i = 0; i < intentos; i++) {
            for (int j = 0; j < colores; j++) {
                circles[i][j] = new Circle(false, this.radios, 0, 0);
            }
        }
        possibleColors = new Circle[this.colores_usar];
        for (int j = 0; j < colores_usar; j++) {
            possibleColors[j] = new Circle(true, this.radios, 0, 0);
            possibleColors[j].setColor(totalColors[j]);
        }
    }

    void siguienteIntento() {
        intentoActual++;
    }


    @Override
    public void update(double time) {

    }

    private void dibujaColoresDisponibles(IGraphics graph) {
        int w = sceneWidth;
        int totalWidth = this.colores * this.radios * 2+10;
        int spaceToEachSide = (w - totalWidth) / 2;

        graph.setColor(0xFF808080);
        graph.fillRectangle(0, sceneHeight - divisiones  / 2, w, this.radios * 2 + 20);

        for (int i = 0; i < colores; i++) {
            int x = spaceToEachSide + i * (this.radios * 2);
            possibleColors[i].setPositions(x, sceneHeight - divisiones);
            //possibleColors[i].descubrir(true);
            possibleColors[i].render(graph);
        }
    }

    @Override
    public void render(IGraphics graph) {
        dibujaColoresDisponibles(graph);
    }


    @Override
    public void init() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return false;
    }
}
