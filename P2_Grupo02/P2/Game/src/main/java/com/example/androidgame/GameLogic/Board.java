package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;

import java.util.ArrayList;
/* Clase tablero que controla todos los aspectos de cada partida*/
public class Board extends GameObject {
    //Relativo a la dificultad del nivel
    private int codeColors_, usableColors_, tries_;
    private boolean canRepeat_;
    private boolean daltonics_;
    private int acutalTry_;

    //Lógica de espacio y dimensiones en la pantalla
    private int sceneWidth_, sceneHeight_;
    private int subdivisionsScreen_, heightSubdivisions_;
    private int circleRad_;
    private int[] yPositions_;

    //Objetos de la partida
    private SolutionCircle[] usableColorsCircles_;
    private TryCircle[][] playerTries_;
    private HintsCircle[][] hints_;
    private GameManager gm_;

    //Colores totales que puede llegar a haber en una partida
    private int[] totalPossibleColors = new int[]{0xFFFFC0CB, 0xFF87CEEB, 0xFF98FB98, 0xFFFFFF99,
            0xFFE6E6FA, 0xFFFFDAB9, 0xFFE7FFAC, 0xFFFF8FAB, 0xFF6FC0AB};

    //para gestionar todos los inputs y renders de la clase tablero
    private ArrayList<Circle> gameObjectsTable_ = new ArrayList<>();
    private Font font1_, font2_, font3_;
    private int hintsPos_;

    Board(int codeColors_, int tries_, int usableColors, boolean canRepeat_, int scW, int scH) {
        gm_ = GameManager.getInstance();
        this.font1_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, false, false);
        this.codeColors_ = codeColors_;
        this.tries_ = tries_;
        this.usableColors_ = usableColors;
        this.canRepeat_ = canRepeat_;
        this.acutalTry_ = 0;
        this.sceneWidth_ = scW;
        this.sceneHeight_ = scH;
        this.daltonics_ = false;
        //Inicialización de las subdivisiones de la pantalla para repartir el espacio entre los objetos
        this.subdivisionsScreen_ = 13;
        this.heightSubdivisions_ = this.sceneHeight_ / subdivisionsScreen_;
        this.yPositions_ = new int[this.subdivisionsScreen_];
        for (int i = 0; i < this.subdivisionsScreen_; i++) {
            this.yPositions_[i] = i * this.heightSubdivisions_;
        }

