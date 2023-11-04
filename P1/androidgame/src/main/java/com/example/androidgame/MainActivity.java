package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;


import com.example.androidengine.EngineAndroid;
import com.example.gamelogic.GameManager;
import com.example.gamelogic.MenuScene;

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
        MenuScene gm=new MenuScene(IEngineAndroid_,400,600);

        IEngineAndroid_.setScene(gm);

        IEngineAndroid_.resume();
        gm.init();
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