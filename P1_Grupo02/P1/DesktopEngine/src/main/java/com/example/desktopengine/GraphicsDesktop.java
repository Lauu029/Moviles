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
    private String ImagesRoute_ = "Assets/Images/"; // Ruta base para las imagenes
    private JFrame myView_; // JFrame que representa la vista
    private BufferStrategy myBufferStrategy_; // Estrategia de buffer
    private Graphics2D myGraphics2D_; // Gráficos 2D
    float scale_ = 1; // Escala actual
    float translateX_ = 0, translateY_ = 0; // Desplazamiento
    private int width_ = 0, height_ = 0; //Parametros que gruardan el alto y el ancho de la ventana
    private AffineTransform af_;

    // Constructor de la clase, recibe un JFrame como argumento
    public GraphicsDesktop(JFrame myView){
        this.myView_ =myView;
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                myView_.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        } else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }
        this.myBufferStrategy_ = this.myView_.getBufferStrategy();
        this.myGraphics2D_ = (Graphics2D) myBufferStrategy_.getDrawGraphics();

        height_ = this.myView_.getHeight();
        width_ = this.myView_.getWidth();

        af_ = myGraphics2D_.getTransform();


    }

    @Override
    public IImage newImage(String name) {
        Image image=null;
        try{
        image= ImageIO.read(new File(ImagesRoute_ +name));}
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
    //Metodo para dibujar un texto centrado
    @Override
    public void drawText(String text, int x, int y) {
        FontMetrics fm = this.myGraphics2D_.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getFont().getSize();

        int centerX = x - textWidth / 2;
        int centerY = y + textHeight / 2;

        this.myGraphics2D_.drawString(text, centerX,centerY);
    }
    //Establece el tipo de fuente con el que se va a escribir
    @Override
    public void setFont(IFont font) {
        FontDesktop dfont=(FontDesktop)font;
        this.myGraphics2D_.setFont(dfont.getFont());
    }
    //Pinta la ventana de un determinado color "limpiando" lo que hay en pantalla
    @Override
    public void clear(int color) {
        AffineTransform temp=this.myGraphics2D_.getTransform();
        this.myGraphics2D_.setTransform(af_);
        this.myGraphics2D_.setColor(new Color(color));
        this.myGraphics2D_.fillRect(0, 0, myView_.getWidth(), myView_.getHeight());
        this.myGraphics2D_.setTransform(temp);
    }
    //Dibuja una imagen en una determinada posicion
    @Override
    public void drawImage(IImage iimage, int posX, int posY, int height, int widht) {
        ImageDesktop dimage=(ImageDesktop)iimage;
        myGraphics2D_.drawImage(dimage.getImage_(),posX,posY,posX+widht,posY+height,0,0,dimage.getWidth(),dimage.getHeight(),null);
    }
    //Establece el color con el que se va a pintar
    @Override
    public void setColor(int color) {
        Color color_=new Color(color);

        this.myGraphics2D_.setColor(color_);
    }
    //Metodo para pintar un rectangulo con relleno
    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {
        this.myGraphics2D_.fillRect(cX, cY, width, height);
    }
    //Metodo para pintar un rectangulo con bordes redondeados y relleno
    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D_.fillRoundRect(cX, cY, width, height, arc, arc);
    }
    //Metodo para pintar un rectangulo sin relleno
    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {
        this.myGraphics2D_.drawRect(cX, cY, width, height);
    }
    //Actualiza el grosor de la pintura a un determinado valor
    @Override
    public void setStrokeWidth(int width) {
        this.myGraphics2D_.setStroke(new BasicStroke(width));
    }
    //Metodo para pintar un rectangulo con bordes redondeados sin relleno
    @Override
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        this.myGraphics2D_.drawRoundRect(cX, cY, width, height, arc, arc);
    }
    //Metodo para pintar una linea recta
    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.myGraphics2D_.drawLine(initX, initY, endX, endY);
    }
    //Metodo para dibujar un circulo con relleno
    @Override
    public void drawCircle(int cx, int cy, int radius) {
        this.myGraphics2D_.fillOval(cx-radius,cy-radius,2*radius,2*radius);

    }
    //Metodo que devuelve el ancho de la ventana
    @Override
    public int getWidth_() {
        return width_;
    }
    //Metodo que devuelve el alto de la ventana
    @Override
    public int getHeight_() {
        return height_;
    }
    //Metodo para desplazar
    @Override
    public void translate(float x, float y) {
        this.myGraphics2D_.translate(x,y);
    }
    //Metodo para reescalar
    @Override
    public void scale(float x, float y) {
        this.myGraphics2D_.scale((double)x,(double)y);
    }

    @Override
    public void save() {}
    @Override
    public void restore() { }
    //Prepara un frame para su renderización
    public void prepareFrame() {
        synchronized (this) {
            //Se obtiene el contexto grafico
            myGraphics2D_ = (Graphics2D) this.myBufferStrategy_.getDrawGraphics();
            //Llamadas para respoisiconar y reescalar
            this.translate(translateX_, translateY_);
            this.scale(scale_, scale_);
        }
    }
    //Finaliza el renderizado liberando los recursos gráficos y mosrando los cambios realizados
    public void endFrame() {
        myGraphics2D_.dispose();
        this.myBufferStrategy_.show();
    }
    //Método encargado de hacer los calculos necesarios para reescaalar la pantalla a un determinado tamanyo
    @Override
    public void resize(float sceneWidth, float sceneHeight) {
        //cogemos el width y height del myview
        width_ = (int) myView_.getWidth();
        height_ = (int) myView_.getHeight();

        int viewWidth = width_ - myView_.getInsets().left - myView_.getInsets().right;
        int viewHeight = height_ - myView_.getInsets().top - myView_.getInsets().bottom;
        //calculamos la escana vertica y horizontal
        float scaleW = (float) viewWidth / (float) sceneWidth;
        float scaleH = (float) viewHeight / (float) sceneHeight;
        //elegimos la mas pequeña
        if (scaleW < scaleH) {
            scale_ = scaleW;
        } else {
            scale_ = scaleH;
        }

        float resizeW, resizeH;
        //calculamos el tamaño que tendra
        resizeW = sceneWidth * scale_;
        resizeH = sceneHeight * scale_;
        //trasladamos
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
    //Devuelve el valor del factor de escalado
    @Override
    public float getScale_() {
        return scale_;
    }
    //Devuelve el valor del factor de desplazamiento en X
    @Override
    public float getTranslateX_() {
        return translateX_;
    }
    //Devuelve el valor del factor de desplazamiento en Y
    @Override
    public float getTranslateY_() {
        return translateY_;
    }
}
