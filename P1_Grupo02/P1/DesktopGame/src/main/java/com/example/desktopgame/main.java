package com.example.desktopgame;


import com.example.desktopengine.EngineDesktop;
import com.example.gamelogic.Managers.GameManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class main {
    static private JFrame myView_;

    public static void main(String[] args) {

        myView_ = new JFrame("Mastermind");
        myView_.setSize(600, 600);
        myView_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myView_.setIgnoreRepaint(true);
        myView_.setVisible(true);
        myView_.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveDataOnClose();
            }
        });

        EngineDesktop IEngineDesktop = new EngineDesktop(myView_);
        GameManager.init(IEngineDesktop,400,600);
        GameManager.getInstance_().readData();
        IEngineDesktop.resume();
    }
    private static void saveDataOnClose() {
        // Lógica para guardar datos cuando la aplicación se cierra
        // Por ejemplo, llama a tu método saveData() de GameManager
        GameManager.getInstance_().saveData();
    }

}
