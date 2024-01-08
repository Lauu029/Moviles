package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;


import com.example.androidengine.EngineAndroid;
import com.example.androidengine.FileAndroid;
import com.example.gamelogic.Managers.FileManager;
import com.example.gamelogic.Managers.GameManager;
import com.example.gamelogic.Scenes.MenuScene;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView_;
    private EngineAndroid IEngineAndroid_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        this.renderView_ = new SurfaceView(this);
        setContentView(this.renderView_);
        IEngineAndroid_ = new EngineAndroid(renderView_);
        FileAndroid f= new FileAndroid(this.getAssets(),this,"saved_game.txt");
        FileManager.init(f);
        GameManager.init(IEngineAndroid_,400,600);
        IEngineAndroid_.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IEngineAndroid_.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IEngineAndroid_.resume();
    }
}