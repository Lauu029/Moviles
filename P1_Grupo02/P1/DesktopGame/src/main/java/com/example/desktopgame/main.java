package com.example.desktopgame;


import com.example.desktopengine.EngineDesktop;
import com.example.desktopengine.FileDesktop;
import com.example.gamelogic.Managers.AudioManager;
import com.example.gamelogic.Managers.FileManager;
import com.example.gamelogic.Managers.GameManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

public class main {
    static private JFrame myView_;

    public static void main(String[] args) {

        myView_ = new JFrame("Mastermind");
        myView_.setSize(600, 600);
        myView_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myView_.setIgnoreRepaint(true);
        myView_.setVisible(true);


        EngineDesktop IEngineDesktop = new EngineDesktop(myView_);
        FileDesktop IFileDesktop= new FileDesktop("Assets/Saved/saved_game.txt");
        FileManager.init(IFileDesktop);
        AudioManager.init();
        GameManager.init(IEngineDesktop,400,600);
        IEngineDesktop.resume();
    }

}
