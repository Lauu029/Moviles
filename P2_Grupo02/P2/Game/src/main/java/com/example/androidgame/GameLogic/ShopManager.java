package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ShopManager {
    //private Engine iEngine_;
    private static ShopManager instance_;
    private static Map<Integer, Map<Integer, Boolean>> itemsState_; //Guarda la seccion a la que pertenece, su nombre y si se ha comprado
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
    Map<Integer, Map<Integer, Boolean>> getItemsState(){
        return itemsState_;
    }
    void registerShopItem( Integer typeId,Integer itemId){

        if (!itemsState_.containsKey(typeId)) {
            Map<Integer, Boolean> nuevoItem=new HashMap<>();
            itemsState_.put(typeId,nuevoItem);

        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+typeId);}
        if(itemsState_.containsKey(itemId) && !itemsState_.get(typeId).containsKey(itemId))
        {
            itemsState_.get(typeId).put(itemId, false);
        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+typeId+" y el objeto "+itemId);}
    }
    void changeItemState(Integer typeId,Integer itemId,boolean bought){
        if(itemsState_.containsKey(typeId) && itemsState_.get(typeId).containsKey(itemId)){
            itemsState_.get(typeId).put(itemId,bought);
            Log.d("SHOP","Has comprado en la seccion: "+typeId+" y el objeto "+itemId);
        }
        else{
            Log.d("SHOP","No contiene "+typeId+" y el objeto "+itemId+", no lo puedes comprar");
        }
    }

}
