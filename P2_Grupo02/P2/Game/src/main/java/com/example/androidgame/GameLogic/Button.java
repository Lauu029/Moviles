package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class Button extends GameObject {
    private String text_;
    private Font font_;
    private int color_;
    protected int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    protected Sound mySound_;
    private ButtonClickListener onClickFunction;

    Button(String t, Font f, int c, int w, int h, int a, int x, int y, Sound buttonSound, ButtonClickListener function) {
        this.text_ = t;
        this.font_ = f;
        this.color_ = c;
        this.width_ = w;
        this.height_ = h;
        this.arc_ = a;
        this.posX_ = x;
        this.posY_ = y;
        mySound_ =buttonSound;
        this.onClickFunction = function;
    }

    public void update(double time) {
    }

    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_ + this.width_ / 2;
        yText = this.posY_ + this.height_ / 2;
        graph.setColor(0XFF222222);
        graph.fillRoundRectangle(this.posX_ - 2, this.posY_ - 2, this.width_ + 4, this.height_ + 4, this.arc_);
        graph.setColor(this.color_);
        graph.fillRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, this.arc_);
        graph.setColor(0xFFFFFFFF);
        graph.setFont(this.font_);
        graph.drawText(this.text_, xText, yText);
    }

    @Override
    void init() {

    }

    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP){
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                onClickFunction.onClick();
                GameManager.getInstance_().getIEngine().getAudio().playSound(mySound_, 0);
                return true;
            }
        }
        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                GameManager.getInstance_().getIEngine().getAudio().stopSound(mySound_);
                return true;
            }

        }
        return false;
    }
}
