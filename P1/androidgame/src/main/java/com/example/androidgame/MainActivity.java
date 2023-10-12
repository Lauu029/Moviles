package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Scene;


import com.example.androidengine.EngineAndroid;
import com.example.engine.Engine;
import com.example.gamelogic.GameScene;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Engine engineAndroid= new EngineAndroid();
        GameScene g= new GameScene(engineAndroid);
        engineAndroid.setScene(g);
        g.init();

    }
}