package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class ShopItem extends ButtonImage{

    private int sectionId_;
    private int itemId_;
    private boolean unlocked_;
    private int price_;
    protected Image overlayImage;

    public ShopItem(String image, int w, int h, int x, int y, Sound buttonSound,
                     int sectionId,int itemId,int price) {
        super(image, w, h, x, y, buttonSound, null);
        unlocked_=false;
        sectionId_=sectionId;
        itemId_=itemId;
        price_=price;
        overlayImage = null;

    }
    public int getPrice(){
        return price_;
    }
    public void setPrice(int price){
        price_=price;
    }

    public void buyItem(){

        int currCoins = GameManager.getInstance().getCoins();
        if(currCoins-price_>=0){
            GameManager.getInstance().addCoins(-price_);
            unlocked_ = true;
            ShopManager.getInstance().changeItemState(sectionId_, itemId_, unlocked_);
            deleteOverlayImage();
        }

    }
    public boolean isUnlocked(){
        return unlocked_;
    }
    @Override
    public void render(Graphics graph){
        super.render(graph);
        if (overlayImage != null)
            graph.drawImage(overlayImage, this.posX_+width_/4, this.posY_+height_/4, width_/2, height_/2);
    }
    public void addOverlayImage(Image img) {
        overlayImage = img;
    }
    public void deleteOverlayImage() {
        overlayImage = null;
    }

}
