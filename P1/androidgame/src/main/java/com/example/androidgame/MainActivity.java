package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;


import com.example.androidengine.EngineAndroid;
import com.example.engine.IEngine;
import com.example.gamelogic.EndScene;
import com.example.gamelogic.GameManager;
import com.example.gamelogic.GameScene;
import com.example.gamelogic.MenuScene;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView;
    private EngineAndroid IEngineAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        IEngineAndroid = new EngineAndroid(renderView);
        GameManager.init(IEngineAndroid,400,600);
        MenuScene gm=new MenuScene(IEngineAndroid,400,600);

        IEngineAndroid.setScene(gm);

        IEngineAndroid.resume();
        gm.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IEngineAndroid.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IEngineAndroid.resume();
    }
}