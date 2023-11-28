package com.example.androidgame.GameLogic;

import com.example.androidengine.Sound;

public class ShopItems extends ButtonImage{
    private Boolean unlocked;
    private int price;
    ShopItems(String image, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function) {
        super(image, w, h, x, y, buttonSound, function);
    }
}

