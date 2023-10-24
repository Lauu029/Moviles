package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;


import com.example.androidengine.EngineAndroid;
import com.example.engine.IEngine;
import com.example.gamelogic.GameScene;
import com.example.gamelogic.MenuScene;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        IEngine IEngineAndroid = new EngineAndroid(renderView);
        MenuScene gm=new MenuScene(IEngineAndroid,400,600);
        IEngineAndroid.setScene(gm);

        //RelativeLayout relativeLayout=findViewById(R.id.rlVar1);;

        IEngineAndroid.resume();
        gm.init();

    }
}