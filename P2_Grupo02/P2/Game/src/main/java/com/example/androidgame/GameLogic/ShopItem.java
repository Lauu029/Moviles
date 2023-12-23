package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Font;
import com.example.androidengine.Graphics;
import com.example.androidengine.Image;
import com.example.androidengine.Sound;
import com.example.androidgame.GameLogic.Buttons.ButtonImage;

public class ShopItem extends ButtonImage {

    private String sectionId_;
    private String itemId_;
    private int price_;
    private Font itemFont_;
    private Sound shopingSound_;
    private Image blockedImage_;
    private Image coinImage_;
    public ShopItem(String image, int w, int h, int x, int y, Sound buttonSound,
                     String sectionName,String itemName,int price,Image blockedImage,
                    Image coinImage, Font font) {
        super(image, w, h, x, y, null, null);
        sectionId_=sectionName;
        itemId_=itemName;
        price_=price;
        this.blockedImage_=blockedImage;
        shopingSound_=buttonSound;
        itemFont_=font;
        coinImage_=coinImage;
    }
    public boolean buyItem(){
        boolean canBuy=false;
        int currCoins = GameManager.getInstance().getCoins();
        if(currCoins-price_>=0 && ShopManager.getInstance().itemIsLocked(sectionId_,itemId_)){
            GameManager.getInstance().addCoins(-price_);
            ShopManager.getInstance().changeItemState(sectionId_,itemId_,false);
            //Solo suena si de verdad lo puede comprar
            GameManager.getInstance().getIEngine().getAudio().playSound(shopingSound_, 0);
            canBuy=true;
        }else
        {
            Log.d("BUY","No tienes dinero o ya esta comprado");
        }
        return canBuy;
    }

    public void render(Graphics graph){
        super.render(graph);
        if (active) {
            if (ShopManager.getInstance().itemIsLocked(sectionId_,itemId_))
            {
                graph.drawImage( this.blockedImage_, this.posX_+width_/4, this.posY_+height_/4, width_/2, height_/2);
                graph.setFont(itemFont_);
                graph.drawText(String.valueOf(price_),(this.posX_+width_/2) - 10,this.posY_+height_+20);
                graph.drawImage( this.coinImage_, this.posX_+width_/2,this.posY_+height_+10,30,30);
            }
        }
    }


}
