package com.ibrahim.engine;

import com.ibrahim.entity.Entity;
import com.ibrahim.entity.NPC_OldMan;
import com.ibrahim.object.*;

import java.util.Arrays;

public class AssetLoader {
    GamePanel gp ;
    public static int noOfObjects = 0 ;
    public static int noOfNPC = 0 ;

    public AssetLoader(GamePanel gp) {
        this.gp = gp ;
    }


    public void setObject() {
        addObject(new Chest(), 0, 15);

        addObject(new Door(), 2,27);
        addObject(new Door(), 8, 19);
        addObject(new Door(), 18, 21);
        addObject(new Door(), 16,  21);
        addObject(new Door(), 29, 12);
        addObject(new Door(), 38, 27);
        addObject(new Key(), 33, 35);
        addObject(new Key(), 39, 1);

        addObject(new Key(), 5,33);
        addObject(new Key(), 9,5);
        addObject(new Key(), 27,25);

        addObject(new Pit(), 15, 37);
        addObject(new Pit(), 23, 13);

        addObject(new Boots(), 21, 7);
    }

    public void setNPC() {
        addNPC(new NPC_OldMan(gp), 7, 7);
    }

    public void addNPC(Entity entity, int x, int y) {
        gp.npc[noOfNPC] = entity ;
        gp.npc[noOfNPC].worldX = x * gp.tileSize ;
        gp.npc[noOfNPC].worldY = y * gp.tileSize ;
        noOfNPC++ ;
    }

    public void addObject(ObjectParent object, int x, int y) {
        gp.obj[noOfObjects] = object ;
        gp.obj[noOfObjects].worldX = x * gp.tileSize ;
        gp.obj[noOfObjects].worldY = y * gp.tileSize ;
        noOfObjects++ ;
    }

    public void reloadAllObjects() {
        Arrays.fill(gp.obj, null);
        gp.player.inventory.clear();
        gp.player.keyCount = 0 ;
        noOfObjects = 0 ;
        setObject();
    }
}
