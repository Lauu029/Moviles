package com.example.desktop;


import static java.awt.SystemColor.window;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MyClass {
    private static JTextField myText;
    public static void main(String[] args) {
        final JFrame principalFrame= new JFrame("App de escritorio");
        principalFrame.setSize(300,500);
        principalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        principalFrame.setVisible(true);
        Panel mainPanel= new Panel();
        principalFrame.setContentPane(mainPanel);
        myText= new JTextField();
        myText.setPreferredSize(new Dimension(100,20));
        JButton mainButton= new JButton("Send");
        mainButton.setPreferredSize(new Dimension(70,20));
        mainPanel.add(myText);
        mainPanel.add(mainButton);
        mainButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {


            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                JOptionPane.showMessageDialog(principalFrame, myText.getText());
        }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }
}