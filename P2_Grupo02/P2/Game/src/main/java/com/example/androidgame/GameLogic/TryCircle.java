package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Managers.GameManager;

/* Clase correspondiente a los círculos que forman la solución. Extiende la clase círculo.
 * id_row almacena la posición en la fila en la que está cada círculo para poner la solución y
 * has_color indica al render si tiene que poner el círculo pequeño en medio del círculo para
 * indicar posición vacía o no */
public class TryCircle extends Circle {
    private int idRow_;
    private boolean hasColor_;

    public TryCircle(String t, Font f, int r, int x, int y, int row_, int id,boolean world) {
        super(t, f, r, x, y, row_,world);
        Log.d("MainActivity", "fila: "+row_);
        this.idRow_ = id;
        this.hasColor_ = false;
    }
    @Override
    public void render(Graphics graph) {
        graph.setColor(this.color_);
        if (image_ != null && this.hasColor_)
            graph.drawImage(image_, this.posX_ - (radius_), this.posY_ - (radius_)+translateY_, this.radius_ * 2, this.radius_ * 2);
        else graph.drawCircle(this.posX_, this.posY_+translateY_, this.radius_);
        if (!this.hasColor_) {
            graph.setColor(0Xff454545);
            graph.drawCircle(this.posX_, this.posY_+translateY_, this.radius_ / 3);
        }else if (this.isDaltonics_ && image_ == null) {
            graph.setColor(0xFF000000);
            graph.setFont(this.font_);
            graph.drawText(this.text_, this.posX_, this.posY_+translateY_);
        }
    }

    /*Controla que cuando se pulsa un círculo, este vacía su color y su posición en el array
    de intento temporal. Controla que solo se pueda pulsar si el jugador está en el intento en el
    que está cada círculo*/
    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN)
            if (this.posX_ - this.radius_ < event.x && this.posX_ + this.radius_ > event.x
                    && this.posY_+translateY_ - this.radius_ < event.y && this.posY_ +translateY_+ this.radius_ > event.y) {
                Log.d("MainActivity", "row: "+this.row_+" gametry: "+this.gameTry_);
                if (this.hasColor_ && this.row_ == this.gameTry_) {
                    this.color_ = 0xFFad909c;
                    this.hasColor_ = false;
                    this.text_ ="";
                    GameManager.getInstance().putColorSolution(this.idRow_, -1);
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
        Log.d("MainActivity","Color: "+hasColor_+" intento: "+gameTry_+ " fila: "+row_);
    }

    public boolean hasColor() {
        return this.hasColor_;
    }
}
