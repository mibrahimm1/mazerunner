package com.ibrahim.engine;

import com.ibrahim.entity.Entity;

import java.awt.*;

public class CollisionDetector {
    GamePanel gp ;
    public CollisionDetector(GamePanel gp) {
        this.gp = gp ;
    }

    public void checkTileNPC(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        int speedOffset = entity.speed * 3; // Increase the offset for prediction

        switch (entity.direction) {
            case 0: // Up
                entityTopRow = (entityTopWorldY - speedOffset) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case 2: // Down
                entityBottomRow = (entityBottomWorldY + speedOffset) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case 1: // Left
                entityLeftCol = (entityLeftWorldX - speedOffset) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case 3: // Right
                entityRightCol = (entityRightWorldX + speedOffset) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
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
                entityTopRow = (entityTopWorldY - (entity.speed) ) /gp.tileSize;
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

    /*
    public int checkEntity(Entity entity, Entity[] targets) {
        int index = -1; // Use -1 to indicate no collision by default

        for (int i = 0; i < targets.length; i++) {
            Entity target = targets[i];
            if (target != null && target.collisionArea != null) {
                if (target.collisionOn) {
                    entity.collisionOn = true ;
                    index = i; // Set index to the NPC in collision
                    break; // Stop checking after finding a collision
                }
            }
        }
        return index; // Return the index of the colliding NPC, or -1 if no collision
    }

     */


    public int checkEntity(Entity entity, Entity[] targets) {
        int index = -1;

        for (int i = 0; i < targets.length; i++) {
            Entity target = targets[i];
            if (target != null && target.collisionArea != null) {
                Rectangle targetOriginalArea = new Rectangle(target.collisionArea);
                Rectangle playerOriginalArea = new Rectangle(entity.collisionArea);

                target.collisionArea = new Rectangle(
                        target.worldX + target.collisionArea.x,
                        target.worldY + target.collisionArea.y,
                        gp.originalTileSize * 3,
                        gp.originalTileSize * 4
                );

                entity.collisionArea = new Rectangle(
                        entity.worldX + entity.collisionArea.x,
                        entity.worldY + entity.collisionArea.y,
                        gp.originalTileSize * 3,
                        gp.originalTileSize * 4
                );

                switch (entity.direction) {
                    case 0: entity.collisionArea.y -= entity.speed; break;
                    case 2: entity.collisionArea.y += entity.speed; break;
                    case 1: entity.collisionArea.x -= entity.speed; break;
                    case 3: entity.collisionArea.x += entity.speed; break;
                }

                if (target.collisionArea.intersects(entity.collisionArea)) {
                    entity.collisionOn = true;
                    entity.playerCollision = true;
                    index = i; // Return the index of the colliding NPC
                    break;
                } else {
                    entity.playerCollision = false;
                }

                target.collisionArea = targetOriginalArea;
                entity.collisionArea = playerOriginalArea;
            }
        }

        return index;
    }


    public void checkPlayer(Entity entity) {
        Rectangle entityOriginalArea = new Rectangle(entity.collisionArea);
        Rectangle playerOriginalArea = new Rectangle(gp.player.collisionArea);

        entity.collisionArea = new Rectangle(
                entity.worldX + entity.collisionArea.x,
                entity.worldY + entity.collisionArea.y,
                gp.originalTileSize * 3,
                gp.originalTileSize * 4
        );

        gp.player.collisionArea = new Rectangle(
                gp.player.worldX + gp.player.collisionArea.x,
                gp.player.worldY + gp.player.collisionArea.y,
                gp.originalTileSize * 3,
                gp.originalTileSize * 4
        );

        switch (entity.direction) {
            case 0: entity.collisionArea.y -= entity.speed; break;
            case 2: entity.collisionArea.y += entity.speed; break;
            case 1: entity.collisionArea.x -= entity.speed; break;
            case 3: entity.collisionArea.x += entity.speed; break;
        }

        if (entity.collisionArea.intersects(gp.player.collisionArea)) {
            entity.playerCollision = true;

            if (!entity.directionSwitched) {
                switch (gp.player.direction) {
                    case 0: entity.direction = 2; break;
                    case 2: entity.direction = 0; break;
                    case 1: entity.direction = 3; break;
                    case 3: entity.direction = 1; break;
                }
                entity.directionSwitched = true;
            }
        } else {
            entity.playerCollision = false;
            entity.directionSwitched = false;
        }

        entity.collisionArea = entityOriginalArea;
        gp.player.collisionArea = playerOriginalArea;
    }

    public boolean checkEntityCollision(Entity entity, Entity target) {
        // Storing original positions and sizes
        Rectangle targetOriginalArea = new Rectangle(target.collisionArea);
        Rectangle playerOriginalArea = new Rectangle(entity.collisionArea);

        // Adjusting Collision Areas to be centered
        target.collisionArea = new Rectangle(
                target.worldX + target.collisionArea.x + (gp.originalTileSize - target.collisionArea.width) / 2,
                target.worldY + target.collisionArea.y + (gp.originalTileSize - target.collisionArea.height) / 2,
                gp.originalTileSize * 3 * 7 / 10,
                gp.originalTileSize * 4 * 7 / 10
        );

        entity.collisionArea = new Rectangle(
                entity.worldX + entity.collisionArea.x + (gp.originalTileSize - entity.collisionArea.width) / 2,
                entity.worldY + entity.collisionArea.y + (gp.originalTileSize - entity.collisionArea.height) / 2,
                gp.originalTileSize * 3 * 7 / 10,
                gp.originalTileSize * 4 * 7 / 10
        );

        // Temporarily move the entity's collision area (Predictive Collision Detection)
        switch (entity.direction) {
            case 0: // Up
                entity.collisionArea.y -= entity.speed;
                break;
            case 2: // Down
                entity.collisionArea.y += entity.speed;
                break;
            case 1: // Left
                entity.collisionArea.x -= entity.speed;
                break;
            case 3: // Right
                entity.collisionArea.x += entity.speed;
                break;
        }

        boolean collisionDetected = entity.collisionArea.intersects(target.collisionArea);

        // Reset positions
        target.collisionArea = targetOriginalArea;
        entity.collisionArea = playerOriginalArea;

        return collisionDetected;
    }

    public int checkEntityCollisions(Entity entity, Entity[] targets) {
        int i = 0 ;
        for (Entity target : targets) {
            if (target != null && checkEntityCollision(entity, target)) {
                entity.playerCollision = true;
                target.playerCollision = true;
                break;
            }
        }
        return i ;
    }

    public boolean checkPlayerCollision(Entity npc, Entity player) {
        return checkEntityCollision(npc, player);
    }
}
