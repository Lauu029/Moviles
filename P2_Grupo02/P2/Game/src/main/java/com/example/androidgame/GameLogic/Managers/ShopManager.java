package com.example.androidgame.GameLogic.Managers;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {
    private static ShopManager instance_;
    private static Map<String, Map<String, Boolean>> itemsState_; //Guarda la seccion a la que pertenece, su nombre y si se ha comprado
    private ShopManager() {
        // Constructor privado
    }
    public static ShopManager getInstance() {
        return instance_;
    }

    public static int init() {
        instance_ = new ShopManager();
        itemsState_= new HashMap<>();
        return 1;
    }
    public Map<String, Map<String, Boolean>> getItemsState(){
        return itemsState_;
    }
    public void registerShopItem(String typeId, String itemId){

        if (!itemsState_.containsKey(typeId)) {
            Map<String, Boolean> nuevoItem=new HashMap<>();
            itemsState_.put(typeId,nuevoItem);
        }
        if(itemsState_.containsKey(typeId) && !itemsState_.get(typeId).containsKey(itemId))
        {
            itemsState_.get(typeId).put(itemId, true);
        }
    }
    public void changeItemState(String typeId, String itemId, boolean locked){
        if(itemsState_.containsKey(typeId) && itemsState_.get(typeId).containsKey(itemId)){
            itemsState_.get(typeId).put(itemId,locked);
        }
    }
    public boolean itemIsLocked(String typeId, String itemId){
        boolean isLocked=true;
        if(itemsState_.containsKey(typeId) && itemsState_.get(typeId).containsKey(itemId)) {
           if(!itemsState_.get(typeId).get(itemId)){
               isLocked=false;
           }
        }
        return isLocked;
    }
}
