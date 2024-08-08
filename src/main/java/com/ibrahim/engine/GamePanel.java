package com.ibrahim.engine;

import javax.swing.*;
import java.awt.*;

import com.ibrahim.entity.Entity;
import com.ibrahim.entity.Player;
import com.ibrahim.object.ObjectParent;
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
    public final int maxWorldCol = 41 ;
    public final int maxWorldRow = 41 ;


    //---------------------------------UTILITIES--------------------------------------------

    // KEY HANDLER:
    public KeyHandler keyH = new KeyHandler(this);

    // TILE MANAGER:
    public TileManager tileM = new TileManager(this);

    // COLLISION DETECTOR:
    public CollisionDetector collisionDetector = new CollisionDetector(this);

    // SOUND MANAGER:
    public SoundManager soundManager = new SoundManager() ;

    // GAME CLOCK:
    Thread gameThread ;

    // GAME STATE:
    public int gameState ;
    public final int playState = 1 ;
    public final int pauseState = 2 ;
    public final int dialogueState = 3 ;
    public final int titleState = 4 ;
    public int stateDelay = 0 ;

    // FRAMES PER SECOND:
    final int FPS = 60 ;

    // UI:
    public UI UserInterface = new UI(this) ;

    //------------------------------ASSETS------------------------------------------------

    // ASSET LOADER
    public AssetLoader assetLoader = new AssetLoader(this);

    // PLAYER:
    public Player player ;

    // OBJECTS:
    public ObjectParent obj[] = new ObjectParent[50] ;

    // NPCs:
    public Entity npc[] = new Entity[10] ;

    // CONSTRUCTOR:
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player(this, keyH);

    }

    public void pregameSetup() {
        assetLoader.setObject();
        assetLoader.setNPC();
        gameState = titleState ;
    }

    public void startGameThread() {
        gameThread = new Thread(this);      // Passing Game Panel to thread to instantiate
        gameThread.start();
    }

    public void restart() {
        assetLoader.reloadAllObjects();
        player.worldX = tileSize * 40 ;
        player.worldY = tileSize * 21 ;
        gameState = playState ;
    }

    public void stopGameThread(boolean status) {
        if (status) {
            gameThread = null;
        }
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
                stopGameThread(UserInterface.gameOver);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {
        if (gameState == playState) {
            for (int i = 0 ; i < npc.length ; i++) {
                if (npc[i] != null) {
                    npc[i].update() ;
                }
            }
            player.update();
            player.resetInteractionFlag();
        } else if (gameState == dialogueState) {
            stateDelay++ ;
            if (!keyH.enterPressed) {
                stateDelay = 0 ;
                keyH.setDefault();
                gameState = playState ;
            }
            if (stateDelay > 180) {
                stateDelay = 0 ;
                keyH.setDefault();
                gameState = playState ;
            }
        }

    }

    // BUILT IN METHOD OF JPanel TO PAINT GRAPHICS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g ; // Converting Graphics to Graphics2D

        if (gameState == titleState) {
            UserInterface.draw(g2);
        } else {

            // TILES
            tileM.draw(g2);

            // OBJECTS
            for (int i = 0 ; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // NPCs
            for (int i = 0 ; i < npc.length ; i++) {
                if(npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // PLAYER
            player.draw(g2);

            // UI
            UserInterface.draw(g2) ;

            g2.dispose();   // Saving Memory
        }
    }

    public void playMusic(int i) {
        soundManager.setFile(i);
        soundManager.play() ;
        soundManager.loop() ;
    }

    public void stopMusic() {
        soundManager.stop();
    }

    public void playSFX(int i) {
        soundManager.setFile(i);
        soundManager.play();
    }
}
