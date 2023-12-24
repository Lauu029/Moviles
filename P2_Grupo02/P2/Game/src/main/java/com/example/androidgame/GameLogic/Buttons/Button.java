package com.example.androidgame.GameLogic.Buttons;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
import com.example.androidgame.GameLogic.Managers.AssetsManager;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.GameObject;

public class Button extends GameObject {
    protected String text_;
    protected Font font_;
    protected int color_, lineColor_, textColor_;
    protected int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    protected Sound mySound_;
    private ButtonClickListener onClickFunction;
    protected boolean active = true;

    public Button(String t, Font f, int backgroundColor, int textColor, int lineColor, int w, int h, int a, int x, int y, Sound buttonSound, ButtonClickListener function) {
        this.text_ = t;
        this.font_ = f;
        this.color_ = backgroundColor;
        this.width_ = w;
        this.height_ = h;
        this.arc_ = a;
        this.posX_ = x;
        this.posY_ = y;
        mySound_ = buttonSound;
        this.onClickFunction = function;
        this.textColor_ = textColor;
        this.lineColor_ = lineColor;
    }

    public void update(double time) {
        if(this.color_!= AssetsManager.getInstance().getButtonColor())
            this.color_ = AssetsManager.getInstance().getButtonColor();
        if(this.lineColor_!= AssetsManager.getInstance().getLineColor())
            this.lineColor_ = AssetsManager.getInstance().getLineColor();
    }

    public void render(Graphics graph) {
        if (active) {
            int xText, yText;
            xText = this.posX_ + this.width_ / 2;
            yText = this.posY_ + this.height_ / 2;

            graph.setColor(this.color_);
            graph.fillRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, this.arc_);
            graph.setColor(textColor_);
            graph.setFont(this.font_);
            graph.drawText(this.text_, xText, yText);
            graph.setColor(lineColor_);
            graph.setStrokeWidth(3);
            graph.drawRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, (int) (this.arc_));
        }
    }

    @Override
    public void init() {
    }

    public boolean handleInput(TouchEvent event) {
        if (active) {
            if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
                if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                        && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                    //GameManager.getInstance_().getIEngine().getAudio().stopSound(mySound_);
                    if (onClickFunction != null) {
                        onClickFunction.onClick();
                        if(mySound_!=null)
                        {
                            GameManager.getInstance().getIEngine().getAudio().playSound(mySound_, 0);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setButtonColor(int color) {
        this.color_ = color;
    }

    public void setButtonTextColor(int textColor) {
        this.textColor_ = textColor;
    }

    public void setButtonLineColor(int lineColor) {
        this.lineColor_ = lineColor;
    }

    public void changeActive(boolean active) {
        this.active = active;
    }
    public boolean isActive(){ return active;}

    public void setAction(ButtonClickListener b) {
        this.onClickFunction = b;
    }
}
