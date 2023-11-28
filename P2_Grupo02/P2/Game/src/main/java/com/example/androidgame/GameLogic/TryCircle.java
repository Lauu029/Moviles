package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;

/* Clase correspondiente a los círculos que forman la solución. Extiende la clase círculo.
 * id_row almacena la posición en la fila en la que está cada círculo para poner la solución y
 * has_color indica al render si tiene que poner el círculo pequeño en medio del círculo para
 * indicar posición vacía o no */
public class TryCircle extends Circle {
    private int idRow_;
    private boolean hasColor_;

    public TryCircle(String t, Font f, int r, int x, int y, int row_, int id) {
        super(t, f, r, x, y, row_);
        this.idRow_ = id;
        this.hasColor_ = false;
    }

    public void render(Graphics graph) {
        super.render(graph);
        if (!this.hasColor_) {
            graph.setColor(0Xff5c3947);
            graph.drawCircle(this.posX_, this.posY_, this.radius_ / 3);
        }
    }

    /*Controla que cuando se pulsa un círculo, este vacía su color y su posición en el array
    de intento temporal. Controla que solo se pueda pulsar si el jugador está en el intento en el
    que está cada círculo*/
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX_ - this.radius_ < event.x && this.posX_ + this.radius_ > event.x
                    && this.posY_ - this.radius_ < event.y && this.posY_ + this.radius_ > event.y) {
                if (this.hasColor_ && this.row_ == this.gameTry_) {
                    this.color_ = 0xFFad909c;
                    this.hasColor_ = false;
                    this.text_ ="";
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
}
