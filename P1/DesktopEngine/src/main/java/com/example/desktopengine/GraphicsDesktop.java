package com.example.desktopengine;

import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GraphicsDesktop implements com.example.engine.Graphics {
    private JFrame myView_;
    private BufferStrategy bufferStrategy_;
    private Graphics2D graphics2D_;
    private int width_ = 0, height_ = 0;
    private int resX_ = 0, resY_ = 0;
    float scale_=1;
    float translateX_=0,translateY_=0;
    int sceneW_=0,sceneH=0;
    AffineTransform af;
    public GraphicsDesktop(JFrame myView){
        this.myView_=myView;

        this.bufferStrategy_ = this.myView_.getBufferStrategy();
        this.graphics2D_ = (Graphics2D) bufferStrategy_.getDrawGraphics();
        //this.graphics2D_.getTransform();

        height_=myView_.getHeight();
        width_=myView_.getWidth();

        af = graphics2D_.getTransform();


    }
    private void resizeCanvas(Scene myScene){
        height_ = myView_.getHeight();
        width_ = myView_.getWidth();

        float scaleW = (float) width_ / (float) myScene.getWidth();
        float scaleH = (float) height_ / (float) myScene.getHeight();

        if (scaleW < scaleH) {
            scale_ = scaleW;
        } else {
            scale_ = scaleH;
        }


        float resizeW, resizeH;
        resizeW = myScene.getWidth() * scale_;
        resizeH = myScene.getHeight() * scale_;

        translateX_ = ((float)width_ - resizeW) / 2.0f;
        translateY_ = ((float)height_ - resizeH) / 2.0f;
    }
    void setSize( ){}
    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        this.graphics2D_.setColor(new Color(color));
        this.graphics2D_.fillRect(0, 0, width_, height_);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setcolor(int color) {
        Color color_=new Color(color);

        this.graphics2D_.setColor(color_);
    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {

        this.graphics2D_.fillRect(cX, cY, width, height);

        //this.graphics2D_.setColor();
        //this.graphics2D_.setPaintMode();
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.graphics2D_.fillRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        this.graphics2D_.drawRect(cX, cY, width, height);
    }

    @Override
    public void setStrokeWidth(int width) {
        this.graphics2D_.setStroke(new BasicStroke(width));
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.graphics2D_.drawRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.graphics2D_.drawLine(initX, initY, endX, endY);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        this.graphics2D_.fillOval(cx-radius,cy-radius,2*radius,2*radius);

    }

    @Override
    public void drawText(String text, int x, int y,int size, Font font) {
        this.graphics2D_.drawString(text, x, y);
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
        do {
            do {
                graphics2D_ = (Graphics2D) this.bufferStrategy_.getDrawGraphics();
                try {
                    // Establecer la escala y la traslación
                    graphics2D_.setTransform(af);
                    resizeCanvas(myScene);
                    this.scale(scale_, scale_);
                    this.translate(translateX_, translateY_);
                    System.out.println("translateX: " + translateX_ + " translateY: " + translateY_ + " scale: " + scale_);

                    // Dibujar la escena
                    myScene.render();
                } finally {
                    graphics2D_.dispose();
                }
            } while (this.bufferStrategy_.contentsRestored());
            this.bufferStrategy_.show();
        } while (this.bufferStrategy_.contentsLost());

        // Restaurar la transformación original
        //graphics2D_.setTransform(af);
    }

    @Override
    public void translate(float x, float y) {
        int left = myView_.getInsets().left;
        int up = myView_.getInsets().top;
        this.graphics2D_.translate(x,y);
    }

    @Override
    public void scale(float x, float y) {

        this.graphics2D_.scale((double)x,(double)y);
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
