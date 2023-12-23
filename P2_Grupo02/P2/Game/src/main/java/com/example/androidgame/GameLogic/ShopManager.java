package com.example.androidgame.GameLogic;

import android.util.Log;

import com.example.androidengine.Engine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ShopManager {
    //private Engine iEngine_;
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
    Map<String, Map<String, Boolean>> getItemsState(){
        return itemsState_;
    }
    public void registerShopItem(String typeId, String itemId){

        if (!itemsState_.containsKey(typeId)) {
            Map<String, Boolean> nuevoItem=new HashMap<>();
            itemsState_.put(typeId,nuevoItem);
        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+typeId);}
        if(itemsState_.containsKey(typeId) && !itemsState_.get(typeId).containsKey(itemId))
        {
            itemsState_.get(typeId).put(itemId, true);
        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+typeId+" y el objeto "+itemId);}
    }
    void changeItemState(String typeId,String itemId,boolean locked){
        if(itemsState_.containsKey(typeId) && itemsState_.get(typeId).containsKey(itemId)){
            itemsState_.get(typeId).put(itemId,locked);
            Log.d("COMPRA","Has comprado en la seccion: "+typeId+" y el objeto "+itemId);
        }
        else{
            Log.d("SHOP","No contiene "+typeId+" y el objeto "+itemId+", no lo puedes comprar");
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
