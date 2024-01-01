package com.example.desktopgame;


import com.example.desktopengine.EngineDesktop;
import com.example.gamelogic.Managers.GameManager;

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
        GameManager.init(IEngineDesktop,400,600);
        IEngineDesktop.resume();
    }

}
