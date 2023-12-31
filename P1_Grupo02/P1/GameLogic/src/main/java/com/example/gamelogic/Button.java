package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

public class Button implements IGameObject {
    private String text_;
    private IFont font_;
    protected IScene scene_ = null;
    private int color_;
    protected int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    private SceneNames sceneName_;
    protected ISound mySound_;
    private ButtonClickListener onClickFunction;
    Button(String t, IFont f, int c, int w, int h, int a, int x, int y, ISound buttonSound, ButtonClickListener function) {
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

    @Override
    public void update(double time) {
    }

    @Override
    public void render(IGraphics graph) {
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
    public void init() {
    }

    @Override
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
}
