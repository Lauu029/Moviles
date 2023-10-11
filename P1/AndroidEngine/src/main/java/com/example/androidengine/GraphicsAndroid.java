package com.example.androidengine;

import com.example.engine.Color;
import com.example.engine.Font;
import com.example.engine.Image;

public class GraphicsAndroid implements com.example.engine.Graphics {
    private int width = 0, height = 0;

    @Override
    public ImageAndroid newImage(String name) {
        return null;
    }

    @Override
    public FontAndroid newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY, int height, int widht) {

    }

    @Override
    public void setcolor(Color color) {

    }

    @Override
    public void fillRectangle(int cX, int cY, int width, int height) {

    }

    @Override
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {

    }

    @Override
    public void drawRectangle(int cX, int cY, int width, int height) {

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
    public void drawText(String text, int x, int y, Font font) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
