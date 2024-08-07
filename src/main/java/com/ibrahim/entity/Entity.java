package com.ibrahim.entity;

import com.ibrahim.engine.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp ;

    public int worldX, worldY ;
    BufferedImage spriteSheet;
    BufferedImage[][] playerSprites; // Array to store player sprites
    public int speed ;
    public Rectangle collisionArea ;
    public int collisionAreaDefaultX, collisionAreaDefaultY ;
    public boolean collisionOn ;
    final long spriteChangeInterval = 100;
    public int direction; // 0: UP, 2: DOWN, 1: LEFT, 3: RIGHT

    public boolean playerCollision = false ;

    public boolean directionSwitched ;


    public Entity(GamePanel gp) {
        this.gp = gp ;
        collisionArea = new Rectangle(0, 0, gp.originalTileSize * 3, gp.originalTileSize * 4);
    }

    public void update() {
    }

    public void draw(Graphics2D g2) {
    }
}
