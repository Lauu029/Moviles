package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;

/* Clase correspondiente a los círculos que forman la solución. Extiende la clase círculo.
 * id_row almacena la posición en la fila en la que está cada círculo para poner la solución y
 * has_color indica al render si tiene que poner el círculo pequeño en medio del círculo para
 * indicar posición vacía o no */
public class TryCircle extends Circle {
    private int id_row;
    private boolean has_color;

    public TryCircle(String t, IFont f, int r, int x, int y, int row_, int id) {
        super(t, f, r, x, y, row_);
        this.id_row = id;
        this.has_color = false;
    }

    public void render(IGraphics graph) {
        super.render(graph);
        if (!this.has_color) {
            graph.setColor(0Xff5c3947);
            graph.drawCircle(this.posX, this.posY, this.radius / 3);
        }
    }

    /*Controla que cuando se pulsa un círculo, este vacía su color y su posición en el array
    de intento temporal. Controla que solo se pueda pulsar si el jugador está en el intento en el
    que está cada círculo*/
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                    && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
                if (this.has_color && this.row == this.game_try) {
                    this.color = 0xFFad909c;
                    this.has_color = false;
                    this.text="";
                    gm.putColorSolution(this.id_row, -1);
                }
                return true;
            }
        return false;
    }

    public void setColor(int color, int id) {
        super.setColor(color);
        this.id_color = id;
        this.text = Integer.toString(id_color);
        this.has_color = true;
    }

    public boolean hasColor() {
        return this.has_color;
    }
}
