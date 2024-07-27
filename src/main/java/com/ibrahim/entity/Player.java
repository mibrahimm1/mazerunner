package com.ibrahim.entity;

import com.ibrahim.GamePanel;
import com.ibrahim.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH ;
    BufferedImage spriteSheet;
    BufferedImage[][] playerSprites; // Array to store player sprites
    int spriteIndex = 0; // To cycle through sprites for animation
    int direction = 2; // Default direction (down: 2, up: 0, left: 1, right: 3)
    int playerWidth ;
    int playerHeight ;

    long lastSpriteChangeTime;
    final long spriteChangeInterval = 100;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp ;
        this.keyH = keyH ;
        this.playerWidth = gp.originalTileSize * 3;
        this.playerHeight = gp.originalTileSize * 4;
        loadSpriteSheet();
        loadPlayerSprites();
        setDefaultValue();
    }

    public void setDefaultValue() {
        x = 100 ;
        y = 100 ;
        speed = 3 ;
        lastSpriteChangeTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/spritesheet.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayerSprites() {
        int numSprites = 9; // Number of sprites in each row
        playerSprites = new BufferedImage[4][numSprites]; // 4 rows for up, left, down, and right

        // Load walking up sprites (9th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[0][i] = spriteSheet.getSubimage(i * playerWidth, 0 * playerHeight, playerWidth, playerHeight);
        }

        // Load walking left sprites (10th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[1][i] = spriteSheet.getSubimage(i * playerWidth, 1 * playerHeight, playerWidth, playerHeight);
        }

        // Load walking down sprites (11th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[2][i] = spriteSheet.getSubimage(i * playerWidth, 2 * playerHeight, playerWidth, playerHeight);
        }

        // Load walking right sprites (12th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[3][i] = spriteSheet.getSubimage(i * playerWidth, 3 * playerHeight, playerWidth, playerHeight);
        }
    }

    public void update() {
        boolean moved = false;
        if (keyH.upPressed) {
            y -= speed ;
            direction = 0;
            moved = true;
        }
        if (keyH.downPressed) {
            y += speed ;
            direction = 2;
            moved = true;
        }
        if (keyH.leftPressed) {
            x -= speed ;
            direction = 1;
            moved = true;
        }
        if (keyH.rightPressed) {
            x += speed ;
            direction = 3;
            moved = true;
        }

        // Update sprite index if player moved
        if (moved) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastSpriteChangeTime >= spriteChangeInterval) {
                spriteIndex = (spriteIndex + 1) % playerSprites[direction].length;
                lastSpriteChangeTime = currentTime;
            }
        } else {
            spriteIndex = 0; // Reset to the first sprite if not moving
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(playerSprites[direction][spriteIndex], x, y, playerWidth, playerHeight, null); // Drawing sprite with adjusted height
    }
}
