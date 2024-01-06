package com.example.gamelogic.Buttons;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.ISound;

public class ButtonText extends Button {
    private String text_;
    int color_;

    public ButtonText(String t, IFont f, int c, int w, int h, int x, int y, ISound buttonSound, ButtonClickListener function) {
        super(t, f, 0, w, h, 0, x, y, buttonSound, function);
        this.text_ = t;
        this.color_=c;
    }
    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX_ + this.width_ / 2;
        yText = this.posY_ + this.height_ / 2;
        graph.setColor(color_);
        graph.drawText(this.text_, xText, yText);

    }
}
