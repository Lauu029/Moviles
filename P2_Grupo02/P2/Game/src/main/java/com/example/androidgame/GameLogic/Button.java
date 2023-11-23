package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;
import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.IScene;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;
enum ButtonType{
    DALTONICS,
    IMAGE,
    LEVEL,
    REWARD
}
public class Button extends GameObject {
    private String text_;
    private Font font_;
    protected IScene scene_ = null;
    private int color_;
    private int width_ = 0, height_ = 0, posX_ = 0, posY_ = 0, arc_ = 0;
    private SceneNames sceneName_;
    private Sound mySound_;
    private ButtonType type_;
    Button(String t, Font f, int c, int w, int h, int a, int x, int y, SceneNames sceneNames, Sound buttonSound) {
        this.text_ = t;
        this.font_ = f;
        this.color_ = c;
        this.width_ = w;
        this.height_ = h;
        this.arc_ = a;
        this.posX_ = x;
        this.posY_ = y;
        sceneName_ = sceneNames;
        mySound_ =buttonSound;
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

    public void init() {
    }

    void actionButton() {
    }

    public boolean handleInput(TouchEvent event) {
        if(event.type== TouchEvent.TouchEventType.TOUCH_UP){
            if (this.posX_ < event.x && this.posX_ + this.width_ > event.x
                    && this.posY_ < event.y && this.posY_ + this.height_ > event.y) {
                Engine engine_ = GameManager.getInstance_().getIEngine();
                int sceneWidth = GameManager.getInstance_().getwidth();
                int sceneHeight = GameManager.getInstance_().getHeight_();
                engine_.getAudio().playSound(mySound_,0);
                switch (sceneName_) {
                    case GAME:
                        scene_ = new GameScene(engine_, sceneWidth, sceneHeight);
                        break;
                    case LEVEL:
                        scene_ = new LevelScene(engine_, sceneWidth, sceneHeight);
                        break;
                }
                actionButton();
                GameManager.getInstance_().changeScene(scene_);
                return true;
            }
        }
        return false;
    }
}
