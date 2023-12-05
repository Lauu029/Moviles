package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class ButtonImage extends Button {
    protected Image buttonImage_;
    protected Image overlayImage;

    ButtonImage(String image, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super("", null, AssetsManager.getInstance().getButtonColor(),
                AssetsManager.getInstance().getTextColor(), AssetsManager.getInstance().getLineColor(), w, h, 0, x, y, buttonSound, function);
        buttonImage_ = GameManager.getInstance().getIEngine().getGraphics().newImage(image);
        overlayImage = null;
    }

    void init() {
    }

    @Override
    public void render(Graphics graph) {
        if (active) {
            graph.drawImage(buttonImage_, this.posX_, this.posY_, width_, height_);
            if (overlayImage != null)
                graph.drawImage(overlayImage, this.posX_+width_/4, this.posY_+height_/4, width_/2, height_/2);
        }
    }

    public void addOverlayImage(Image img) {
        overlayImage = img;
    }

    public boolean handleInput(TouchEvent event) {
        boolean input = super.handleInput(event);
        if (input)
            deleteOverlayImage();
        return input;
    }

    public void deleteOverlayImage() {
        overlayImage = null;
    }
}