package com.ibrahim.object;

import com.ibrahim.engine.CollisionDetector;
import com.ibrahim.engine.GamePanel;
import com.ibrahim.engine.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;

public class Door extends ObjectParent {

    public Door() {
        name = "Door" ;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door_iron.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX
                    , screenY
                    , gp.tileSize
                    , gp.tileSize
                    , null);
        }
    }
}