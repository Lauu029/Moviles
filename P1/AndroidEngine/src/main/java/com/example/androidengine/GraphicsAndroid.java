package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsAndroid implements IGraphics {
    private int width_ = 0, height_ = 0;
    private SurfaceView myView_;
    private SurfaceHolder myHolder_;
    private Paint myPaint_;
    private Canvas myCanvas_;

    private float scale_ = 1;
    private float translateX_ = 0, translateY_ = 0;
    private AssetManager myAssetManager_;

    public GraphicsAndroid(SurfaceView view, AssetManager asset) {
        this.myView_ = view;
        this.myHolder_ = this.myView_.getHolder();
        this.myPaint_ = new Paint();
        this.myCanvas_ = new Canvas();
        myAssetManager_ = asset;
    }

    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        height_ = myCanvas_.getHeight();
        width_ = myCanvas_.getWidth();

        float scaleW = (float) width_ / (float) sceneWidth;
        float scaleH = (float) height_ / (float) sceneHeight;

        if (scaleW < scaleH)
            scale_ = scaleW;
        else
            scale_ = scaleH;

        float resizeW, resizeH;
        resizeW = sceneWidth * scale_;
        resizeH = sceneHeight * scale_;

        translateX_ = (width_ - resizeW) / 2;
        translateY_ = (height_ - resizeH) / 2;
    }

    public float getScale_() {
        return scale_;
    }

    public float getTranslateX_() {
        return translateX_;
    }

    public float getTranslateY_() {
        return translateY_;
    }


    @Override
    public ImageAndroid newImage(String name) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = this.myAssetManager_.open(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (is != null)
            bitmap = BitmapFactory.decodeStream(is);

        return new ImageAndroid(bitmap);
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold, boolean italic) {
        return new FontAndroid(filename, size, isBold, italic, myAssetManager_);
    }

    /*@Override
    public FontAndroid newFont(String filename, int size, boolean isBold) {
        return new FontAndroid(filename, size, isBold);
    }*/

    @Override
    public void clear(int color) {
        myCanvas_.drawColor(color);
    }

    @Override
    public void setStrokeWidth(int width) {
        myPaint_.setStrokeWidth(width);
    }

    @Override
    public void drawImage(IImage iimage, int posX, int posY, int height, int widht) {
        ImageAndroid imageAndroid = (ImageAndroid) iimage;
        Rect src = new Rect();
        src.left = 0;
        src.top = 0;
        src.right = imageAndroid.getWidth();
        src.bottom = imageAndroid.getHeight();
        Rect dst = new Rect();
        dst.left = posX;
        dst.top = posY;
        dst.right = posX + widht;
        dst.bottom = posY + height;
        this.myCanvas_.drawBitmap(imageAndroid.getImage(), src, dst, this.myPaint_);
    }

    @Override
    public void setColor(int color_) {
        this.myPaint_.setColor(color_);
    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawRect(cX, cY, width + cX, height + cY, this.myPaint_);
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
    public void setFont(IFont font) {
        FontAndroid aFont = (FontAndroid) font;
        //Canvas canvas = holder.lockCanvas();

        myPaint_.setTypeface(aFont.getFont());
        myPaint_.setTextSize(aFont.getSize_());

        myPaint_.setFakeBoldText(aFont.isBold_());
        myPaint_.setTextSkewX(aFont.isItalic_() ? -0.25f : 0.0f);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawCircle(cx, cy, radius, myPaint_);
    }

    @Override
    public void drawText(String text, int x, int y) {
        myPaint_.setStyle(Paint.Style.FILL);

        // Medir el ancho del texto
        float textWidth = myPaint_.measureText(text);
        float textHeight = myPaint_.getTextSize(); // Altura de la fuente
        // Calcular las coordenadas para centrar el texto
        float centerX = x - (textWidth / 2);
        float centerY = y + (textHeight / 2);

        myCanvas_.drawText(text, centerX, centerY, myPaint_);
    }

    @Override
    public int getWidth_() {
        return width_;
    }

    @Override
    public int getHeight_() {
        return height_;
    }

    @Override
    public void translate(float x, float y) {
        this.myCanvas_.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        this.myCanvas_.scale(x, y);
    }

    @Override
    public void save() {
    }

    @Override
    public void restore() {
    }


    public void prepareFrame() {
        while (!this.myHolder_.getSurface().isValid()) ;
        //resizeCanvas(myScene);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.myCanvas_ = this.myHolder_.lockHardwareCanvas();
        } else this.myCanvas_ = this.myHolder_.lockCanvas();
        this.translate(translateX_, translateY_);
        this.scale(scale_, scale_);
    }

    public void endFrame() {
        this.myHolder_.unlockCanvasAndPost(myCanvas_);
    }

}
