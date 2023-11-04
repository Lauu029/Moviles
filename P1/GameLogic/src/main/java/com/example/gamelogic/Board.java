package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

import java.util.ArrayList;
/* Clase tablero que controla todos los aspectos de cada partida*/
public class Board implements IGameObject {
    //Relativo a la dificultad del nivel
    private int codeColors, usableColors, tries;
    private boolean canRepeat;
    private boolean daltonics;
    private int acutalTry;

    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth, sceneHeight;
    private int subdivisionsScreen, heightSubdivisions;
    private int circleRad;
    private int[] yPositions;

    //Objetos de la partida
    private SolutionCircle[] usableColorsCircles;
    private TryCircle[][] playerTries;
    private HintsCircle[][] hints;

    private GameManager gm;


    //Colores totales que puede llegar a haber en una partida
    private int[] totalPossibleColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};

    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<Circle> gameObjectsTable = new ArrayList<>();
    private IFont font1, font2, font3;
    private int hintsPos_;

    Board(int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH) {
        gm = GameManager.getInstance();
        this.font1 = gm.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, false, false);
        this.codeColors = codeColors_;
        this.tries = tries_;
        this.usableColors = usableColors;
        this.canRepeat = canRepeat_;
        this.acutalTry = 0;
        this.sceneWidth = scW;
        this.sceneHeight = scH;
        this.daltonics = false;
        //Inicialización de las subdivisiones de la pantalla para repartir el espacio entre los objetos
        this.subdivisionsScreen = 13;
        this.heightSubdivisions = this.sceneHeight / subdivisionsScreen;
        this.yPositions = new int[this.subdivisionsScreen];
        for (int i = 0; i < this.subdivisionsScreen; i++) {
            this.yPositions[i] = i * this.heightSubdivisions;
        }

        this.circleRad = this.heightSubdivisions / 2 - 8;
        //Inicialización de los circulos de la solucion y la matriz de intentos
        createCircles();
    }
    //Crea los círculos de la matriz de soluciones y de las pistas, sin color. Las posiciones están a 0 porque se setean más adelante
    void createCircles() {
        this.usableColorsCircles = new SolutionCircle[this.usableColors];
        this.playerTries = new TryCircle[this.tries][this.codeColors];
        this.hints = new HintsCircle[this.tries][this.codeColors];
        //Inicializa los colores que se puedan usar
        for (int i = 0; i < usableColors; i++) {
            usableColorsCircles[i] = new SolutionCircle(Integer.toString(i), this.font1, this.circleRad, 0, 0, -1);
            usableColorsCircles[i].setColor(totalPossibleColors[i]);
            usableColorsCircles[i].setIdColor(i);
            gameObjectsTable.add(usableColorsCircles[i]);
        }
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < codeColors; j++) {
                playerTries[i][j] = new TryCircle("", this.font1, this.circleRad, 0, yPositions[i + 2], i, j);
                playerTries[i][j].setGameTry(acutalTry);
                gameObjectsTable.add(playerTries[i][j]);
                hints[i][j] = new HintsCircle("", this.font1, this.circleRad / 3, 0, yPositions[i + 2], i);
            }
        }
        //Seteo las posiciones de los circulos en la escena (como no se mueven solo tengo que hacerlo una vez
        setCirclesPositions();
    }
    /*Avanza al siguiente intento y resetea la solución tempporal, avisa a todos los cículos para que
     * actualicen la información del intento actual de la partida*/
    void nexTry() {
        acutalTry++;
        gm.resetLevelSolution();
        for (int i = 0; i < this.tries; i++) {
            for (int j = 0; j < codeColors; j++) {
                playerTries[i][j].setGameTry(acutalTry);
            }
        }
    }
    //Coloca las pistas para el siguiente intento dependiendo de la cantidad de colores y posiciones correctas
    public void setNewHints(int correctPositions, int correctColors) {
        int array_pos = 0;

        for (int i = 0; i < correctPositions; i++) {
            hints[acutalTry][array_pos].putHintColor(true);
            array_pos++;
        }
        for (int i = 0; i < correctColors; i++) {
            hints[acutalTry][array_pos].putHintColor(false);
            array_pos++;
        }
    }

    @Override
    public void update(double time) {
        for (IGameObject g : gameObjectsTable) {
            g.update(time);
        }
    }
    //Coloca los círculos centrados en la pantalla dependiendo del espacio disponible y la cantidad de círculos que haya que poner
    private void setCirclesPositions() {
        int offset = 4;
        int width_ = gm.getwidth();
        int totalCircleWidth = usableColors * (this.circleRad * 2 + offset); // Ancho total de todos los círculos
        int x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < usableColors; i++) {
            usableColorsCircles[i].setPositions(x_ + i * (this.circleRad * 2 + offset) + this.circleRad, yPositions[12] + this.circleRad);
        }
        offset = 2;
        totalCircleWidth = codeColors * (this.circleRad * 2 + offset) + (codeColors / 2) * (this.circleRad + offset);
        x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < tries; i++) {

            x_ = (width_ - totalCircleWidth) / 2;
            for (int j = 0; j < codeColors; j++) {
                playerTries[i][j].setPositions(x_ + j * (this.circleRad * 2 + offset) + this.circleRad, yPositions[i + 2] + this.circleRad / 2);
            }
            // Dibujo los circulos de las pistas
            x_ = x_ + (codeColors - 1) * (this.circleRad * 2 + offset*2) + this.circleRad + offset*2 + this.circleRad * 2+10;

            int xUsed = x_;
            int half = codeColors / 2;
            int offsetY = this.circleRad;
            int c = 0;
            for (int j = 0; j < codeColors; j++) {
                if (j == half) {
                    offsetY = 0;
                    x_ = xUsed;
                    c = 0;
                }
                hints[i][j].setPositions(x_ + c * (this.circleRad + offset*2) + this.circleRad / 2, yPositions[i + 2] - offsetY + this.circleRad / 2 + this.circleRad / 2);

                c++;
            }
        }
        hintsPos_=x_;
    }

    @Override
    public void render(IGraphics graph) {
        graph.setColor(0xFFad909c);
        graph.fillRectangle(0, yPositions[12], sceneWidth, heightSubdivisions + 8);
        graph.setFont(font2);
        graph.setColor(0xFF272b2b);
        graph.drawText("Averigua el codigo", sceneWidth / 2, yPositions[0] + 6);
        graph.setFont(font3);
        graph.drawText("Te quedan " + (this.tries - acutalTry) + " intentos", sceneWidth / 2, yPositions[1] + 6);
        for (IGameObject g : gameObjectsTable) {
            g.render(graph);
        }
        int offsetY = 5;
        int posX = (sceneWidth - (sceneWidth - 8)) / 2;
        for (int i = 0; i < this.tries; i++) {
            graph.setColor(0xFFad909c);
            graph.setStrokeWidth(2);
            graph.drawRoundRectangle(posX, yPositions[i + 2] - (this.circleRad /2)-(offsetY/2)  , sceneWidth - 8, circleRad * 2 + offsetY, 10);
            graph.drawLine(50, yPositions[i + 2]- (this.circleRad /2)-(offsetY/2)+4,50,(yPositions[i + 2]- (this.circleRad /2)-(offsetY/2))+ circleRad * 2 + offsetY-4);
            graph.drawLine(hintsPos_-5, yPositions[i + 2]- (this.circleRad /2)-(offsetY/2)+4,hintsPos_-5,(yPositions[i + 2]- (this.circleRad /2)-(offsetY/2))+ circleRad * 2 + offsetY-4);

            graph.setFont(font2);
            graph.setColor(0xFF472735);
            graph.drawText(i + 1 + "", 25, yPositions[i + 2]);


            for (int j = 0; j < codeColors; j++) {
                hints[i][j].render(graph);
            }
        }
    }

    @Override
    public void init() {

        font2 = gm.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, true, false);
        font3 = gm.getIEngine().getGraphics().newFont("Lexendt.ttf", 17, false, false);
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        for (IGameObject g : gameObjectsTable)
            if (g.handleInput(event))
                return true;
        return false;
    }

    public int getAcutalTry() {
        return acutalTry;
    }

    public void changeDaltonics(boolean dalt) {
        for (Circle c: gameObjectsTable) {
            c.setDaltonics(dalt);
        }
    }

    public void putNewColor(int id, int color) {
        for (int i = 0; i < this.codeColors; i++) {
            if (!playerTries[acutalTry][i].hasColor()) {
                playerTries[acutalTry][i].setColor(color, id);
                this.gm.putColorSolution(i, id);
                break;
            }
        }
    }

}
