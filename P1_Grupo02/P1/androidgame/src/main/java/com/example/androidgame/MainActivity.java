package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;


import com.example.androidengine.EngineAndroid;
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
        GameManager.init(IEngineAndroid_,400,600);
        IEngineAndroid_.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IEngineAndroid_.pause();
        GameManager.getInstance_().saveData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IEngineAndroid_.resume();
        GameManager.getInstance_().readData();

    }
}