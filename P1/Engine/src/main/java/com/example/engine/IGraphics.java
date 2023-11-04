package com.example.engine;

import java.io.IOException;

public interface IGraphics {
    IImage newImage(String name) throws IOException;

    IFont newFont(String fontname, int size, boolean isBold,boolean italic);
    void setFont(IFont font);
    void drawText(String text, int x, int y);
    void clear(int color);

    void drawImage(IImage IImage, int posX, int posY, int height, int widht);

    void setColor(int color);

    void fillRectangle(int cX, int cY, int width, int height);

    void fillRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawRectangle(int cX, int cY, int width, int height);
    void setStrokeWidth(int width);
    void drawRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawLine(int initX, int initY, int endX, int endY);

    void drawCircle(int cx, int cy, int radius);

    int getWidth_();
    int getHeight_();
    void translate(float x,float y);
    void scale(float x,float y);
    void save();
    void restore();


    void resize(float sceneWidth, float sceneHeight);
    public float getScale_();
    public float getTranslateX_();
    public float getTranslateY_();



}
