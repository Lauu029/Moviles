package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.gms.ads.AdView;
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
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView_.loadAd(adRequest);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        AdView adView_ = (AdView)findViewById(R.id.adView);

        this.renderView_ = findViewById(R.id.surfaceView);
        //setContentView(this.renderView_);
        IEngine_ = new Engine(renderView_, this);

        if (adView_ != null) {
            Log.d("MainActivity", "AdView loaded..");
            IEngine_.getMobile().loadAdBanner(adView_);
        } else {
            Log.e("MainActivity", "AdView is definetly null");
        }
        GameManager.init(IEngine_, 400, 600);
        MenuScene gm = new MenuScene(IEngine_, 400, 600);

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
        if(getIntent().hasExtra("notification")){
            System.out.println("Vengo de una notificacion");
        }
    }
}