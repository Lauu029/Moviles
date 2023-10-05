package com.example.androidengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.InputStream;

public class ImageAndroid implements com.example.engine.Image {
    private int widht = 0, height = 0;


    ImageAndroid(String source, int h, int w) {
        height = h;
        widht = w;
//        InputStream is = assetManager.open(source);
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        Canvas canvas = holder.lockCanvas();
//        Paint paint = new Paint();
//        canvas.drawBitmap(bitmap, x, y, paint);

    }

    @Override
    public int getWidth() {
        return widht;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
