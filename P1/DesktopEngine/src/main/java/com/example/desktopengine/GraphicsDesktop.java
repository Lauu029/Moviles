package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GraphicsDesktop implements IGraphics {

    private JFrame myView_;
    private BufferStrategy myBufferStrategy_;
    private Graphics2D myGraphics2D_;
    private int resX_ = 0, resY_ = 0;
    float scale_=1;
    float translateX_=0,translateY_=0;
    private int width_ = 0, height_ = 0;

    AffineTransform af;
    public GraphicsDesktop(JFrame myView){
        this.myView_=myView;

        this.myBufferStrategy_ = this.myView_.getBufferStrategy();
        this.myGraphics2D_ = (Graphics2D) myBufferStrategy_.getDrawGraphics();
        //this.graphics2D_.getTransform();

        height_=myView_.getHeight();
        width_=myView_.getWidth();

        af = myGraphics2D_.getTransform();


    }
    void setSize( ){}

    @Override
    public IImage newImage(String name) {
        return null;
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        this.myGraphics2D_.setColor(new Color(color));
        this.myGraphics2D_.fillRect(0, 0, width_, height_);
    }

    @Override
    public void drawImage(IImage IImage, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setColor(int color) {
        Color color_=new Color(color);

        this.myGraphics2D_.setColor(color_);
    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {

        this.myGraphics2D_.fillRect(cX, cY, width, height);

        //this.graphics2D_.setColor();
        //this.graphics2D_.setPaintMode();
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D_.fillRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        this.myGraphics2D_.drawRect(cX, cY, width, height);
    }

    @Override
    public void setStrokeWidth(int width) {
        this.myGraphics2D_.setStroke(new BasicStroke(width));
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D_.drawRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.myGraphics2D_.drawLine(initX, initY, endX, endY);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        this.myGraphics2D_.fillOval(cx-radius,cy-radius,2*radius,2*radius);

    }

    @Override
    public void drawText(String text, int x, int y,int size, IFont IFont) {
        this.myGraphics2D_.drawString(text, x, y);
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
        int left = myView_.getInsets().left;
        int up = myView_.getInsets().top;
        this.myGraphics2D_.translate(x,y+up);
    }

    @Override
    public void scale(float x, float y) {

        this.myGraphics2D_.scale((double)x,(double)y);
    }



    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void prepareFrame() {
        do {
            do {
                myGraphics2D_ = (Graphics2D) this.myBufferStrategy_.getDrawGraphics();
            } while (this.myBufferStrategy_.contentsRestored());
        } while (this.myBufferStrategy_.contentsLost());
    }

    @Override
    public void endFrame() {
        do {
            do {

                myGraphics2D_.dispose();
            } while (this.myBufferStrategy_.contentsRestored());
            this.myBufferStrategy_.show();
        } while (this.myBufferStrategy_.contentsLost());


    }

    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        //System.out.print("Resize\n");
        width_ =(int) myView_.getWidth();
        height_ = (int) myView_.getHeight();

        float scaleW = (float) width_ / (float) sceneWidth;
        float scaleH = (float) height_ / (float) sceneHeight;

        if (scaleW < scaleH) {
            scale_ = scaleW;
        } else {
            scale_ = scaleH;
        }

        float resizeW, resizeH;
        resizeW = sceneWidth * scale_;
        resizeH = sceneHeight * scale_;

        translateX_ = ((float)width_ - resizeW) / 2.0f;
        translateY_ = ((float)height_ - resizeH) / 2.0f;
        this.myGraphics2D_.setTransform(af);
        this.translate(translateX_,translateY_);
        this.scale(scale_,scale_);


    }
}
