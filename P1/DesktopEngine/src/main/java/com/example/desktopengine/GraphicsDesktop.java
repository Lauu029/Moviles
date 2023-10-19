package com.example.desktopengine;

import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Scene;

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
    int translateX_=0,translateY_=0;
    public GraphicsDesktop(JFrame myView, int resX, int resY){
        this.myView_=myView;
        resX_=resX;
        resY_=resY;
        this.bufferStrategy_ = this.myView_.getBufferStrategy();
        this.graphics2D_ = (Graphics2D) bufferStrategy_.getDrawGraphics();
        this.graphics2D_.getTransform();
    }
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
    public void drawCircle(float cx, float cy, float radius) {

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
        do {
            do {
                Graphics graphics = this.bufferStrategy_.getDrawGraphics();
                try {
                    //this.render(myScene);
                    myScene.render();
                }
                finally {
                    graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                }
            } while(this.bufferStrategy_.contentsRestored());
            this.bufferStrategy_.show();
        } while(this.bufferStrategy_.contentsLost());

    }
}
