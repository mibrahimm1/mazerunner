package com.ibrahim.engine;

import com.ibrahim.object.*;

public class AssetLoader {
    GamePanel gp ;
    public static int noOfObjects ;

    public AssetLoader(GamePanel gp) {
        this.gp = gp ;
    }


    public void setObject() {
        Key key = new Key() ;
        Door door = new Door() ;
        Chest chest = new Chest() ;
        Boots boots = new Boots() ;

        addObject(new Key(), 6, 32);
        addObject(new Key(), 6, 63);
        addObject(new Key(), 27, 62);
        addObject(new Key(), 36, 58);
        addObject(new Key(), 40, 26);
        addObject(new Key(), 44, 6);
        addObject(new Key(), 59, 54);
        addObject(new Key(), 64, 48);
        addObject(new Key(), 18, 55);


        addObject(new Door(), 59,56);
        addObject(new Door(), 59,52);
        addObject(new Door(), 62,41);
        addObject(new Door(), 54,41);
        addObject(new Door(), 57,17);
        addObject(new Door(), 40,46);
        addObject(new Door(), 27,60);
        addObject(new Door(), 20,47);
        addObject(new Door(), 15,56);
        addObject(new Door(), 15,54);
        addObject(new Door(), 10,31);
        addObject(new Door(), 14,15);
        addObject(new Door(), 12,10);


        addObject(new Chest(), 12,8);

        addObject(new Boots(),5,48);

        addObject(new Pit(), 55,20);
    }

    public void addObject(ObjectParent object, int x, int y) {
        gp.obj[noOfObjects] = object ;
        gp.obj[noOfObjects].worldX = x * gp.tileSize ;
        gp.obj[noOfObjects].worldY = y * gp.tileSize ;
        noOfObjects++ ;
    }
}
