package com.example.desktopengine;

import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    public GraphicsDesktop(JFrame myView){
        this.myView_=myView;

        this.bufferStrategy_ = this.myView_.getBufferStrategy();
        this.graphics2D_ = (Graphics2D) bufferStrategy_.getDrawGraphics();
        //this.graphics2D_.getTransform();

        height_=myView_.getHeight();
        width_=myView_.getWidth();

    }
    private void resizeCanvas(Scene myScene){
        float scaleW;
        float scaleH;
        height_=myView_.getHeight();
        width_=myView_.getWidth();

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
        //myView_.setBackground(color);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setcolor(int color) {

    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        this.graphics2D_.setColor(java.awt.Color.black);
        this.graphics2D_.fillRect(cX, cY, width, height);

        //this.graphics2D_.setColor();
        //this.graphics2D_.setPaintMode();
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {

    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {

    }

    @Override
    public void setStrokeWidth(int width) {

    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {

    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {

    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        this.graphics2D_.drawOval(cx,cy,radius,radius);
    }

    @Override
    public void drawText(String text, int x, int y,int size, Font font) {

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
        // Pintamos el frame
        resizeCanvas(myScene);
        do {
            do {
                Graphics graphics = this.bufferStrategy_.getDrawGraphics();
                try {

                    this.scale(scale_,scale_);
                    this.translate(translateX_,translateY_);
                    myScene.render();


                }
                finally {
                    graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                }
            } while(this.bufferStrategy_.contentsRestored());
            this.bufferStrategy_.show();
        } while(this.bufferStrategy_.contentsLost());

    }

    @Override
    public void translate(float x, float y) {
        this.graphics2D_.translate(x,y);
    }

    @Override
    public void scale(float x, float y) {
        this.graphics2D_.scale(x,y);
    }



    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }
}
