package com.example.gamelogic.Buttons;

import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.gamelogic.Managers.GameManager;

public class ButtonImage extends Button {
    protected IImage buttonImage_;

    public ButtonImage(String image, int w, int h, int x, int y, ISound buttonSound, ButtonClickListener function) {
        super("", null, 0, w, h, 0, x, y, buttonSound, function);
        buttonImage_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(image);
    }

    public void init() {
    }

    @Override
    public void render(IGraphics graph) {
        graph.drawImage(buttonImage_, this.posX_, this.posY_, width_, height_);
    }
}

