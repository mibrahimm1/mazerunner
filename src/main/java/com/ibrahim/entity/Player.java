package com.ibrahim.entity;

import com.ibrahim.engine.GamePanel;
import com.ibrahim.engine.KeyHandler;
import com.ibrahim.object.ObjectParent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH ;

    public final int screenX ;
    public final int screenY ;
    BufferedImage spriteSheet;
    BufferedImage[][] playerSprites; // Array to store player sprites
    int spriteIndex = 0 ; // To cycle through sprites for animation
    int playerWidth ;
    int playerHeight ;
    public boolean justInteracted = false;
    public int keyCount = 0 ;


    ArrayList<ObjectParent> inventory ;



    long lastSpriteChangeTime;
    final long spriteChangeInterval = 100;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp ;
        this.keyH = keyH ;
        this.playerWidth = gp.originalTileSize * 3;
        this.playerHeight = gp.originalTileSize * 4;
        direction = 2 ;
        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);
        inventory = new ArrayList<>();

        collisionArea = new Rectangle(20, 33, 18, 22);
        collisionAreaDefaultX = collisionArea.x ;
        collisionAreaDefaultY = collisionArea.y ;
        loadSpriteSheet();
        loadPlayerSprites();
        setDefaultValue();
    }

    public void setDefaultValue() {
        worldX = gp.tileSize * 61 ;
        worldY = gp.tileSize * 62 ;
        speed = 4 ;
        lastSpriteChangeTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/spritesheet.png")));
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
            direction = 0;
        }
        if (keyH.downPressed) {
            direction = 2;
        }
        if (keyH.leftPressed) {
            direction = 1;
        }
        if (keyH.rightPressed) {
            direction = 3;
        }

        // Check if any movement key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            moved = true;
            collisionOn = false; // Reset collision flag before checking collision

            // Check Tile Collision
            gp.collisionDetector.checkTile(this);

            // Checking Object Collission ;
            int objIndex = gp.collisionDetector.checkObject(this, true) ;

            objectAction(objIndex);

            if (!collisionOn) {
                // Move the player if no collision detected
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
            }
        } else {
            // No movement keys pressed, reset sprite index to 0
            spriteIndex = 0;
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

    public void objectAction(int index) {
        if (index != 999 && !justInteracted) {
            switch (gp.obj[index].name) {
                case "Key":
                    gp.playSFX(1);
                    keyCount++ ;
                    inventory.add(gp.obj[index]) ;
                    gp.obj[index] = null ;
                    justInteracted = true;
                    gp.UserInterface.showMessage("You got a key! Added to Inventory");
                    break ;
                case "Door":
                    boolean hasKey = false ;
                    int keyIndex = 0;
                    keyCount = 0 ;
                    int i = 0 ;
                    while (i < inventory.size()) {
                        ObjectParent obj = inventory.get(i) ;
                        if (obj.getClass().getSimpleName().equals("Key")) {
                            hasKey = true ;
                            keyIndex = i ;
                            keyCount++ ;
                        }
                        i++ ;
                    }
                    if (hasKey) {
                        gp.playSFX(3);
                        inventory.remove(keyIndex) ;
                        String message = "Used Key! Keys Left: " + --keyCount ;
                        gp.UserInterface.showMessage(message);
                        gp.obj[index] = null ;
                        justInteracted = true;
                        if (keyCount == 0) {
                            hasKey = false ;
                        }
                        collisionOn = false;
                    } else {
                        gp.UserInterface.showMessage("Denied Entry! You Don't Have Any Keys Left");
                        collisionOn = true ;
                    }
                    break ;
                case "Boots":
                    gp.playSFX(2);
                    inventory.add(gp.obj[index]) ;
                    gp.UserInterface.showMessage("Boot Obtained, Speed increased to " + speed);
                    this.speed++ ;
                    gp.obj[index] = null ;
                    justInteracted = true;
                    break ;
                case "Chest":
                    gp.UserInterface.gameOver = true ;
                    gp.stopMusic();
                    gp.playSFX(2);
                    break ;
                case "Pit":
                    gp.UserInterface.showMessage("You thought it was that easy! Try Again");
                    worldX = gp.tileSize * 61 ;
                    worldY = gp.tileSize * 62 ;
                    break ;

            }
        }
    }

    public void resetInteractionFlag() {
        justInteracted = false;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(playerSprites[direction][spriteIndex], screenX, screenY, playerWidth, playerHeight, null); // Drawing sprite with adjusted height
    }
}
