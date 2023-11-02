package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGameObject;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.TouchEvent;

import java.io.IOException;
import java.util.ArrayList;

public class MenuScene implements IScene {

    private IEngine IEngine_;
    private ArrayList<IGameObject> IGameObjects_ = new ArrayList<>();
    private int width_, height_;
    private Button button;
    private IFont font;
    private IFont font_button;
    private IImage oreo_;
    private IImage image_;
    private ISound overSound_,alarmSound_;

    public MenuScene(IEngine IEngine, int w, int h) {
        IEngine_ = IEngine;
        width_ = w;
        height_ = h;
        System.out.print("Scene Width: " + width_ + " Scene Height: " + height_ + "\n");
    }
    @Override
    public void init()  {
        //creacion de la solucion
        IGraphics graph = IEngine_.getGraphics();
        this.font=graph.newFont("Hexenkoetel-qZRv1.ttf",40,true,true);
        graph.setFont(this.font);
        font_button=graph.newFont("Hexenkoetel-qZRv1.ttf",20,false,false);
        this.button = new Button("Jugar",font_button,0XFFFB839B
                ,150,50, 35,this.width_/2-150/2, this.height_/2+20,SceneNames.LEVEL);

        addGameObject(button);

        try {
            oreo_=graph.newImage("cerebro.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // this.image_=IEngine_.getGraphics().newImage("hola.png");
        overSound_=IEngine_.getAudio().newSound("over.wav","overMenu");
        alarmSound_=IEngine_.getAudio().newSound("alarm.wav","alarmMenu");
    }


    public void addGameObject(IGameObject gm) {
        IGameObjects_.add(gm);
    }

    @Override
    public int getHeight() {
        return height_;
    }

    @Override
    public int getWidth() {
        return width_;
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        if (events.size() != 0) {
            System.out.println("inpuuut");
            IEngine_.getAudio().playSound(overSound_,0);
            IEngine_.getAudio().playSound(alarmSound_,0);
        }
        for (IGameObject g : IGameObjects_) {
            for (TouchEvent event: events) {
                if(g.handleInput(event))
                    return;
            }

        }
    }

    @Override
    public void render() {
        IGraphics graph = IEngine_.getGraphics();

        //Dibujamos un color de fondo para la escena
        graph.clear(0xFFe3fcf3);



        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).render(graph);
        }
        graph.setColor(0XFF222222);
        this.IEngine_.getGraphics().setFont(font);
        graph.drawText("MasterMind", width_/2, 100);
        graph.drawImage(oreo_,this.width_/2-80/2, this.height_/2-140,80,80);
    }


    @Override
    public void update(double time) {
        for (int i = 0; i < IGameObjects_.size(); i++) {
            IGameObjects_.get(i).update(time);
        }
    }

}
