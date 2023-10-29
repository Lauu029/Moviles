package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class Board implements IGameObject {
    //Relativo a la dificultad del nivel
    private int code_colors, usable_colors, tries;
    private boolean can_repeat;

    private int acutal_try;
    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth, sceneHeight;
    private int subdivisions_screen, height_subdivisions;
    private int circle_rad;
    private int[] y_positions;

    //Objetos de la partida
    private Circle[] usable_colors_circles;
    private Circle[][] player_tries;

    //Colores totales que puede llegar a haber en una partida
    private int[] total_possible_colors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE6E6FA, 0xFF00CED1, 0xFFD1E231};

    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<IGameObject> game_objects_table = new ArrayList<>();

    Board(int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH) {
        this.code_colors = codeColors_;
        this.tries = tries_;
        this.usable_colors = usableColors;
        this.can_repeat = canRepeat_;
        this.acutal_try = 0;
        this.sceneWidth = scW;
        this.sceneHeight = scH;

        //Inicialización de las subdivisiones de la pantalla para repartir el espacio entre los objetos
        this.subdivisions_screen = 12;
        this.height_subdivisions = this.sceneHeight / subdivisions_screen;
        this.y_positions = new int[this.subdivisions_screen];
        for (int i = 0; i < this.subdivisions_screen; i++) {
            this.y_positions[i] = i * this.height_subdivisions;
        }

        this.circle_rad = this.height_subdivisions / 2 - 3;

        //Inicialización de los circulos de la solucion y la matriz de intentos
        createCircles();
    }

//    void putColor(int pos, int ficha) {
//        tablero[intentoActual][pos] = ficha;
//    }

    void createCircles() {
        this.usable_colors_circles = new Circle[this.usable_colors];
        this.player_tries = new Circle[this.tries][this.code_colors];
        //Inicializa los colores que se puedan usar
        for (int i = 0; i < usable_colors; i++) {
            usable_colors_circles[i] = new Circle(true, this.circle_rad, 0, 0, -1);
            usable_colors_circles[i].setColor(total_possible_colors[i + 1]);
            usable_colors_circles[i].setIdColor(i);
            game_objects_table.add(usable_colors_circles[i]);
        }
        this.player_tries = new Circle[this.tries][this.code_colors];
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < code_colors; j++) {
                player_tries[i][j] = new Circle(false, this.circle_rad, 0, y_positions[i + 1], i);
                player_tries[i][j].setGameTry(acutal_try);
                game_objects_table.add(player_tries[i][j]);
            }
        }
        //Seteo las posiciones de los circulos en la escena (como no se mueven solo tengo que hacerlo una vez
        setCirclesPositions();
    }

    void nexTry() {
        acutal_try++;
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < code_colors; j++) {
                player_tries[i][j].setGameTry(acutal_try);
            }
        }
    }

    @Override
    public void update(double time) {
    }

    private void setCirclesPositions() {
        int totalWidth = this.usable_colors * this.circle_rad * 2;
        int spaceToEachSide = (sceneWidth - totalWidth) / 2;

        for (int i = 0; i < usable_colors; i++) {
            int x = spaceToEachSide + i * (this.circle_rad * 2);
            usable_colors_circles[i].setPositions(x, y_positions[11] + this.circle_rad);
        }

        for (int i = 0; i < tries; i++) {
            for (int j = 0; j < code_colors; j++) {
                int x = spaceToEachSide + j * (this.circle_rad * 2);
                player_tries[i][j].setPositions(x, y_positions[i + 1] + this.circle_rad / 2);
            }
        }
    }
    @Override
    public void render(IGraphics graph) {
//        graph.setColor(0xFF000000);
//        for (int i = 0; i < this.tries; i++) {
//            graph.drawRoundRectangle(10, y_positions[i + 1], sceneWidth - 20, height_subdivisions - 10, 3);
//        }
        graph.setColor(0xFF808080);
        graph.fillRectangle(0, y_positions[11], sceneWidth, height_subdivisions);
        for (IGameObject g : game_objects_table) {
            g.render(graph);
        }
    }
    @Override
    public void init() {
    }
    @Override
    public boolean handleInput(TouchEvent event) {
        for (IGameObject g : game_objects_table)
            if (g.handleInput(event))
                return true;
        return false;
    }
}
