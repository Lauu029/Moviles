package com.example.androidgame.GameLogic;

import com.example.androidengine.Engine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ShopManager {
    //private Engine iEngine_;
    private static ShopManager instance_;
    private Map<String, Map<String, Boolean>> itemsState_; //Guarda la seccion a la que pertenece, su nombre y si se ha comprado
    private ShopManager() {
        // Constructor privado
    }
    public static ShopManager getInstance() {
        return instance_;
    }

    public static int init() {
        instance_ = new ShopManager();
        return 1;
    }

    void registerShopItem(String itemType,String itemName){

        if (!itemsState_.containsKey(itemType) ||
                (itemsState_.containsKey(itemType) && !itemsState_.get(itemType).containsKey(itemName))) {
            Map<String, Boolean> nuevoItem=new HashMap<>();
            nuevoItem.put(itemName, false);
            itemsState_.put(itemType,nuevoItem);
        }
    }
    void changeItemState(String itemType,String itemName,boolean bought){

    }

}
