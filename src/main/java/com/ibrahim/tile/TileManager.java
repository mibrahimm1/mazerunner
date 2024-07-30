package com.ibrahim.tile;

import com.ibrahim.engine.GamePanel;
import com.ibrahim.engine.UtilityTool;
import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePanel gp) {
        this.gp = gp ;
        tile = new Tile[50] ; // Types of Tiles (Textures)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldv4.txt");
    }

    public void getTileImage() {
        try {
            addTile(0, "grass00");
            addTile(1,"grass00");
            addTile(2,"grass00");
            addTile(3,"grass00");
            addTile(4,"grass00");
            addTile(5,"grass00");
            addTile(6, "grass00");
            addTile(7, "grass00");
            addTile(8, "grass00");
            addTile(9, "grass00");
            addTile(10, "grass00");
            addTile(11, "grass01");
            addTile(12, "water00", true);
            addTile(13, "water01", true);
            addTile(14, "water02", true);
            addTile(15, "water03", true);
            addTile(16, "water04", true);
            addTile(17, "water05", true);
            addTile(18, "water06", true);
            addTile(19, "water07", true);
            addTile(20, "water08", true);
            addTile(21, "water09", true);
            addTile(22, "water10", true);
            addTile(23, "water11", true);
            addTile(24, "water12", true);
            addTile(25, "water13", true);
            addTile(26, "road00");
            addTile(27, "road01");
            addTile(28, "road02");
            addTile(29, "road03");
            addTile(30, "road04");
            addTile(31, "road05");
            addTile(32, "road06");
            addTile(33, "road07");
            addTile(34, "road08");
            addTile(35, "road09");
            addTile(36, "road10");
            addTile(37, "road11");
            addTile(38, "road12");
            addTile(39, "earth");
            addTile(40, "wall", true);
            addTile(41, "tree", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTile(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool() ;
        try {
            tile[index] = new Tile() ;
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize) ;
            tile[index].collision = collision ;
        } catch (IOException e) {
            e.printStackTrace();;
        }

    }

    public void addTile(int index, String imageName) {
        UtilityTool uTool = new UtilityTool() ;
        try {
            tile[index] = new Tile() ;
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize) ;
            tile[index].collision = false ;
        } catch (IOException e) {
            e.printStackTrace();;
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
                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }
                worldCol++;
            }
            worldCol = 0 ;
            worldRow++;
        }
    }
}
