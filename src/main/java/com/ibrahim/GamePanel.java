package com.ibrahim;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS:
    final int originalTileSize = 16 ;   // 16 x 16 px (player, graphics etc)
    final int scale = 3 ; // Scaling for bigger screens

    final int tileSize = originalTileSize * scale ;

    final int maxScreenCol = 16 ;
    final int maxScreenRow = 12 ;
    final int screenWidth = tileSize * maxScreenCol ; // 768 px
    final int screenHeight = tileSize * maxScreenRow ; // 576 px

    Thread gameThread ;     // Implementing Game Loop


    // CONSTRUCTOR:
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   // Better rendering performance

    }

    public void startGameThread() {
        gameThread = new Thread(this);      // Passing Game Panel to thread to instantiate
        gameThread.start();
    }


    @Override
    public void run() {
        // Main Game Loop

    }
}
