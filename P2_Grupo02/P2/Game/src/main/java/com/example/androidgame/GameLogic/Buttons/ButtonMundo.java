package com.example.androidgame.GameLogic.Buttons;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Managers.GameManager;

public class ButtonMundo extends Button {
    protected Image lockImage_;
    Boolean locked_;
    protected int color_,lineColor_,textColor_;
    public ButtonMundo(String t, Font f, int backgroundColor, int textColor, int lineColor, int w, int h, int a, int x, int y, Sound buttonSound, Boolean locked, ButtonClickListener function) {
        super(t, f, backgroundColor,textColor,lineColor,w, h, a, x, y, buttonSound, function);
       Graphics graphics= GameManager.getInstance().getIEngine().getGraphics();
       lockImage_=graphics.newImage("lock.png");
       locked_=locked;
       color_=backgroundColor;
       textColor_=textColor;
       lineColor_=lineColor;

    }
    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_ + this.width_ / 2;
        yText = this.posY_ + this.height_ / 2;
        graph.setColor(this.lineColor_);
        graph.setStrokeWidth(5);
        graph.drawRoundRectangle(this.posX_ , this.posY_ , this.width_ , this.height_ , (int) (this.arc_));
        graph.setColor(this.color_);
        graph.fillRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, this.arc_);
        graph.setColor(this.textColor_);
        graph.setFont(this.font_);
        if(!locked_)
        graph.drawText(this.text_, xText, yText);
        else graph.drawImage(lockImage_,xText-this.width_ / 4,yText-this.width_ / 4,this.width_ / 2,this.width_ / 2);
    }

}
