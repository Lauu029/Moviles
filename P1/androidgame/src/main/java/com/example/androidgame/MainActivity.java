package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gamelogic.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Game g=new Game();
        g.run();
    }
}