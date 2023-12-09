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
        createWorkRequest(12);
        GameManager.init(IEngine_, 400, 600);
        IEngine_.resume();
    }
    private void createWorkRequest(long timeDelayInSeconds) {
        @SuppressLint("RestrictedApi") Data inputData = new Data.Builder()
                .putString("title", "Reminder")
                .putString("message", "Tu mensaje aquí")  // Puedes cambiar esto según tus necesidades
                .put("mobile", IEngine_.getMobile())  // Suponiendo que Mobile sea Serializable o Parcelable
                .build();
        WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
                .setInputData(inputData)
                .build();

        // Enqueue the work request

        IEngine_.getMobile().sendWork(myWorkRequest);
    }
    @Override
    protected void onPause() {
        super.onPause();
        GameManager.getInstance().saveGameData();
        Log.d("GAME","--------------------me pause-------------------------");
        IEngine_.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        GameManager.getInstance().loadGameData();
        Log.d("GAME", "RESUMEEEE");
        IEngine_.resume();
        if(getIntent().hasExtra("notification")){
              Log.d("TAG", "NOTIFICATION");
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);  // Actualiza el Intent actual con el nuevo Intent recibido
        System.out.println("---------------------------------nuevo intent-----------------");
        if(getIntent().hasExtra("notification")){
            System.out.println("Vengo de una notificacion");
        }
    }
}