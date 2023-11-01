package com.example.gamelogic;

import com.example.engine.IColor;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

public class Button implements IGameObject {
    private String text;
    private IFont font;
    IScene scene = null;
    private boolean pressed_;
    private int color;
    private int width = 0, height = 0, posX = 0, posY = 0, arc = 0;
    SceneNames sceneName_;

    Button(String t, IFont f, int c, int w, int h, int a, int x, int y, SceneNames sceneNames) {
        this.text = t;
        this.font = f;
        this.color = c;
        this.width = w;
        this.height = h;
        this.arc = a;
        this.posX = x;
        this.posY = y;
        sceneName_ = sceneNames;
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(IGraphics graph) {
        int xText, yText;
        xText = this.posX + this.width / 2;
        yText = this.posY + this.height / 2;
        graph.setColor(0XFF222222);
        graph.fillRoundRectangle(this.posX - 2, this.posY - 2, this.width + 4, this.height + 4, this.arc);
        graph.setColor(this.color);
        graph.fillRoundRectangle(this.posX, this.posY, this.width, this.height, this.arc);
        graph.setColor(0xFFFFFFFF);
        graph.setFont(this.font);
        graph.drawText(this.text, xText, yText);
    }

    @Override
    public void init() {

    }

    void actionButton() {

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        if (this.posX < event.x && this.posX + this.width > event.x
                && this.posY < event.y && this.posY + this.height > event.y) {
            IEngine engine_ = GameManager.getInstance().getIEngine();
            int sceneWidth = GameManager.getInstance().getwidth();
            int sceneHeight = GameManager.getInstance().getHeight();
            switch (sceneName_) {
                case GAME:
                    scene = new GameScene(engine_, sceneWidth, sceneHeight);
                    break;

                case LEVEL:
                    scene = new LevelScene(engine_, sceneWidth, sceneHeight);
                    break;
            }
            actionButton();
            GameManager.getInstance().changeScene(scene);

            return true;
        }
        return false;
    }
}
