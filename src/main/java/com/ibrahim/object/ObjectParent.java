package com.ibrahim.object;

import com.ibrahim.engine.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjectParent {
    public BufferedImage image ;
    public String name ;
    public boolean collision = false ;
    public int worldX, worldY ;

    public abstract void draw(Graphics2D g2, GamePanel gp) ;
}
