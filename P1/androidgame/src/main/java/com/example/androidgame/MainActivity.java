package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        EngineAndroid IEngineAndroid = new EngineAndroid(renderView);

       // MenuScene gm = new MenuScene(IEngineAndroid, 400, 600);
        //GameScene gm = new GameScene(IEngineAndroid,400,600);
        GameManager.init(IEngineAndroid,400,600);
        MenuScene gm=new MenuScene(IEngineAndroid,400,600);
       // int[] intentos_ = {0, 1, 2, 3};
        //EndScene gm= new EndScene(IEngineAndroid,400,600,false,intentos_,0);

        IEngineAndroid.setScene(gm);

        //RelativeLayout relativeLayout=findViewById(R.id.rlVar1);;

        IEngineAndroid.resume();
        gm.init();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}