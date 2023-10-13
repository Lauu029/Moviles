package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.SurfaceView;


import com.example.androidengine.EngineAndroid;
import com.example.engine.Engine;
import com.example.gamelogic.GameScene;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        Engine engineAndroid= new EngineAndroid(renderView);
        GameScene g= new GameScene(engineAndroid);
        engineAndroid.setScene(g);
        engineAndroid.resume();
        g.init();

    }
}