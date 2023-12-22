package com.example.androidgame.GameLogic;

import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidengine.TouchEvent;

public class ShopItem extends ButtonImage{

    private String sectionId_;
    private String itemId_;
    private int price_;

    private Sound shopingSound_;
    private Image blockedImage_;
    public ShopItem(String image, int w, int h, int x, int y, Sound buttonSound,
                     String sectionName,String itemName,int price,Image blockedImage) {
        super(image, w, h, x, y, null, null);
        sectionId_=sectionName;
        itemId_=itemName;
        price_=price;
        blockedImage_=blockedImage;
        shopingSound_=buttonSound;

    }

    public boolean buyItem(){
        boolean canBuy=false;
        int currCoins = GameManager.getInstance().getCoins();
        if(currCoins-price_>=0 && ShopManager.getInstance().itemIsLocked(sectionId_,itemId_)){
            GameManager.getInstance().addCoins(-price_);
            ShopManager.getInstance().changeItemState(sectionId_,itemId_,false);
            deleteOverlayImage();
            //Solo suena si de verdad lo puede comprar
            GameManager.getInstance().getIEngine().getAudio().playSound(shopingSound_, 0);
            canBuy=true;
        }
        return canBuy;
    }
    @Override
    public void render(Graphics graph){
        super.render(graph);
        if (ShopManager.getInstance().itemIsLocked(sectionId_,itemId_))
            graph.drawImage(blockedImage_, this.posX_+width_/4, this.posY_+height_/4, width_/2, height_/2);
    }

    public void deleteOverlayImage() {
        blockedImage_.setVisibility(false);
    }

}
