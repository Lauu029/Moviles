package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

/* Clase correspondiente a los círculos de colores que se usan para adivinar la combinación.
 * Extiende la clase Círculo y se encarga de avisar al GameManager cuando se ha pulsado uno para que
 * almacene el color y lo coloque en la posición correspondiente*/
public class SolutionCircle extends Circle {
    private Sound myButtonSound_;
    public SolutionCircle(String t, Font f, int r, int x, int y, int row_,boolean world) {
        super(t, f, r, x, y, row_,world);
        myButtonSound_=GameManager.getInstance().getIEngine().getAudio().newSound("circleSound.wav");
    }

    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_UP)
            if (this.posX_ - this.radius_ < event.x && this.posX_ + this.radius_ > event.x
                    && this.posY_ - this.radius_ < event.y && this.posY_ + this.radius_ > event.y) {
                GameManager.getInstance().getIEngine().getAudio().stopSound(myButtonSound_);
                GameManager.getInstance().takeColor(this.color_, this.idColor_);
                GameManager.getInstance().getIEngine().getAudio().playSound(myButtonSound_,0);
                return true;
            }
        return false;
    }
}
