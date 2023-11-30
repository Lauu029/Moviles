package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class Button extends GameObject {
    protected String text_;
    protected Font font_;
    protected int color_,lineColor_,textColor_;
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
        mySound_ = buttonSound;
        this.onClickFunction = function;
        this.lineColor_=0XFF222222;
        this.textColor_= 0xFFFFFFFF;
    }

    public void update(double time) {
    }

    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_ + this.width_ / 2;
        yText = this.posY_ + this.height_ / 2;
        graph.setColor(lineColor_);
        graph.fillRoundRectangle(this.posX_ - 2, this.posY_ - 2, this.width_ + 4, this.height_ + 4, this.arc_);
        graph.setColor(this.color_);
        graph.fillRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, this.arc_);
        graph.setColor(textColor_);
        graph.setFont(this.font_);
        graph.drawText(this.text_, xText, yText);
    }

    @Override
    void init() {
    }

    public boolean handleInput(TouchEvent event) {
        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
               //GameManager.getInstance_().getIEngine().getAudio().stopSound(mySound_);
                onClickFunction.onClick();
                GameManager.getInstance_().getIEngine().getAudio().playSound(mySound_, 0);
                return true;
            }
        }
        return false;
    }
    public void setButtonColor(int color){
        this.color_=color;
    }
    public void setButtonTextColor(int textColor){
        this.textColor_=textColor;
    }
    public void setButtonLineColor(int lineColor){
        this.lineColor_=lineColor;
    }
}
