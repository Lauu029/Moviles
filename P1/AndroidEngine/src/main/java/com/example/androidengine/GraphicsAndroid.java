package com.example.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.Color;
import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Scene;

public class GraphicsAndroid implements com.example.engine.Graphics {
    private int width = 0, height = 0;
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    public GraphicsAndroid(SurfaceView view)  {
        this.myView = view;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.canvas= new Canvas();
        this.paint.setColor(0xFFFF0080);
    }

    @Override
    public ImageAndroid newImage(String name) {
        return null;
    }

    @Override
    public FontAndroid newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setcolor(Color color) {

    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {

    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {

    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        Log.d("MiTag", "Dibujando cuadrado...");
        canvas.drawRect(cX,cY,width,height,this.paint);
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {

    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {

    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {

    }

    @Override
    public void drawText(String text, int x, int y, Font font) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render(Scene myScene) {
        this.canvas.drawColor(0xFF0000FF);
        while (!this.holder.getSurface().isValid());

        this.canvas = this.holder.lockCanvas();
        clear(0xFF340001);
        myScene.render();
        this.holder.unlockCanvasAndPost(canvas);
    }


}