        this.circleRad_ = this.heightSubdivisions_ / 2 - 8;
        //Inicialización de los circulos de la solucion y la matriz de intentos
        createCircles();
    }
    //Crea los círculos de la matriz de soluciones y de las pistas, sin color. Las posiciones están a 0 porque se setean más adelante
    void createCircles() {
        this.usableColorsCircles_ = new SolutionCircle[this.usableColors_];
        this.playerTries_ = new TryCircle[this.tries_][this.codeColors_];
        this.hints_ = new HintsCircle[this.tries_][this.codeColors_];
        //Inicializa los colores que se puedan usar
        for (int i = 0; i < usableColors_; i++) {
            usableColorsCircles_[i] = new SolutionCircle(Integer.toString(i), this.font1_, this.circleRad_, 0, 0, -1);
            usableColorsCircles_[i].setColor_(totalPossibleColors[i]);
            usableColorsCircles_[i].setImage(""+(i+1));
            usableColorsCircles_[i].setIdColor_(i);
            gameObjectsTable_.add(usableColorsCircles_[i]);
        }
        for (int i = 0; i < this.tries_; i++) {
            for (int j = 0; j < codeColors_; j++) {
                playerTries_[i][j] = new TryCircle("", this.font1_, this.circleRad_, 0, yPositions_[i + 2], i, j);
                playerTries_[i][j].setGameTry_(acutalTry_);
                gameObjectsTable_.add(playerTries_[i][j]);
                hints_[i][j] = new HintsCircle("", this.font1_, this.circleRad_ / 3, 0, yPositions_[i + 2], i);
            }
        }
        //Seteo las posiciones de los circulos en la escena (como no se mueven solo tengo que hacerlo una vez
        setCirclesPositions();
    }
    /*Avanza al siguiente intento y resetea la solución tempporal, avisa a todos los cículos para que
     * actualicen la información del intento actual de la partida*/
    void nexTry() {
        acutalTry_++;
        gm_.resetLevelSolution();
        for (int i = 0; i < this.tries_; i++) {
            for (int j = 0; j < codeColors_; j++) {
                playerTries_[i][j].setGameTry_(acutalTry_);
            }
        }
    }
    //Coloca las pistas para el siguiente intento dependiendo de la cantidad de colores y posiciones correctas
    public void setNewHints(int correctPositions, int correctColors) {
        int array_pos = 0;

        for (int i = 0; i < correctPositions; i++) {
            hints_[acutalTry_][array_pos].putHintColor(true);
            array_pos++;
        }
        for (int i = 0; i < correctColors; i++) {
            hints_[acutalTry_][array_pos].putHintColor(false);
            array_pos++;
        }
    }

    @Override
    public void update(double time) {
        for (GameObject g : gameObjectsTable_) {
            g.update(time);
        }
    }

    //Coloca los círculos centrados en la pantalla dependiendo del espacio disponible y la cantidad de círculos que haya que poner
    private void setCirclesPositions() {
        int offset = 4;
        int width_ = gm_.getwidth();
        int totalCircleWidth = usableColors_ * (this.circleRad_ * 2 + offset); // Ancho total de todos los círculos
        int x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < usableColors_; i++) {
            usableColorsCircles_[i].setPositions(x_ + i * (this.circleRad_ * 2 + offset) + this.circleRad_, yPositions_[12] + this.circleRad_);
        }
        offset = 2;
        totalCircleWidth = codeColors_ * (this.circleRad_ * 2 + offset) + (codeColors_ / 2) * (this.circleRad_ + offset);
        x_ = (width_ - totalCircleWidth) / 2;
        for (int i = 0; i < tries_; i++) {

            x_ = (width_ - totalCircleWidth) / 2;
            for (int j = 0; j < codeColors_; j++) {
                playerTries_[i][j].setPositions(x_ + j * (this.circleRad_ * 2 + offset) + this.circleRad_, yPositions_[i + 2] + this.circleRad_ / 2);
            }
            // Dibujo los circulos de las pistas
            x_ = x_ + (codeColors_ - 1) * (this.circleRad_ * 2 + offset*2) + this.circleRad_ + offset*2 + this.circleRad_ * 2+10;

            int xUsed = x_;
            int half = codeColors_ / 2;
            int offsetY = this.circleRad_;
            int c = 0;
            for (int j = 0; j < codeColors_; j++) {
                if (j == half) {
                    offsetY = 0;
                    x_ = xUsed;
                    c = 0;
                }
                hints_[i][j].setPositions(x_ + c * (this.circleRad_ + offset*2) + this.circleRad_ / 2, yPositions_[i + 2] - offsetY + this.circleRad_ / 2 + this.circleRad_ / 2);

                c++;
            }
        }
        hintsPos_=x_;
    }

    @Override
    public void render(Graphics graph) {
        graph.setColor(0xFFad909c);
        graph.fillRectangle(0, yPositions_[12], sceneWidth_, heightSubdivisions_ + 8);
        graph.setFont(font2_);
        graph.setColor(0xFF272b2b);
        graph.drawText("Averigua el codigo", sceneWidth_ / 2, yPositions_[0] + 6);
        graph.setFont(font3_);
        graph.drawText("Te quedan " + (this.tries_ - acutalTry_) + " intentos", sceneWidth_ / 2, yPositions_[1] + 6);
        for (GameObject g : gameObjectsTable_) {
            g.render(graph);
        }
        int offsetY = 5;
        int posX = (sceneWidth_ - (sceneWidth_ - 8)) / 2;
        for (int i = 0; i < this.tries_; i++) {
            graph.setColor(0xFFad909c);
            graph.setStrokeWidth(2);
            graph.drawRoundRectangle(posX, yPositions_[i + 2] - (this.circleRad_ /2)-(offsetY/2)  , sceneWidth_ - 8, circleRad_ * 2 + offsetY, 10);
            graph.drawLine(50, yPositions_[i + 2]- (this.circleRad_ /2)-(offsetY/2)+4,50,(yPositions_[i + 2]- (this.circleRad_ /2)-(offsetY/2))+ circleRad_ * 2 + offsetY-4);
            graph.drawLine(hintsPos_-5, yPositions_[i + 2]- (this.circleRad_ /2)-(offsetY/2)+4,hintsPos_-5,(yPositions_[i + 2]- (this.circleRad_ /2)-(offsetY/2))+ circleRad_ * 2 + offsetY-4);

            graph.setFont(font2_);
            graph.setColor(0xFF472735);
            graph.drawText(i + 1 + "", 25, yPositions_[i + 2]);

            for (int j = 0; j < codeColors_; j++) {
                hints_[i][j].render(graph);
            }
        }
    }

    @Override
    public void init() {
        font2_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 20, true, false);
        font3_ = gm_.getIEngine().getGraphics().newFont("Lexendt.ttf", 17, false, false);
    }



    @Override
    public boolean handleInput(TouchEvent event) {
        for (GameObject g : gameObjectsTable_)
            if (g.handleInput(event))
                return true;
        return false;
    }

    public int getAcutalTry_() {
        return acutalTry_;
    }

    public void changeDaltonics(boolean dalt) {
        for (Circle c: gameObjectsTable_) {
            c.setDaltonics_(dalt);
        }
    }

    public void putNewColor(int id, int color) {
        for (int i = 0; i < this.codeColors_; i++) {
            if (!playerTries_[acutalTry_][i].hasColor()) {
                playerTries_[acutalTry_][i].setColor(color, id);
                playerTries_[acutalTry_][i].setImage(""+(id+1));
                this.gm_.putColorSolution(i, id);
                break;
            }
        }
    }

}
