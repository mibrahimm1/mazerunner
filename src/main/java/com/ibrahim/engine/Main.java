package com.ibrahim.engine;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Creating Application Frame
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Blue Boy Adventure");

        // Adding Main Panel to the Application Frame
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        // Displaying Game Frame
        window.setLocationRelativeTo(null) ; // Centering the Window
        window.setVisible(true);

        // Loading Assets
        gamePanel.loadObjects();

        // Starting the Game
        gamePanel.startGameThread();
    }
}