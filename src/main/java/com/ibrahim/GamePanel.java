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

    // KEY HANDLER:
    KeyHandler keyH = new KeyHandler();

    // GAME CLOCK:
    Thread gameThread ;

    // PLAYER POSITION:

    int playerX = 100 ;
    int playerY = 100;

    int playerSpeed = 4 ;

    // FRAMES PER SECOND:
    final int FPS = 60 ;



    // CONSTRUCTOR:
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   // Better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);      // Passing Game Panel to thread to instantiate
        gameThread.start();
    }


    @Override
    public void run() {
        // MAIN GAME ENGINE:
        //      - UPDATING INFORMATION AND CHARACTER POSITION
        //      - DRAWING THE SCREEN WITH UPDATED INFORMATION

        double drawInterval = 1000000000/FPS ;
        double nextDrawTime = System.nanoTime() + drawInterval ;

        while (gameThread != null) {
            update();

            repaint(); // Used to Call paintComponent()



            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000000 ;
                if (remainingTime < 0) {
                    remainingTime = 0 ;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval ;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed ;
        }
        if (keyH.downPressed) {
            playerY += playerSpeed ;
        }
        if (keyH.leftPressed) {
            playerX -= playerSpeed ;
        }
        if (keyH.rightPressed) {
            playerX += playerSpeed ;
        }
    }

    // BUILT IN METHOD OF JPanel TO PAINT GRAPHICS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g ; // Converting Graphics to Graphics2D

        g2.setColor(Color.white);
        g2.fillRect(playerX,playerY,tileSize, tileSize);
        g2.dispose();   // Saving Memory
    }
}
