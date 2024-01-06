package com.example.gamelogic.Circles;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Managers.GameManager;

/* Clase correspondiente a los círculos que forman la solución. Extiende la clase círculo.
 * id_row almacena la posición en la fila en la que está cada círculo para poner la solución y
 * has_color indica al render si tiene que poner el círculo pequeño en medio del círculo para
 * indicar posición vacía o no */
public class TryCircle extends Circle {
    private int idRow_;
    private boolean hasColor_;

    public TryCircle(String t, IFont f, int r, int x, int y, int row_, int id) {
        super(t, f, r, x, y, row_);
        this.idRow_ = id;
        this.hasColor_ = false;

    }

    public void render(IGraphics graph) {
        graph.setColor(this.color_);
        nuevoAspecto=gm.getNuevoAspecto();
        if(!nuevoAspecto)
            graph.drawCircle(this.posX_, this.posY_+translateY_, this.radius_);
        else graph.fillRoundRectangle(posX_-radius_/2,posY_+translateY_-radius_/2,radius_,radius_,0);
        if (!this.hasColor_) {
            graph.setColor(0Xff5c3947);
            if(!nuevoAspecto)
                graph.drawCircle(this.posX_, this.posY_+translateY_, this.radius_ / 3);
            else graph.fillRoundRectangle(posX_-((radius_/3)/2),posY_+translateY_-((radius_/3)/2),radius_/3,radius_/3,0);
        } else if (this.isDaltonics_) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font_);
            graph.drawText(this.text_, this.posX_, this.posY_ + translateY_);
        }
    }

    /*Controla que cuando se pulsa un círculo, este vacía su color y su posición en el array
    de intento temporal. Controla que solo se pueda pulsar si el jugador está en el intento en el
    que está cada círculo*/
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX_ - this.radius_ < event.x && this.posX_ + this.radius_ > event.x
                    && this.posY_+translateY_ - this.radius_ < event.y && this.posY_ +translateY_+ this.radius_ > event.y)  {
                if (this.hasColor_ && this.row_ == this.gameTry_) {
                    this.color_ = 0xFFad909c;
                    this.hasColor_ = false;
                    this.text_ = "";
                    GameManager.getInstance_().putColorSolution(this.idRow_, -1);
                }
                return true;
            }
        return false;
    }

    public void setColor(int color, int id) {
        super.setColor_(color);
        this.idColor_ = id;
        this.text_ = Integer.toString(idColor_);
        this.hasColor_ = true;
    }

    public boolean hasColor() {
        return this.hasColor_;
    }
    public int getId(){return idColor_;}
}
