package com.ibrahim.engine;

import javax.swing.*;
import java.awt.*;

import com.ibrahim.entity.Player;
import com.ibrahim.tile.TileManager;

public class GamePanel extends JPanel implements Runnable
{
    // SCREEN SETTINGS:
    public final int originalTileSize = 16 ;   // sub tile size
    final int scale = 3 ; // Scaling
    public final int tileSize = originalTileSize * scale ;  // 32 px
    public final int maxScreenCol = 16 ;
    public final int maxScreenRow = 12 ;
    public final int screenWidth = tileSize * maxScreenCol ; // 512 px
    public final int screenHeight = tileSize * maxScreenRow ; // 384 px

    // WORLD SETTINGS:
    public final int maxWorldCol = 50 ;
    public final int maxWorldRow = 50 ;
    public final int worldWidth = tileSize * maxWorldCol ;
    public final int worldHeight = tileSize * maxWorldRow ;


    // KEY HANDLER:
    KeyHandler keyH = new KeyHandler();

    // TILE MANAGER:
    public TileManager tileM = new TileManager(this);

    // COLLISION DETECTOR:
    public CollisionDetector collisionDetector = new CollisionDetector(this);

    // GAME CLOCK:
    Thread gameThread ;

    // PLAYER:
    public Player player ;


    // FRAMES PER SECOND:
    final int FPS = 60 ;



    // CONSTRUCTOR:
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player(this, keyH);

    }

    public void startGameThread() {
        gameThread = new Thread(this);      // Passing Game Panel to thread to instantiate
        gameThread.start();
    }


    @Override
    public void run() {
        // MAIN GAME ENGINE:
        // - UPDATING INFORMATION AND CHARACTER POSITION
        // - DRAWING THE SCREEN WITH UPDATED INFORMATION

        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint(); // Calls paintComponent()

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) (remainingTime / 1000000)); // Convert nanoseconds to milliseconds
                nextDrawTime += drawInterval;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {
        player.update();
    }

    // BUILT IN METHOD OF JPanel TO PAINT GRAPHICS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g ; // Converting Graphics to Graphics2D
        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();   // Saving Memory
    }
}
