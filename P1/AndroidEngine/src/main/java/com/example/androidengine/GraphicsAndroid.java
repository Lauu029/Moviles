package com.example.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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
    private Color color;

    public GraphicsAndroid(SurfaceView view) {
        this.myView = view;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.canvas = new Canvas();
        this.color = new ColorAndroid();
       // this.paint.setColor(0x53ECDED3);
    }

    @Override
    public ImageAndroid newImage(String name) {
        return new ImageAndroid(name);
    }

    @Override
    public FontAndroid newFont(String filename, int size, boolean isBold) {
        return new FontAndroid(filename, size, isBold);
    }

    @Override
    public void clear(int color) {
        canvas.drawColor(color);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setcolor(int color_) {
        this.color.setColor(color_);
        this.paint.setColor(color_);

    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cX, cY, width, height, this.paint);
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        paint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        canvas.drawRoundRect(rect, arc, arc, paint);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawRect(cX, cY, width, height, this.paint);
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        canvas.drawRoundRect(rect, arc, arc, paint);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        canvas.drawLine(initX, initY, endX, endY, this.paint);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public void drawText(String text, int x, int y,int size, Font font) {
//        canvas.drawText();

        paint.setStyle(Paint.Style.FILL);

        paint.setTextSize(size);
        canvas.drawText(text, x, y, paint);
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

        while (!this.holder.getSurface().isValid()) ;

        this.canvas = this.holder.lockCanvas();
        myScene.render();
        this.holder.unlockCanvasAndPost(canvas);
    }


}
