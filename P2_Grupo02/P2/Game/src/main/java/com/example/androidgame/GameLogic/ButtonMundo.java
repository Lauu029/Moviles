package com.example.androidgame.GameLogic;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;

public class ButtonMundo extends Button{
    protected Image lockImage_;
    Boolean locked_;
    private int color_,lineColor_,textColor_;
    ButtonMundo(String t, Font f, int c, int w, int h, int a, int x, int y, Sound buttonSound, ButtonClickListener function,Boolean locked) {
        super(t, f, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(),AssetsManager.getInstance().getLineColor(),
                w, h, a, x, y, buttonSound, function);
       Graphics graphics= GameManager.getInstance().getIEngine().getGraphics();
       lockImage_=graphics.newImage("lock.png");
       locked_=locked;

    }
    public void render(Graphics graph) {
        int xText, yText;
        xText = this.posX_ + this.width_ / 2;
        yText = this.posY_ + this.height_ / 2;
        graph.setColor(this.lineColor_);
        graph.fillRoundRectangle(this.posX_ - 2, this.posY_ - 2, this.width_ + 4, this.height_ + 4, this.arc_);
        graph.setColor(this.color_);
        graph.fillRoundRectangle(this.posX_, this.posY_, this.width_, this.height_, this.arc_);
        graph.setColor(this.textColor_);
        graph.setFont(this.font_);
        if(!locked_)
        graph.drawText(this.text_, xText, yText);
        else graph.drawImage(lockImage_,xText-this.width_ / 4,yText-this.width_ / 4,this.width_ / 2,this.width_ / 2);
    }

}
