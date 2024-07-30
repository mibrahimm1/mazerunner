package com.ibrahim.engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        // Creating Application Frame
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setIconImage(ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/player/app-icon.png")))) ;
        window.setTitle("Maze Runner");

        // Adding Main Panel to the Application Frame
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        // Displaying Game Frame
        window.setLocationRelativeTo(null) ; // Centering the Window
        window.setVisible(true);

        // Loading Assets
        gamePanel.pregameSetup();

        // Starting the Game
        gamePanel.startGameThread();
    }
}