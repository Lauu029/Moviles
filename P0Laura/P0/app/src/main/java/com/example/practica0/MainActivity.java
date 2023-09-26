package com.example.practica0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "Prueba.depuracion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "Starting app");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"Restarting app");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Resuming app");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"App Paused");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"App Stopped");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"App Destroyed");
    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}