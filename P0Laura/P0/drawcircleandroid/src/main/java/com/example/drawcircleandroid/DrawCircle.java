package com.example.drawcircleandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

public class DrawCircle extends AppCompatActivity {
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_circle);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        paint = new Paint();
        paint.setColor(Color.YELLOW);

       surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

    }

    public void drawButton(View view) {

        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            float centerX = canvas.getWidth() / 2f;
            float centerY = canvas.getHeight() / 2f;
            float radius = 100f;
            canvas.drawCircle(centerX, centerY, radius, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void clean(View view) {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}