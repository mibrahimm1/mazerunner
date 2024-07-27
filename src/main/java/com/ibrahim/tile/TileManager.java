package com.ibrahim.tile;

import com.ibrahim.engine.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePanel gp) {
        this.gp = gp ;
        tile = new Tile[10] ; // Types of Tiles (Textures)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile() ;
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile() ;
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true ;

            tile[2] = new Tile() ;
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true ;

            tile[3] = new Tile() ;
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile() ;
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true ;

            tile[5] = new Tile() ;
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String pathToFile) {
        try {
            InputStream is = getClass().getResourceAsStream(pathToFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0 ;
            int row = 0 ;

            while (row < gp.maxWorldRow) {
                // Populating String Array for the Map Textures
                String line = br.readLine();
                String numbers[] = line.split(" ") ;

                while (col < gp.maxWorldCol) {
                    // Converting each Texture into Integer Value
                    int num = Integer.parseInt(numbers[col]) ;
                    mapTileNum[col][row] = num ;
                    col++;
                }
                col = 0 ;
                row++ ;
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0 ;
        int worldRow = 0 ;

        while (worldRow < gp.maxWorldRow) {
            while (worldCol < gp.maxWorldCol) {
                int tileNum = mapTileNum[worldCol][worldRow] ;
                int worldX = worldCol * gp.tileSize ;
                int worldY = worldRow * gp.tileSize ;
                int screenX = worldX - gp.player.worldX + gp.player.screenX ;
                int screenY = worldY - gp.player.worldY + gp.player.screenY ;

                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                worldCol++;
            }
            worldCol = 0 ;
            worldRow++;
        }
    }
}
