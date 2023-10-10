package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gamelogic.MasterMind;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MasterMind g=new MasterMind();
        g.init();
    }
}