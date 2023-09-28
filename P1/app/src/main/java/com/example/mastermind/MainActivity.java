package com.example.mastermind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {
    private SurfaceView renderView;

    private MyRenderClass render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creamos el SurfaceView que "contendrá" nuestra escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        MyScene scene = new MyScene();

        this.render = new MyRenderClass(this.renderView);
        scene.init(render);
        render.setScene(scene);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.render.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.render.pause();
    }
}
