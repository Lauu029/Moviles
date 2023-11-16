package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
//import android.widget.RelativeLayout;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidgame.GameLogic.MenuScene;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView_;
    private Engine IEngine_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        this.renderView_ = new SurfaceView(this);
        setContentView(this.renderView_);
        IEngine_ = new Engine(renderView_);
        GameManager.init(IEngine_,400,600);
        MenuScene gm=new MenuScene(IEngine_,400,600);

        IEngine_.setScene(gm);

        IEngine_.resume();
        gm.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IEngine_.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IEngine_.resume();
    }
}