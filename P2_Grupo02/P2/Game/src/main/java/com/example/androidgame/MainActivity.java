package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
//import android.widget.RelativeLayout;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidgame.GameLogic.MenuScene;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
        this.renderView_ = new SurfaceView(this);
        setContentView(this.renderView_);
        IEngine_ = new Engine(renderView_,this);
        GameManager.init(IEngine_,400,600);
        MenuScene gm=new MenuScene(IEngine_,400,600);

        IEngine_.setScene(gm);

        IEngine_.resume();
        gm.init();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
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