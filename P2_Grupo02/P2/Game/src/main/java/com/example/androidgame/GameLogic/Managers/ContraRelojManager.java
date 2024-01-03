package com.example.androidgame.GameLogic.Managers;

import com.example.androidengine.Engine;
import com.example.androidengine.Image;
import com.example.androidgame.GameLogic.Board;
import com.example.androidgame.GameLogic.Difficulty;
import com.example.androidgame.GameLogic.Utils.SaveData;

public class ContraRelojManager {
    private static ContraRelojManager instance_;
    public double maxTime=0;
    private double time=900;
    private ContraRelojManager() {

        // Constructor privado
    }

    public void updateMaxTime(double times){
        if(times>maxTime)maxTime=time;
    }
    public void addTime(double deltaTime){
        time-=deltaTime;
    }
    public void restartTimer(){
        time=900;
    }
    public double getTime(){
        return time;
    }
    public String getFormattedTime() {
        int minutes = (int) (time / 60);
        int seconds = (int) (time % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
    public String getFormattedMaxTime() {
        int minutes = (int) (maxTime / 60);
        int seconds = (int) (maxTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static ContraRelojManager getInstance() {
        return instance_;
    }


    public static int init() {
        instance_ = new ContraRelojManager();
        return 1;
    }


}
