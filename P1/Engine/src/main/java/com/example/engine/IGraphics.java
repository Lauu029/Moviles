package com.example.engine;

public interface IGraphics {
    IImage newImage(String name);

    IFont newFont(String filename, int size, boolean isBold);

    void clear(int color);

    void drawImage(IImage IImage, int posX, int posY, int height, int widht);

    void setcolor(int color);

    void fillRectangle(int cX, int cY, int width, int height);

    void fillRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawRectangle(int cX, int cY, int width, int height);
    void setStrokeWidth(int width);
    void drawRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawLine(int initX, int initY, int endX, int endY);

    void drawCircle(int cx, int cy, int radius);

    void drawText(String text, int x, int y, int size, IFont IFont);

    int getWidth();

    int getHeight();

    //void render();
    void translate(float x,float y);
    void scale(float x,float y);
    void save();
    void restore();
    void prepareFrame();
    void endFrame();

}
