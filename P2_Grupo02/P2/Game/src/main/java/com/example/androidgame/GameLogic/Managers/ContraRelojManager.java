package com.example.androidgame.GameLogic.Managers;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;
import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Utils.SaveData;

public class ContraRelojManager {
    private static ContraRelojManager instance_;
    
    private ContraRelojManager() {
        // Constructor privado
    }

    public static ContraRelojManager getInstance() {
        return instance_;
    }


    public static int init() {

        return 1;
    }


}
