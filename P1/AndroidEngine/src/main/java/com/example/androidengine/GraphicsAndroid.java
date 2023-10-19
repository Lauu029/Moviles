package com.example.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.Color;
import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Scene;

public class GraphicsAndroid implements com.example.engine.Graphics {
    private int width_ = 0, height_ = 0;
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;
    private Color color;
    private int resX_ = 0, resY_ = 0;
    float scale_=1;
    float translateX_=0,translateY_=0;
    public GraphicsAndroid(SurfaceView view) {
        this.myView = view;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.canvas = new Canvas();

        this.color = new ColorAndroid();
       // this.paint.setColor(0x53ECDED3);
    }
    private void resizeCanvas(Scene myScene){
        float scaleW;
        float scaleH;
        height_=canvas.getHeight();
        width_=canvas.getWidth();

        scaleW=width_/myScene.getWidth();
        scaleH=height_/myScene.getHeight();

        if(scaleW<scaleH){
            scale_=scaleW;
        }else scale_=scaleH;

        int resizeW,resizeH;
        resizeW=myScene.getWidth()*(int)scale_;
        resizeH=myScene.getHeight()*(int)scale_;
        translateX_=0.0f; translateY_=0.0f;
        if(resizeH==height_){
            translateX_=(width_-resizeW)/2;
        }else{
            translateY_=(height_-resizeH)/2;
        }
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
    public void setStrokeWidth(int width) {
        paint.setStrokeWidth(width);
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

        canvas.drawRect(cX, cY, width, height, this.paint);
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        paint.setStyle(Paint.Style.STROKE);

        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        canvas.drawRoundRect(rect, arc, arc, paint);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        canvas.drawLine(initX, initY, endX, endY, this.paint);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
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
        return width_;
    }

    @Override
    public int getHeight() {
        return height_;
    }

    @Override
    public void render(Scene myScene) {

        //engine
        myScene.render();
        //endframe
        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void translate(float x, float y) {
        this.canvas.translate(x,y);
    }

    @Override
    public void scale(float x, float y) {
        this.canvas.scale(x,y);
    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void prepare() {

    }

    @Override
    public void endFrame() {

    }


}
