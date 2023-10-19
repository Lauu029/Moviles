package com.example.gamelogic;

import com.example.engine.GameObject;

public class Board {
    private int colores, intentos, colores_usar;
    private int intentoActual;
    private boolean rep_color;
    private int[][] tablero;
    private Circles[][] fichas;

    Board(int c, int i, int u, boolean rep) {
        colores = c;
        intentos = i;
        colores_usar = u;
        rep_color = rep;
        intentoActual = 0;
        tablero = new int[intentos][colores];
        fichas = new Circles[intentos][colores];
    }

    void initializeFichas() {
        for (int i = 0; i < intentos; i++) {
            for (int j = 0; j < colores; j++) {
                //fichas[i][j]=new Circles(0xFF9b9b9b,2);
            }
        }
    }

    void putColor(int pos, int ficha) {
        tablero[intentoActual][pos] = ficha;
    }

    void siguienteIntento() {
        intentoActual++;
    }

    void dibujaTablero() {
        int posX = 50, posY = 50;
        for (int i = 0; i < intentos; i++) {
            for (int j = 0; j < colores; j++) {

            }
        }
    }
}
