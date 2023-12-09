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
    void registerShopItem(String itemType,String itemName){

        if (!itemsState_.containsKey(itemType)) {
            Map<String, Boolean> nuevoItem=new HashMap<>();
            itemsState_.put(itemType,nuevoItem);

        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+itemType);}
        if(itemsState_.containsKey(itemType) && !itemsState_.get(itemType).containsKey(itemName))
        {
            itemsState_.get(itemType).put(itemName, false);
        }
        else{ Log.d("SHOP","Ya contiene la seccion: "+itemType+" y el objeto "+itemName);}
    }
    void changeItemState(String itemType,String itemName,boolean bought){
        if(itemsState_.containsKey(itemType) && itemsState_.get(itemType).containsKey(itemName)){
            itemsState_.get(itemType).put(itemName,bought);
            Log.d("SHOP","Has comprado en la seccion: "+itemType+" y el objeto "+itemName);
        }
        else{
            Log.d("SHOP","No contiene "+itemType+" y el objeto "+itemName+", no lo puedes comprar");
        }
    }
    void setItemsState(Map<String,Map<String,Boolean>> nuevoMapa){
        itemsState_=new HashMap<>(nuevoMapa);
        Log.d("GUARDADO","Comprobando el estado de itemsState");
    }

}
