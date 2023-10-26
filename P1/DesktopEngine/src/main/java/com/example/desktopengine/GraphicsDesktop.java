package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.TouchEvent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GraphicsDesktop implements IGraphics {
    private String Imagesroute="Assets/";
    private JFrame myView_;
    private BufferStrategy myBufferStrategy_;
    private Graphics2D myGraphics2D_;
    private int resX_ = 0, resY_ = 0;
    float scale_=1;
    float translateX_=0,translateY_=0;
    private int width_ = 0, height_ = 0;
    IFont myFont_;
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
        Image image=null;
        try{
        image= ImageIO.read(new File(Imagesroute+name));}
        catch (IOException e){
            System.out.println("No se cargo la imagen "+name);
            throw new RuntimeException(e);
        }
        return new ImageDesktop(image);

    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold, boolean italic) {

        return new FontDesktop(filename,size,isBold,italic);
    }

    @Override
    public void drawText(String text, int x, int y) {
        FontMetrics fm = this.myGraphics2D_.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int centerX = x - textWidth / 2;
        int centerY = y + textHeight / 2;


        this.myGraphics2D_.drawString(text, centerX,centerY);
    }
    @Override
    public void setFont(IFont font) {
        FontDesktop dfont=(FontDesktop)font;
        this.myGraphics2D_.setFont(dfont.getFont());
    }
    @Override
    public void clear(int color) {
        AffineTransform temp=this.myGraphics2D_.getTransform();
        this.myGraphics2D_.setTransform(af);
        this.myGraphics2D_.setColor(new Color(color));
        this.myGraphics2D_.fillRect(0, 0, myView_.getWidth(), myView_.getHeight());
        this.myGraphics2D_.setTransform(temp);
    }

    @Override
    public void drawImage(IImage iimage, int posX, int posY, int height, int widht) {
        ImageDesktop dimage=(ImageDesktop)iimage;
        myGraphics2D_.drawImage(dimage.getImage(),posX,posY,posX+widht,posY+height,0,0,dimage.getWidth(),dimage.getHeight(),null);

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


   /* @Override
    public void drawText(String text, int x, int y,int size, IFont IFont) {
        Font font = new Font("Arial", Font.PLAIN, size);
        this.myGraphics2D_.setFont(font);
        FontMetrics fm = this.myGraphics2D_.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

       x = x - (textWidth / 2);
       y = y + (textHeight / 2);  // Cambia el signo para que el pivote sea el centro

        this.myGraphics2D_.drawString(text, x, y);
        this.myGraphics2D_.drawString(text, x, y);
    }*/

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


        this.myGraphics2D_.translate(x,y);
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


    public void prepareFrame() {

        myGraphics2D_ = (Graphics2D) this.myBufferStrategy_.getDrawGraphics();
        this.translate(translateX_,translateY_);
        this.scale(scale_,scale_);
    }


    public void endFrame() {
        myGraphics2D_.dispose();
        this.myBufferStrategy_.show();
    }






    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        //System.out.print("Resize\n");
        width_ = (int) myView_.getWidth();
        height_ = (int) myView_.getHeight();

        int viewWidth = width_ - myView_.getInsets().left - myView_.getInsets().right;
        int viewHeight = height_ - myView_.getInsets().top - myView_.getInsets().bottom;

        float scaleW = (float) viewWidth / (float) sceneWidth;
        float scaleH = (float) viewHeight / (float) sceneHeight;

        if (scaleW < scaleH) {
            scale_ = scaleW;
        } else {
            scale_ = scaleH;
        }

        float resizeW, resizeH;
        resizeW = sceneWidth * scale_;
        resizeH = sceneHeight * scale_;

         translateX_ = ((float) viewWidth - resizeW) / 2.0f;
         translateY_ = ((float) viewHeight - resizeH) / 2.0f;

// Ajustar por los bordes
        translateX_ += myView_.getInsets().left;
        translateY_ += myView_.getInsets().top;

// Configurar la transformación
        AffineTransform af = new AffineTransform();
        af.translate(translateX_, translateY_);
        af.scale(scale_, scale_);
        myGraphics2D_.setTransform(af);




    }

    @Override
    public float getScale_() {
        return scale_;
    }

    @Override
    public float getTranslateX_() {
        return translateX_;
    }

    @Override
    public float getTranslateY_() {
        return translateY_;
    }
}
