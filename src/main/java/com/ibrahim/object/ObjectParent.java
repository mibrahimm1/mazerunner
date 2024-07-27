package com.ibrahim.object;

import com.ibrahim.engine.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjectParent {
    public BufferedImage image ;
    public String name ;
    public boolean collision = false ;
    public int worldX, worldY ;
    public Rectangle collisionArea ;
    public int collisionAreaDefaultX = 0;
    public int collisionAreaDefaultY = 0;

    public abstract void draw(Graphics2D g2, GamePanel gp) ;
}
