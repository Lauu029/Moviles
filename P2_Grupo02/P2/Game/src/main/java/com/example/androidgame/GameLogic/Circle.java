package com.example.androidgame.GameLogic;


import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.TouchEvent;

/* Clase que controla los círculos de forma genérica, con los atributos comunes entre todos
 * los tipos de círculos y una instancia del Game Manager para acceder rápidamente a sus métodos
 * */
public class Circle extends GameObject {
    protected String text_;
    protected Font font_;
    protected int color_;
    protected int radius_;
    protected int posX_, posY_;
    protected int idColor_;
    protected int row_, gameTry_;
    protected boolean isDaltonics_;
    protected static GameManager gm_;
    protected Image image_;
    private EnumTheme tematica_;

    public Circle(String t, Font f, int r, int x, int y, int row_) {
        this.font_ = f;
        this.text_ = t;
        this.color_ = 0xFFad909c;
        this.posX_ = x;
        this.posY_ = y;
        this.radius_ = r;
        this.row_ = row_;
        this.isDaltonics_ = GameManager.getInstance().getDaltonic();
        this.tematica_ = AssetsManager.getInstance().getCirleTheme();
    }

    public void setColor_(int color_) {
        this.color_ = color_;
    }

    public void setImage(String image) {
        if (tematica_ != EnumTheme.DEFAULT) {
            String path = tematica_.getPath();
            this.image_ = GameManager.getInstance().getIEngine().getGraphics().newImage(path + image + ".png");
        }
    }

    public void setPositions(int x, int y) {
        this.posX_ = x;
        this.posY_ = y;
    }

    public void update(double time) {
    }

    //Render de los círculos dependiendo de si tienen que poner números o no encima de los círculos
    public void render(Graphics graph) {
        graph.setColor(this.color_);
        //
        if (image_ != null && tematica_ != EnumTheme.DEFAULT)
            graph.drawImage(image_, this.posX_ - (radius_), this.posY_ - (radius_), this.radius_ * 2, this.radius_ * 2);
        else graph.drawCircle(this.posX_, this.posY_, this.radius_);
        if (this.isDaltonics_) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font_);
            graph.drawText(this.text_, this.posX_, this.posY_);
        }
    }

    public void init() {
    }

    public void setIdColor_(int id) {
        this.idColor_ = id;
    }

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