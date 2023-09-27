package com.example.drawcircledesktop;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import javax.swing.JPanel;

public class drawCircleDesktop extends JFrame {
    private JPanel canvas;
    private Button clearButton;
    private Button drawButton;
    private boolean dibuja;
    public drawCircleDesktop() {
        setTitle("Dibujo del círculo");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                if (dibuja) {
                    //Dibuja el óvalo y lo rellena de color negro
                    gr.drawOval(60, 50, 70, 70);
                    gr.fillOval(60, 50, 70, 70);
                }
            }
        };

        drawButton = new Button("Draw");
        drawButton.setPreferredSize(new Dimension(70,20));
        drawButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                dibuja=true;
                canvas.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        clearButton= new Button("Clear");
        clearButton.setPreferredSize(new Dimension(70,20));
        clearButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                dibuja=false;
                canvas.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        Panel mainPanel= new Panel();
        mainPanel.add(clearButton);
        mainPanel.add(drawButton);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.NORTH);
    }
    public static void main(String[]args){
        drawCircleDesktop app= new drawCircleDesktop();
        app.setVisible(true);
    }

}