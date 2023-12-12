package com.example.androidgame.GameLogic;

import com.example.androidengine.Sound;

public class ShopItem extends ButtonImage{

    private int sectionId_;
    private int itemId_;
    private boolean unlocked_;
    private int price_;
    public ShopItem(String image, int w, int h, int x, int y, Sound buttonSound, ButtonClickListener function,
                     int sectionId,int itemId,int price) {
        super(image, w, h, x, y, buttonSound, function);
        unlocked_=false;
        sectionId_=sectionId;
        itemId_=itemId;
        price_=price;
    }
    public int getPrice(){
        return price_;
    }
    public void setPrice(int price){
        price_=price;
    }

    public void buyItem(){
        unlocked_=true;
        GameManager.getInstance().addCoins(-price_);
    }
    public boolean isUnlocked(){
        return unlocked_;
    }

}
