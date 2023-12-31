package com.example.engine;

import java.io.IOException;

public interface IGraphics {
    IImage newImage(String name) ; //Crea una nueva imagen

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






}
