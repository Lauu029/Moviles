package com.example.engine;

import java.io.IOException;

public interface IGraphics {
    IImage newImage(String name) throws IOException; //Crea una nueva imagen

    IFont newFont(String fontname, int size, boolean isBold,boolean italic); //Crea una nueva tipografia
    void setFont(IFont font); //Establece una tipografia
    void drawText(String text, int x, int y); //Diubja un texto
    void clear(int color); //"Limpia" la pantalla
    void drawImage(IImage IImage, int posX, int posY, int height, int widht); //Dibuja una imagen
    void setColor(int color); //Establece un color

    void fillRectangle(int cX, int cY, int width, int height); //Dibuja un rectangulo con relleno

    void fillRoundRectangle(int cX, int cY, int width, int height, int arc); //Pinta un rectangulo con bordes redondeados y relleno

    void drawRectangle(int cX, int cY, int width, int height); //Metodo para pintar un rectangulo sin relleno
    void setStrokeWidth(int width); //Actualiza el grosor de la pintura a un determinado valor
    void drawRoundRectangle(int cX, int cY, int width, int height, int arc); //Metodo para pintar un rectangulo con bordes redondeados sin relleno

    void drawLine(int initX, int initY, int endX, int endY); //Metodo para pintar una linea recta

    void drawCircle(int cx, int cy, int radius);    //Metodo para pintar un circulo

    int getWidth_(); //Metodo para obtener el ancho
    int getHeight_(); //Metodo para obtener la altura
    void translate(float x,float y); //Metodo para desplazar graficos
    void scale(float x,float y); //Metodo para escalar graficos
    void save();
    void restore();
    void resize(float sceneWidth, float sceneHeight); //Metodo para reescalar graficos
    public float getScale_(); //Metodo para obtener la escala
    public float getTranslateX_(); //Metodo para obtener el desplazamiento en X
    public float getTranslateY_(); //Metodo para obtener el desplazamiento en Y



}
