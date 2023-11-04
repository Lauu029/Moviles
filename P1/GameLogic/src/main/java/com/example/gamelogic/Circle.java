package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

/* Clase que controla los círculos de forma genérica, con los atributos comunes entre todos
* los tipos de círculos y una instancia del Game Manager para acceder rápidamente a sus métodos
* */
public class Circle implements IGameObject {
    protected String text_;
    protected IFont font_;
    protected int color_;
    protected int radius_;
    protected int posX_, posY_;
    protected int idColor_;
    protected int row_, gameTry_;
    protected boolean isDaltonics_;
    protected GameManager gm_;

    public Circle(String t, IFont f, int r, int x, int y, int row_) {
        this.font_ = f;
        this.text_ = t;
        this.color_ = 0xFFad909c;
        this.posX_ = x;
        this.posY_ = y;
        this.radius_ = r;
        this.row_ = row_;
        this.gm_ = GameManager.getInstance_();
        this.isDaltonics_ = gm_.getDaltonic();
    }

    public void setColor_(int color_) {
        this.color_ = color_;
    }

    public void setPositions(int x, int y) {
        this.posX_ = x;
        this.posY_ = y;
    }

    @Override
    public void update(double time) {
    }

    //Render de los círculos dependiendo de si tienen que poner números o no encima de los círculos
    @Override
    public void render(IGraphics graph) {
        graph.setColor(this.color_);
        graph.drawCircle(this.posX_, this.posY_, this.radius_);
        if (this.isDaltonics_) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font_);
            graph.drawText(this.text_, this.posX_, this.posY_ -this.radius_ /3);
        }
    }

    @Override
    public void init() {
    }

    public void setIdColor_(int id) {
        this.idColor_ = id;
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        return true;
    }
    /*Actualiza el intento del juego para ver si se puede pulsar o no cada círculo
    * y para ver en que círculos se pueden colorear*/
    public void setGameTry_(int t) {
        this.gameTry_ = t;
    }

    public void setDaltonics_(boolean d) {
        this.isDaltonics_ = d;
    }
}