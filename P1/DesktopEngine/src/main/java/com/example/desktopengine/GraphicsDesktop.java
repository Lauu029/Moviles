package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

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
    private String Imagesroute = "Assets/"; // Ruta base para las imagenes
    private JFrame myView; // JFrame que representa la vista
    private BufferStrategy myBufferStrategy; // Estrategia de buffer
    private Graphics2D myGraphics2D; // Gráficos 2D
    float scale = 1; // Escala actual
    float translateX = 0, translateY = 0; // Desplazamiento
    private int width = 0, height = 0;
    private AffineTransform af;

    // Constructor de la clase, recibe un JFrame como argumento
    public GraphicsDesktop(JFrame myView){
        this.myView =myView;

        this.myBufferStrategy = this.myView.getBufferStrategy();
        this.myGraphics2D = (Graphics2D) myBufferStrategy.getDrawGraphics();

        height = this.myView.getHeight();
        width = this.myView.getWidth();

        af = myGraphics2D.getTransform();
    }

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
        FontMetrics fm = this.myGraphics2D.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int centerX = x - textWidth / 2;
        int centerY = y + textHeight / 2;

        this.myGraphics2D.drawString(text, centerX,centerY);
    }
    @Override
    public void setFont(IFont font) {
        FontDesktop dfont=(FontDesktop)font;
        this.myGraphics2D.setFont(dfont.getFont());
    }
    @Override
    public void clear(int color) {
        AffineTransform temp=this.myGraphics2D.getTransform();
        this.myGraphics2D.setTransform(af);
        this.myGraphics2D.setColor(new Color(color));
        this.myGraphics2D.fillRect(0, 0, myView.getWidth(), myView.getHeight());
        this.myGraphics2D.setTransform(temp);
    }

    @Override
    public void drawImage(IImage iimage, int posX, int posY, int height, int widht) {
        ImageDesktop dimage=(ImageDesktop)iimage;
        myGraphics2D.drawImage(dimage.getImage(),posX,posY,posX+widht,posY+height,0,0,dimage.getWidth(),dimage.getHeight(),null);
    }

    @Override
    public void setColor(int color) {
        Color color_=new Color(color);

        this.myGraphics2D.setColor(color_);
    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        this.myGraphics2D.fillRect(cX, cY, width, height);
    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D.fillRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        this.myGraphics2D.drawRect(cX, cY, width, height);
    }

    @Override
    public void setStrokeWidth(int width) {
        this.myGraphics2D.setStroke(new BasicStroke(width));
    }

    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D.drawRoundRect(cX, cY, width, height, arc, arc);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.myGraphics2D.drawLine(initX, initY, endX, endY);
    }

    @Override
    public void drawCircle(int cx, int cy, int radius) {
        this.myGraphics2D.fillOval(cx-radius,cy-radius,2*radius,2*radius);

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
    public void translate(float x, float y) {
        this.myGraphics2D.translate(x,y);
    }

    @Override
    public void scale(float x, float y) {
        this.myGraphics2D.scale((double)x,(double)y);
    }

    @Override
    public void save() {
    }

    @Override
    public void restore() {
    }

    public void prepareFrame() {
        synchronized (this) {
            // Código crítico
            myGraphics2D = (Graphics2D) this.myBufferStrategy.getDrawGraphics();
            this.translate(translateX, translateY);
            this.scale(scale, scale);
        }
    }

    public void endFrame() {
        myGraphics2D.dispose();
        this.myBufferStrategy.show();
    }

    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        //cogemos el width y height del myview
        width = (int) myView.getWidth();
        height = (int) myView.getHeight();

        int viewWidth = width - myView.getInsets().left - myView.getInsets().right;
        int viewHeight = height - myView.getInsets().top - myView.getInsets().bottom;
        //calculamos la escana vertica y horizontal
        float scaleW = (float) viewWidth / (float) sceneWidth;
        float scaleH = (float) viewHeight / (float) sceneHeight;
        //elegimos la mas pequeña
        if (scaleW < scaleH) {
            scale = scaleW;
        } else {
            scale = scaleH;
        }

        float resizeW, resizeH;
        //calculamos el tamaño que tendra
        resizeW = sceneWidth * scale;
        resizeH = sceneHeight * scale;
        //trasladamos
         translateX = ((float) viewWidth - resizeW) / 2.0f;
         translateY = ((float) viewHeight - resizeH) / 2.0f;

        // Ajustar por los bordes
        translateX += myView.getInsets().left;
        translateY += myView.getInsets().top;

        // Configurar la transformación
        AffineTransform af = new AffineTransform();
        af.translate(translateX, translateY);
        af.scale(scale, scale);
        myGraphics2D.setTransform(af);
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public float getTranslateX() {
        return translateX;
    }

    @Override
    public float getTranslateY() {
        return translateY;
    }
}
