package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.TouchEvent;

/* Clase correspondiente a los círculos de colores que se usan para adivinar la combinación.
 * Extiende la clase Círculo y se encarga de avisar al GameManager cuando se ha pulsado uno para que
 * almacene el color y lo coloque en la posición correspondiente*/
public class SolutionCircle extends Circle {

    public SolutionCircle(String t, IFont f, int r, int x, int y, int row_) {
        super(t, f, r, x, y, row_);
    }

    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX - this.radius < event.x && this.posX + this.radius > event.x
                    && this.posY - this.radius < event.y && this.posY + this.radius > event.y) {
                gm.takeColor(this.color, this.idColor);
                return true;
            }
        return false;
    }
}
