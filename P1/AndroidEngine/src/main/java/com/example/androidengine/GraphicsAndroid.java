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
    private SurfaceView myView;
    private SurfaceHolder myHolder;
    private Paint myPaint;
    private Canvas myCanvas;

    private float scale = 1;
    private float translateX = 0, translateY = 0;
    private AssetManager myAssetManager;

    public GraphicsAndroid(SurfaceView view, AssetManager asset) {
        this.myView = view;
        this.myHolder = this.myView.getHolder();
        this.myPaint = new Paint();
        this.myCanvas = new Canvas();
        myAssetManager = asset;
    }

    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        height_ = myCanvas.getHeight();
        width_ = myCanvas.getWidth();

        float scaleW = (float) width_ / (float) sceneWidth;
        float scaleH = (float) height_ / (float) sceneHeight;

        if (scaleW < scaleH)
            scale = scaleW;
        else
            scale = scaleH;

        float resizeW, resizeH;
        resizeW = sceneWidth * scale;
        resizeH = sceneHeight * scale;

        translateX = (width_ - resizeW) / 2;
        translateY = (height_ - resizeH) / 2;
    }

    public float getScale() {
        return scale;
    }

    public float getTranslateX() {
        return translateX;
    }

    public float getTranslateY() {
        return translateY;
    }


    @Override
    public ImageAndroid newImage(String name) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = this.myAssetManager.open(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (is != null)
            bitmap = BitmapFactory.decodeStream(is);

        return new ImageAndroid(bitmap);
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold, boolean italic) {
        return new FontAndroid(filename, size, isBold, italic, myAssetManager);
    }

    /*@Override
    public FontAndroid newFont(String filename, int size, boolean isBold) {
        return new FontAndroid(filename, size, isBold);
    }*/

    @Override
    public void clear(int color) {
        myCanvas.drawColor(color);
    }

    @Override
    public void setStrokeWidth(int width) {
        myPaint.setStrokeWidth(width);
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
        this.myCanvas.drawBitmap(imageAndroid.getImage(), src, dst, this.myPaint);
    }

    @Override
    public void setColor(int color_) {
        this.myPaint.setColor(color_);
    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        myPaint.setStyle(Paint.Style.FILL);
        myCanvas.drawRect(cX, cY, width + cX, height + cY, this.myPaint);
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas.drawRoundRect(rect, arc, arc, myPaint);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        myPaint.setStyle(Paint.Style.STROKE);

        myCanvas.drawRect(cX, cY, width, height, this.myPaint);
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint.setStyle(Paint.Style.STROKE);

        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas.drawRoundRect(rect, arc, arc, myPaint);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        myCanvas.drawLine(initX, initY, endX, endY, this.myPaint);
    }

    @Override
    public void setFont(IFont font) {
        FontAndroid aFont = (FontAndroid) font;
        //Canvas canvas = holder.lockCanvas();

        myPaint.setTypeface(aFont.getFont());
        myPaint.setTextSize(aFont.getSize());

        myPaint.setFakeBoldText(aFont.isBold());
        myPaint.setTextSkewX(aFont.isItalic() ? -0.25f : 0.0f);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        myPaint.setStyle(Paint.Style.FILL);
        myCanvas.drawCircle(cx, cy, radius, myPaint);
    }

    @Override
    public void drawText(String text, int x, int y) {
        myPaint.setStyle(Paint.Style.FILL);

        // Medir el ancho del texto
        float textWidth = myPaint.measureText(text);
        float textHeight = myPaint.getTextSize(); // Altura de la fuente
        // Calcular las coordenadas para centrar el texto
        float centerX = x - (textWidth / 2);
        float centerY = y + (textHeight / 2);

        myCanvas.drawText(text, centerX, centerY, myPaint);
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
        this.myCanvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        this.myCanvas.scale(x, y);
    }

    @Override
    public void save() {
    }

    @Override
    public void restore() {
    }


    public void prepareFrame() {
        while (!this.myHolder.getSurface().isValid()) ;
        //resizeCanvas(myScene);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.myCanvas = this.myHolder.lockHardwareCanvas();
        } else this.myCanvas = this.myHolder.lockCanvas();
        this.translate(translateX, translateY);
        this.scale(scale, scale);
    }

    public void endFrame() {
        this.myHolder.unlockCanvasAndPost(myCanvas);
    }

}
