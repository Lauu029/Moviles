package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;


import com.google.android.gms.ads.AdView;


import com.example.androidengine.Engine;
import com.example.androidgame.GameLogic.Managers.GameManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView_;
    private Engine IEngine_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        AdView adView_ = (AdView)findViewById(R.id.adView);

        this.renderView_ = findViewById(R.id.surfaceView);

        IEngine_ = new Engine(renderView_, this);

        if (adView_ != null) {
            Log.d("MainActivity", "AdView loaded..");
            IEngine_.getMobile().loadAdBanner(adView_);
        } else {
            Log.e("MainActivity", "AdView is definetly null");
        }

        GameManager.init(IEngine_, 400, 600);
        IEngine_.resume();

        onNewIntent(getIntent());

    }
    private void createWorkRequest(long timeDelayInSeconds) {
        //accedemos a mobile y creamos una notificacion dado estos segundos,este icono ...
        IEngine_.getMobile().programNotification((int)timeDelayInSeconds,TimeUnit.SECONDS,R.drawable.logo,"MASTERMIND","Entra y juega te esperan nuevos niveles!!");
    }
    @Override
    protected void onPause() {
        super.onPause();
        GameManager.getInstance().saveGameData();
        //metodo para crear una notificación
        createWorkRequest(45);
        IEngine_.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        GameManager.getInstance().loadGameData();
        IEngine_.resume();
        onNewIntent(getIntent());

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);  // Actualiza el Intent actual con el nuevo Intent recibido
        if (getIntent().hasExtra("notification")) {
            //si es una notificacion con este extra el jugador recibe 5 monedas
            Log.d("NOTIFI", "NOTIFICATION");
            GameManager.getInstance().addCoins(5);
        }
    }
}