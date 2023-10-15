package com.example.engine;

public interface Graphics {
    Image newImage(String name);

    Font newFont(String filename, int size, boolean isBold);

    void clear(int color);

    void drawImage(Image image, int posX, int posY, int height, int widht);

    void setcolor(int color);

    void fillRectangle(int cX, int cY, int width, int height);

    void fillRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawRectangle(int cX, int cY, int width, int height);
    void setStrokeWidth(int width);
    void drawRoundRectangle(int cX, int cY, int width, int height, int arc);

    void drawLine(int initX, int initY, int endX, int endY);

    void drawCircle(float cx, float cy, float radius);

    void drawText(String text, int x, int y,int size,Font font);

    int getWidth();

    int getHeight();

    void render(Scene myScene);

}
