package com.ibrahim.entity;

import java.awt.Rectangle;

public class Entity {
    public int worldX, worldY ;
    public int speed ;
    public Rectangle collisionArea;
    public boolean collisionOn ;
    public int direction; // 0: UP, 2: DOWN, 1: LEFT, 3: RIGHT
}
