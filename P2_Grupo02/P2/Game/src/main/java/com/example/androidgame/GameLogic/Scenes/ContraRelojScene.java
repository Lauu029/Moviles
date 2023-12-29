package com.example.androidgame.GameLogic.Scenes;

import com.example.androidengine.Graphics;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Managers.ContraRelojManager;
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
    public void update(double time) {
        super.update(time);
        ContraRelojManager.getInstance().addTime(time);
        if(ContraRelojManager.getInstance().getTime()<=0){
            MenuScene contra =new MenuScene();
            SceneManager.getInstance().addScene(contra, SceneNames.MENU.ordinal());
        }
    }
    @Override
    public void render() {
        super.render();
        Graphics graph=GameManager.getInstance().getIEngine().getGraphics();
        graph.setFont(font_);
        graph.setColor(0xFF000000);
        graph.drawText(  ContraRelojManager.getInstance().getFormattedTime()+" ",GameManager.getInstance().getWidth()/2,85);
    }
    @Override
    protected void ChangeEndScene(boolean win, int try_) {
        nivel++;
        if(win&&nivel<5){


            ContraRelojScene contra =new ContraRelojScene(world_,nivel);
            Difficulty dif = new Difficulty();
            int codeSize= GameManager.getInstance().getLevel().getSolutionColors()+1;
            dif.setSolutionColors(codeSize);
            dif.setPosibleColors( GameManager.getInstance().getLevel().getPosibleColors()+1);
            dif.setTries(GameManager.getInstance().getLevel().getTries()-1);
            dif.setRepeat(false);
            GameManager.getInstance().setLevel(dif);
            SceneManager.getInstance().addScene(contra, SceneNames.CONTRA.ordinal());

        }
        else {
            if(win){
                ContraRelojManager.getInstance().updateMaxTime(ContraRelojManager.getInstance().getTime());
            }
            MenuScene contra =new MenuScene();
            SceneManager.getInstance().addScene(contra, SceneNames.MENU.ordinal());
            ContraRelojManager.getInstance().restartTimer();
        }


    }
}
