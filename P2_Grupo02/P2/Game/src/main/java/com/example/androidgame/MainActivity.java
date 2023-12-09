package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkRequest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.example.androidengine.ReminderWorker;
import com.example.androidgame.GameLogic.AssetsManager;
import com.example.androidgame.GameLogic.EnumPalette;
import com.google.android.gms.ads.AdView;
//import android.widget.RelativeLayout;

import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.GameManager;
import com.example.androidgame.GameLogic.MenuScene;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView_;
    private Engine IEngine_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("--------------me creo-----------------------");
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
        createWorkRequest(15);
        GameManager.init(IEngine_, 400, 600);
        IEngine_.resume();
        onNewIntent(getIntent());
    }
    private void createWorkRequest(long timeDelayInSeconds) {
        IEngine_.getMobile().programNotification((int)timeDelayInSeconds,TimeUnit.SECONDS,R.drawable.logo,"MASTERMIND","Entra y juega te esperan nuevos niveles!!");
    }
    @Override
    protected void onPause() {
        super.onPause();
        GameManager.getInstance().saveGameData();
        Log.d("GAME","--------------------me pause-------------------------");
        createWorkRequest(45);
        IEngine_.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        GameManager.getInstance().loadGameData();
        Log.d("GAME", "RESUMEEEE");
        IEngine_.resume();
        onNewIntent(getIntent());

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);  // Actualiza el Intent actual con el nuevo Intent recibido

        if (getIntent().hasExtra("notification")) {
            Log.d("NOTIFI", "NOTIFICATION");
        } else {
            Log.d("NOTIFI", "No notification extra found");
        }
    }
}