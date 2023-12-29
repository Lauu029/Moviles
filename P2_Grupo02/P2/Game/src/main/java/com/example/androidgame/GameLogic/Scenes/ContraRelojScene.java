package com.example.androidgame.GameLogic.Scenes;

import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Managers.GameManager;
import com.example.androidgame.GameLogic.Managers.LevelManager;
import com.example.androidgame.GameLogic.Managers.SceneManager;

public class ContraRelojScene extends GameScene{
    int nivel=0;
    public ContraRelojScene(boolean world ,int actaulaLevel) {
        super(world,true);
        nivel=actaulaLevel;
    }
    @Override
    protected void ChangeEndScene(boolean win, int try_) {
        nivel++;
        if(win&&nivel<5){


            ContraRelojScene contra =new ContraRelojScene(world_,nivel);
            Difficulty dif = new Difficulty();
            int codeSize= GameManager.getInstance().getLevel().getSolutionColors()+nivel;
            dif.setSolutionColors(codeSize);
            dif.setPosibleColors( GameManager.getInstance().getLevel().getPosibleColors()+nivel);
            dif.setTries(GameManager.getInstance().getLevel().getTries()-1);
            dif.setRepeat(false);
            GameManager.getInstance().setLevel(dif);
            SceneManager.getInstance().addScene(contra, SceneNames.CONTRA.ordinal());
        }
        else {
            MenuScene contra =new MenuScene();
            SceneManager.getInstance().addScene(contra, SceneNames.MENU.ordinal());
        }


    }
}
