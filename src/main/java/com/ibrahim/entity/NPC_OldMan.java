package com.ibrahim.entity;

import com.ibrahim.engine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC_OldMan extends Entity {
    BufferedImage spriteSheet;
    BufferedImage[][] playerSprites; // Array to store player sprites
    int spriteIndex = 0 ; // To cycle through sprites for animation
    int playerWidth ;
    int playerHeight ;
    long lastSpriteChangeTime;
    int[] directions = {0, 3, 2, 0, 1, 2};
    int currentDirectionIndex = 0;

    public boolean directionSwitched ;


    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = 2 ;
        this.playerWidth = gp.originalTileSize * 3;
        this.playerHeight = gp.originalTileSize * 4;
        collisionArea = new Rectangle(20, 28, 18, 22);
        collisionAreaDefaultX = collisionArea.x ;
        collisionAreaDefaultY = collisionArea.y ;

        loadSpriteSheet();
        loadPlayerSprites();
        setDefaultValue();
    }

    public void setDefaultValue() {
        worldX = gp.tileSize * 7 ;
        worldY = gp.tileSize * 5 ;
        speed = 1 ;
        lastSpriteChangeTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/spritesheet-oldman.png")));
            System.out.println("Sprite sheet loaded successfully.");
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
            System.out.println("Loaded walking up sprite: " + i);
        }

        // Load walking left sprites (10th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[1][i] = spriteSheet.getSubimage(i * playerWidth, 1 * playerHeight, playerWidth, playerHeight);
            System.out.println("Loaded walking left sprite: " + i);
        }

        // Load walking down sprites (11th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[2][i] = spriteSheet.getSubimage(i * playerWidth, 2 * playerHeight, playerWidth, playerHeight);
            System.out.println("Loaded walking down sprite: " + i);
        }

        // Load walking right sprites (12th row)
        for (int i = 0; i < numSprites; i++) {
            playerSprites[3][i] = spriteSheet.getSubimage(i * playerWidth, 3 * playerHeight, playerWidth, playerHeight);
            System.out.println("Loaded walking right sprite: " + i);
        }
    }

    public void update() {
        boolean moved = false;
        collisionOn = false; // Reset collision flag before checking collision

        // Check Player Collision
        boolean playerCollision = gp.collisionDetector.checkEntityCollision(this, gp.player);

        if (playerCollision) {
            if (!directionSwitched) {
                // If collision with player, face the player and stop moving
                switch (gp.player.direction) {
                    case 0: // Player moving up
                        direction = 2; // NPC faces down
                        break;
                    case 2: // Player moving down
                        direction = 0; // NPC faces up
                        break;
                    case 1: // Player moving left
                        direction = 3; // NPC faces right
                        break;
                    case 3: // Player moving right
                        direction = 1; // NPC faces left
                        break;
                }
                directionSwitched = true ;
            }
            speed = 0; // Stop NPC movement
        } else {
            // No collision with player, resume normal movement
            speed = 1;
            // Continue with preset direction array logic
            direction = directions[currentDirectionIndex];
            directionSwitched = false ;
        }

        // Check Tile Collision
        gp.collisionDetector.checkTileNPC(this);

        if (!collisionOn && !playerCollision) {
            switch (direction) {
                case 0: // Up
                    worldY -= speed;
                    break;
                case 2: // Down
                    worldY += speed;
                    break;
                case 1: // Left
                    worldX -= speed;
                    break;
                case 3: // Right
                    worldX += speed;
                    break;
            }
            moved = true;
        } else {
            // Change direction if collided with something other than the player
            if (!playerCollision) {
                currentDirectionIndex = (currentDirectionIndex + 1) % directions.length;
            }
        }

        // Update sprite index if NPC moved
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
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(playerSprites[direction][spriteIndex],
                    screenX,
                    screenY,
                    playerWidth,
                    playerHeight,
                    null); // Drawing sprite with adjusted height
        }
    }
}
