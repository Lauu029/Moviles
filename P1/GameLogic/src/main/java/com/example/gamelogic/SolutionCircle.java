package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

/* Clase correspondiente a los círculos de colores que se usan para adivinar la combinación.
 * Extiende la clase Círculo y se encarga de avisar al GameManager cuando se ha pulsado uno para que
 * almacene el color y lo coloque en la posición correspondiente*/
public class SolutionCircle extends Circle {
    private ISound myButtonSound_;
    public SolutionCircle(String t, IFont f, int r, int x, int y, int row_) {
        super(t, f, r, x, y, row_);
        myButtonSound_=gm_.getInstance_().getIEngine().getAudio().newSound("circleSound.wav");
    }

    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX_ - this.radius_ < event.x && this.posX_ + this.radius_ > event.x
                    && this.posY_ - this.radius_ < event.y && this.posY_ + this.radius_ > event.y) {
                gm_.takeColor(this.color_, this.idColor_);
                gm_.getInstance_().getIEngine().getAudio().playSound(myButtonSound_,0);
                return true;
            }
        return false;
    }
}
