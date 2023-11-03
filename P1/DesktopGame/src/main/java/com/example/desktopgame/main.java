package com.example.desktopgame;


import com.example.desktopengine.EngineDesktop;
import com.example.gamelogic.GameManager;
import com.example.gamelogic.MenuScene;

import javax.swing.JFrame;

public class main {
    static private JFrame myView;

    public static void main(String[] args) {

        myView = new JFrame("Mastermind");
        myView.setSize(600, 600);
        myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myView.setIgnoreRepaint(true);
        myView.setVisible(true);
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                myView.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        } else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        EngineDesktop IEngineDesktop = new EngineDesktop(myView);
        GameManager.init(IEngineDesktop,400,600);
        MenuScene gm = new MenuScene(IEngineDesktop, 400, 600);
        IEngineDesktop.setScene(gm);
        gm.init();

        IEngineDesktop.resume();
    }
}
