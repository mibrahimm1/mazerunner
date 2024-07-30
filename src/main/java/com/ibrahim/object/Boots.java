package com.ibrahim.object;

import com.ibrahim.engine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class Boots extends ObjectParent {
    public Boots() {
        name = "Boots" ;
        collisionArea = new Rectangle(8,8,32,32) ;
        collisionAreaDefaultX = collisionArea.x ;
        collisionAreaDefaultY = collisionArea.y ;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
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
            g2.drawImage(image, (screenX + gp.originalTileSize / 2)
                    , screenY + (gp.originalTileSize / 2)
                    , gp.originalTileSize * 2
                    , gp.originalTileSize * 2, null);
        }
    }
}

