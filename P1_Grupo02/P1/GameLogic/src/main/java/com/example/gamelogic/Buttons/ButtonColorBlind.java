package com.example.gamelogic.Buttons;

import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.gamelogic.Managers.GameManager;

public class ButtonColorBlind extends ButtonImage {
    private IImage buttonImage_open_;

    public ButtonColorBlind(String eyeOpenImage, String eyeClosedImage, int w, int h, int x, int y, ISound buttonSound, ButtonClickListener function) {
        super(eyeClosedImage ,w,h,x,y, GameManager.getInstance_().getIEngine().getAudio().newSound("daltonicsButton.wav"),function);
        buttonImage_open_ = GameManager.getInstance_().getIEngine().getGraphics().newImage(eyeOpenImage);
    }

    @Override
    public void render(IGraphics graph) {
        if (GameManager.getInstance_().getDaltonic())
            graph.drawImage(buttonImage_open_, posX_, posY_, width_, height_);
        else graph.drawImage(buttonImage_, posX_, posY_, width_, height_);
    }
}

