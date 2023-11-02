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
    private boolean daltonics;
    private int acutal_try;
    private IFont font;
    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth, sceneHeight;
    private int subdivisions_screen, height_subdivisions;
    private int circle_rad;
    private int[] y_positions;

    //Objetos de la partida
    private SolutionCircle[] usable_colors_circles;
    private TryCircle[][] player_tries;
    private HintsCircle[][] hints;

    private GameManager gm;

    //Colores totales que puede llegar a haber en una partida
    private int[] total_possible_colors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};

    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<Circle> game_objects_table = new ArrayList<>();
    IFont fuente, fuente2;

    Board(IFont font, int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH) {
        this.font = font;

        this.code_colors = codeColors_;
        this.tries = tries_;
        this.usable_colors = usableColors;
        this.can_repeat = canRepeat_;
        this.acutal_try = 0;
        this.sceneWidth = scW;
        this.sceneHeight = scH;
        this.daltonics = false;
        //Inicialización de las subdivisiones de la pantalla para repartir el espacio entre los objetos
        this.subdivisions_screen = 13;
        this.height_subdivisions = this.sceneHeight / subdivisions_screen;
        this.y_positions = new int[this.subdivisions_screen];
        for (int i = 0; i < this.subdivisions_screen; i++) {
            this.y_positions[i] = i * this.height_subdivisions;
        }

        this.circle_rad = this.height_subdivisions / 2 - 8;
        gm = GameManager.getInstance();
        //Inicialización de los circulos de la solucion y la matriz de intentos
        createCircles();
    }

    void createCircles() {
        this.usable_colors_circles = new SolutionCircle[this.usable_colors];
        this.player_tries = new TryCircle[this.tries][this.code_colors];
        this.hints = new HintsCircle[this.tries][this.code_colors];
        //Inicializa los colores que se puedan usar
        for (int i = 0; i < usable_colors; i++) {
            usable_colors_circles[i] = new SolutionCircle(Integer.toString(i), this.font, this.circle_rad, 0, 0, -1);
            usable_colors_circles[i].setColor(total_possible_colors[i]);
            usable_colors_circles[i].setIdColor(i);
            game_objects_table.add(usable_colors_circles[i]);
        }
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < code_colors; j++) {
                player_tries[i][j] = new TryCircle("", this.font, this.circle_rad, 0, y_positions[i + 2], i, j);
                player_tries[i][j].setGameTry(acutal_try);
                game_objects_table.add(player_tries[i][j]);
                hints[i][j] = new HintsCircle("", this.font, this.circle_rad / 3, 0, y_positions[i + 2], i);
            }
        }
        //Seteo las posiciones de los circulos en la escena (como no se mueven solo tengo que hacerlo una vez
        setCirclesPositions();
    }

    void nexTry() {
        acutal_try++;
        gm.resetLevelSolution();
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < code_colors; j++) {
                player_tries[i][j].setGameTry(acutal_try);
            }
        }
    }

    public void setNewHints(int correctPositions, int correctColors) {
        int array_pos = 0;

        for (int i = 0; i < correctPositions; i++) {
            hints[acutal_try][array_pos].putHintColor(true);
            array_pos++;
        }
        for (int i = 0; i < correctColors; i++) {
            hints[acutal_try][array_pos].putHintColor(false);
            array_pos++;
        }
    }

    @Override
    public void update(double time) {
        for (IGameObject g : game_objects_table) {
            g.update(time);
        }
    }

    private void setCirclesPositions() {
        int totalWidth = this.usable_colors * this.circle_rad * 2;
        int spaceToEachSide = (sceneWidth - totalWidth) / 2;
        int x = 10 + this.circle_rad;
        int offset = 4;
        int width_ = gm.getwidth();
        int height_ = gm.getHeight();
        int totalCircleWidth = usable_colors * (this.circle_rad * 2 + offset); // Ancho total de todos los círculos
        int x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < usable_colors; i++) {
            usable_colors_circles[i].setPositions(x_ + i * (this.circle_rad * 2 + offset) + this.circle_rad, y_positions[12] + this.circle_rad);
        }
        offset = 2;
        totalCircleWidth = code_colors * (this.circle_rad * 2 + offset) + (code_colors / 2) * (this.circle_rad + offset);
        x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < tries; i++) {

            x_ = (width_ - totalCircleWidth) / 2;
            for (int j = 0; j < code_colors; j++) {
                player_tries[i][j].setPositions(x_ + j * (this.circle_rad * 2 + offset) + this.circle_rad, y_positions[i + 2] + this.circle_rad / 2);
            }
            // Dibujo los circulos de las pistas
            x_ = x_ + (code_colors - 1) * (this.circle_rad * 2 + offset) + this.circle_rad + offset + this.circle_rad * 2;
            int xused = x_;
            int mitad = code_colors / 2;
            int offsty = this.circle_rad;
            int c = 0;
            for (int j = 0; j < code_colors; j++) {
                if (j == mitad) {
                    offsty = 0;
                    x_ = xused;
                    c = 0;
                }
                hints[i][j].setPositions(x_ + c * (this.circle_rad + offset) + this.circle_rad / 2, y_positions[i + 2] - offsty + this.circle_rad / 2 + this.circle_rad / 2);

                c++;
            }
        }
    }

    @Override
    public void render(IGraphics graph) {
        graph.setColor(0xFFacb5b4);
        graph.fillRectangle(0, y_positions[12], sceneWidth, height_subdivisions + 8);
        graph.setFont(fuente);
        graph.setColor(0xFF272b2b);
        graph.drawText("Averigua el codigo", sceneWidth / 2, y_positions[0] + 6);
        graph.setFont(fuente2);
        graph.drawText("Te quedan " + (this.tries - acutal_try) + " intentos", sceneWidth / 2, y_positions[1] + 6);
        for (IGameObject g : game_objects_table) {
            g.render(graph);
        }
        int offsety = 5;
        int posx = (sceneWidth - (sceneWidth - 8)) / 2;
        for (int i = 0; i < this.tries; i++) {
            graph.setColor(0xFF455657);
            graph.setStrokeWidth(2);
            graph.drawRoundRectangle(posx, y_positions[i + 2] - this.circle_rad / 2 - offsety / 2, sceneWidth - 8, circle_rad * 2 + offsety, 10);

            graph.setFont(fuente);
            graph.drawText(i + 1 + "", 30, y_positions[i + 2]);
            for (int j = 0; j < code_colors; j++) {
                hints[i][j].render(graph);
            }
        }
    }

    @Override
    public void init() {
        fuente = gm.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, true, false);
        fuente2 = gm.getIEngine().getGraphics().newFont("Lexendt.ttf", 17, false, false);
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        for (IGameObject g : game_objects_table)
            if (g.handleInput(event))
                return true;
        return false;
    }

    public int getAcutalTry() {
        return acutal_try;
    }

    public void changeDaltonics(boolean dalt) {
        for (Circle c:game_objects_table) {
            c.setDaltonics(dalt);
        }
    }

    public void putNewColor(int id, int color) {
        for (int i = 0; i < this.code_colors; i++) {
            if (!player_tries[acutal_try][i].hasColor()) {
                player_tries[acutal_try][i].setColor(color, id);
                this.gm.putColorSolution(i, id);
                System.out.println("He puesto un color en la posicion " + i);
                break;
            }
        }
    }

}
