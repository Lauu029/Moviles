package com.example.gamelogic;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.TouchEvent;
import com.example.gamelogic.Circles.HintsCircle;
import com.example.gamelogic.Circles.TryCircle;
import com.example.gamelogic.Managers.GameManager;

import java.util.ArrayList;

public class GameTry extends GameObject {
    private TryCircle[] tries_;
    private HintsCircle[] hints_;
    private int myTry_;
    private int renderTry;
    private int height_;
    private int screenWidth_;
    private int posX_ = 7;
    private int posY_ = 0;
    private int widthRectangle_;
    int solutionSize_;
    int hintsPosX_ = 0;
    int translateY_;
    int minY_, maxY_;
    private IFont fuente;

    GameTry(int solutionSize, int numTries, int height, int minY, int maxY) {
        tries_ = new TryCircle[solutionSize];
        hints_ = new HintsCircle[solutionSize];
        myTry_ = numTries;
        renderTry = myTry_ + 1;
        screenWidth_ = GameManager.getInstance_().getwidth();
        height_ = height;
        minY_ = minY;
        maxY_ = maxY;
        widthRectangle_ = screenWidth_ - (posX_ * 2);
        fuente = GameManager.getInstance_().getIEngine().getGraphics().newFont("Hexenkoetel-qZRv1.ttf", height, false, false);
        solutionSize_ = solutionSize;
    }
    public TryCircle[]getTries(){
        return tries_;
    }
    @Override
    public void init() {
        int offsetX = height_;
        int radius = (int) ((height_) / 2.5f);
        int spaceCircles = widthRectangle_ / 2;
        int dCircle = (spaceCircles) / (solutionSize_ + 1);
        int radius1 = dCircle / 2;
        int margen = (spaceCircles - (dCircle * solutionSize_)) / (solutionSize_ + 1);
        int x = 0;
        int StartX = screenWidth_ / 2 - ((radius * 2) * (solutionSize_ / 2));
        IFont font1_ = GameManager.getInstance_().getIEngine().getGraphics().newFont("Lexendt.ttf", 20, false, false);
        for (int i = 0; i < solutionSize_; i++) {
            tries_[i] = new TryCircle("" + (i + 1), font1_, radius, StartX + ((radius * 2 + 3) * i), posY_ + translateY_ + height_ / 2, myTry_, i);
            tries_[i].init();
        }
        hintsPosX_ = StartX + ((radius * 2 + 3) * (solutionSize_ - 1)) + radius + 15;

        //hints Render
        int columns = (int) Math.ceil((double) solutionSize_ / 2);
        int hintsRad = radius / 2;
        int hy = posY_ + translateY_ + height_ / 4;
        int j = 0;
        int i = 0;
        for (int s = 0; s < solutionSize_; s++) {
            if (j >= columns) {
                i++;
                j = 0;
            }
            hints_[s] = new HintsCircle("" + (i + 1), fuente, hintsRad, hintsPosX_ + hintsRad * 2 + (j * (hintsRad * 2 + 3)), hy + (i * (hintsRad * 2 + 3)), i);
            hints_[s].init();
            j++;
        }
    }

    public void TranslateY(int y) {
        translateY_ += y;
        for (int i = 0; i < solutionSize_; i++) {
            tries_[i].TranslateY(y);
            hints_[i].TranslateY(y);
        }
    }
    public void setIdTries(ArrayList<Integer> triesIds){
        for (int i = 0; i < solutionSize_; i++) {
            tries_[i].setIdColor_(triesIds.get(i));
        }
    }
    @Override
    public void update(double time) {
        for (int i = 0; i < solutionSize_; i++) {
            tries_[i].update(time);
        }
    }

    @Override
    public void render(IGraphics graph) {
        if(translateY_+height_ <= maxY_ && translateY_+height_>=minY_) {
            drawRectange(graph);
            drawTextLines(graph);
            drawCircles(graph);
            graph.setColor(0XFF222222);
            graph.setStrokeWidth(3);
            graph.drawLine(hintsPosX_, posY_ + 4 + translateY_, hintsPosX_, posY_ + translateY_ + height_ - 4);
        }
    }

    void drawCircles(IGraphics graph) {
        for (int i = 0; i < solutionSize_; i++) {
            tries_[i].render(graph);
            hints_[i].render(graph);
        }
    }

    void drawRectange(IGraphics graph) {
        graph.setStrokeWidth(2);
        graph.setColor(0XD0FB839B);
        graph.fillRoundRectangle(posX_, posY_ + translateY_, widthRectangle_, height_, 10);
        graph.setColor(0XFF222222);
        graph.drawRoundRectangle(posX_, posY_ + translateY_, widthRectangle_, height_, 10);
    }

    void drawTextLines(IGraphics graph) {
        graph.setFont(fuente);
        graph.drawText("" + renderTry, posX_ + 20, posY_ + translateY_ + (height_ / 3));
        graph.setStrokeWidth(3);
        graph.drawLine(posX_ + 40, posY_ + 4 + translateY_, posX_ + 40, posY_ + translateY_ + height_ - 4);
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        for (TryCircle try_ : tries_) {
            try_.handleInput(event);
        }
        return false;
    }

    void setGameTry(int t) {
        for (TryCircle try_ : tries_) {
            try_.setGameTry_(t);
        }
    }

    public void setNewHints(int correctPositions, int correctColors) {
        int pos = 0;

        for (int i = 0; i < correctPositions; i++) {
            hints_[pos].putHintColor(true);
            pos++;
        }
        for (int i = 0; i < correctColors; i++) {
            hints_[pos].putHintColor(false);
            pos++;
        }
    }

    public void putNewColor(int id, int color) {
        for (int i = 0; i < tries_.length; i++) {
            if (!tries_[i].hasColor()) {
                tries_[i].setColor(color, id);
                GameManager.getInstance_().putColorSolution(i, id);
                break;
            }
        }
    }
    public void putNewColorPosition(int id, int color,int pos) {

        tries_[pos].setColor(color, id);
        GameManager.getInstance_().putColorSolution(pos, id);

    }
    public int getButtonPosition(){
        return translateY_+height_;
    }
    public void changeDaltonics(boolean dalt) {
        for (TryCircle try_ : tries_) {
            try_.setDaltonics_(dalt);
        }
    }
}