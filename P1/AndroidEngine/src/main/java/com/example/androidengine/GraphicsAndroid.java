package com.example.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;

public class GraphicsAndroid implements IGraphics {
    private int width_ = 0, height_ = 0;
    private SurfaceView myView_;
    private SurfaceHolder myHolder_;
    private Paint myPaint_;
    private Canvas myCanvas_;
    private IColor myColor_;

    float scale_=1;
    float translateX_=0,translateY_=0;
    public GraphicsAndroid(SurfaceView view) {
        this.myView_ = view;
        this.myHolder_ = this.myView_.getHolder();
        this.myPaint_ = new Paint();
        this.myCanvas_ = new Canvas();

        this.myColor_ = new ColorAndroid();
       // this.paint.setColor(0x53ECDED3);
    }
    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        height_ = myCanvas_.getHeight();
        width_ = myCanvas_.getWidth();

        float scaleW = (float) width_ / (float) sceneWidth;
        float scaleH = (float) height_ / (float) sceneHeight;

        if (scaleW < scaleH) {
            scale_ = scaleW;
        } else {
            scale_ = scaleH;
        }

        float resizeW, resizeH;
        resizeW =sceneWidth* scale_;
        resizeH = sceneHeight * scale_;

        translateX_ = (width_ - resizeW) / 2;
        translateY_ = (height_ - resizeH) / 2;
        this.translate(translateX_,translateY_);
        this.scale(scale_,scale_);

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
        myCanvas_.drawColor(color);
    }
    @Override
    public void setStrokeWidth(int width) {
        myPaint_.setStrokeWidth(width);
    }
    @Override
    public void drawImage(IImage IImage, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setColor(int color_) {

        this.myColor_.setColor(color_);
        this.myPaint_.setColor(color_);

    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawRect(cX, cY, width, height, this.myPaint_);
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint_.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas_.drawRoundRect(rect, arc, arc, myPaint_);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        myPaint_.setStyle(Paint.Style.STROKE);

        myCanvas_.drawRect(cX, cY, width, height, this.myPaint_);
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint_.setStyle(Paint.Style.STROKE);

        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas_.drawRoundRect(rect, arc, arc, myPaint_);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        myCanvas_.drawLine(initX, initY, endX, endY, this.myPaint_);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawCircle(cx, cy, radius, myPaint_);
    }

    @Override
    public void drawText(String text, int x, int y,int size, IFont IFont) {
        //canvas.drawText();

        myPaint_.setStyle(Paint.Style.FILL);

        myPaint_.setTextSize(size);
        myCanvas_.drawText(text, x, y, myPaint_);
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
    public void translate(float x, float y) {

        this.myCanvas_.translate(x,y);
    }

    @Override
    public void scale(float x, float y) {
        this.myCanvas_.scale(x,y);
    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void prepareFrame() {

        while (!this.myHolder_.getSurface().isValid()) ;
        //resizeCanvas(myScene);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.myCanvas_ = this.myHolder_.lockHardwareCanvas();
        }
        else this.myCanvas_ = this.myHolder_.lockCanvas();
    }

    @Override
    public void endFrame() {

        this.myHolder_.unlockCanvasAndPost(myCanvas_);
    }

    @Override
    public void render(IScene scene) {

    }


}
