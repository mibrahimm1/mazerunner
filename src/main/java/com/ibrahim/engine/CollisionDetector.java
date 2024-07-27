package com.ibrahim.engine;

import com.ibrahim.entity.Entity;

public class CollisionDetector {
    GamePanel gp ;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp ;
    }
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionArea.x ;
        int entityRightWorldX = entity.worldX +  entity.collisionArea.x +entity.collisionArea.width ;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y ;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize ;
        int entityRightCol = entityRightWorldX/gp.tileSize ;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2 ;

        switch(entity.direction) {
            case 0 :
                entityTopRow = (entityTopWorldY - entity.speed) /gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true ;
                }
                break ;
            case 2 :
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case 1 :
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case 3 :
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && gp.obj[i].collisionArea != null) {
                // GET ENTITY'S SOLID AREA POSITION
                entity.collisionArea.x = entity.worldX + entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.worldY + entity.collisionAreaDefaultY;

                // GET OBJECT'S SOLID AREA POSITION
                gp.obj[i].collisionArea.x = gp.obj[i].worldX + gp.obj[i].collisionAreaDefaultX;
                gp.obj[i].collisionArea.y = gp.obj[i].worldY + gp.obj[i].collisionAreaDefaultY;

                switch (entity.direction) {
                    case 0: // Up
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(gp.obj[i].collisionArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true ;
                            }
                            if (player) {
                                index = i ;
                            }
                        }
                        break;
                    case 2: // Down
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(gp.obj[i].collisionArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true ;
                            }
                            if (player) {
                                index = i ;
                            }
                        }
                        break;
                    case 1: // Left
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(gp.obj[i].collisionArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true ;
                            }
                            if (player) {
                                index = i ;
                            }
                        }
                        break;
                    case 3: // Right
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(gp.obj[i].collisionArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true ;
                            }
                            if (player) {
                                index = i ;
                            }
                        }
                        break;
                }

                // Reset positions
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                gp.obj[i].collisionArea.x = gp.obj[i].collisionAreaDefaultX;
                gp.obj[i].collisionArea.y = gp.obj[i].collisionAreaDefaultY;
            }
        }
        return index;
    }

}
