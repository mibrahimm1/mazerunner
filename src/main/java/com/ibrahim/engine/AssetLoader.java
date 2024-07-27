package com.ibrahim.engine;

import com.ibrahim.object.Chest;
import com.ibrahim.object.Door;
import com.ibrahim.object.Key;

public class AssetLoader {
    GamePanel gp ;

    public AssetLoader(GamePanel gp) {
        this.gp = gp ;
    }

    public void setObject() {
        gp.obj[0] = new Key() ;
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new Key() ;
        gp.obj[1].worldX = 28 * gp.tileSize ;
        gp.obj[1].worldY = 40 * gp.tileSize ;

        gp.obj[2] = new Key() ;
        gp.obj[2].worldX = 37 * gp.tileSize ;
        gp.obj[2].worldY = 7 * gp.tileSize ;

        gp.obj[3] = new Door() ;
        gp.obj[3].worldX = 10 * gp.tileSize ;
        gp.obj[3].worldY = 9 * gp.tileSize ;

        gp.obj[4] = new Door() ;
        gp.obj[4].worldX = 8 * gp.tileSize ;
        gp.obj[4].worldY = 28 * gp.tileSize ;

        gp.obj[5] = new Door() ;
        gp.obj[5].worldX = 12 * gp.tileSize ;
        gp.obj[5].worldY = 22 * gp.tileSize ;

        gp.obj[6] = new Chest() ;
        gp.obj[6].worldX = 10 * gp.tileSize ;
        gp.obj[6].worldY = 7 * gp.tileSize ;
    }
}
