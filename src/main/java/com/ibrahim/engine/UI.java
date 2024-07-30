package com.ibrahim.engine;

import com.ibrahim.object.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Objects;

public class UI {
    GamePanel gp ;
    GraphicsEnvironment ge ;
    Font gameFont ;
    BufferedImage KeyIcon ;
    public boolean messageOn = false ;
    public boolean gameOver = false ;
    public int messageDelayCount ;
    public String message = "" ;

    double playTime = 0 ;

    public UI(GamePanel gp) {
        this.gp = gp ;
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/8bit16.ttf") ;
            gameFont = Font.createFont(Font.TRUETYPE_FONT,is).deriveFont(32f) ;
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (Exception e) {
            System.out.println("Font Not Found");
        }

        // Initialize KeyIcon with the Key's image
        Key key = new Key();
        KeyIcon = key.image;
    }

    public void showMessage(String message) {
        this.message = message ;
        messageOn = true ;
    }

    public void draw(Graphics2D g2) {
        if (gameOver) {
            String text ;
            int textLength ;
            int x ;
            int y ;
            g2.setFont(gameFont.deriveFont(25f));
            g2.setColor(Color.WHITE);
            text = "Treasure Found!" ;
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
            x = gp.screenWidth / 2 - textLength/2 ;
            y = gp.screenHeight / 2 - (gp.tileSize/2);
            g2.drawString(text, x, y);
            g2.setFont(gameFont.deriveFont(Font.BOLD, 30f));
            text = "YOU WON!" ;
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
            x = gp.screenWidth / 2 - textLength/2 ;
            y = gp.screenHeight / 2 + (gp.tileSize/2);
            g2.drawString(text, x, y);
            gameOver = true ;

        } else {
            playTime += (double) 1/60 ;
            DecimalFormat decimalFormat = new DecimalFormat("0.0") ;
            g2.setFont(gameFont) ;
            g2.setColor(Color.WHITE);
            g2.drawImage(KeyIcon,
                    gp.tileSize / 2,
                    gp.tileSize / 2,
                    gp.tileSize,
                    gp.tileSize,
                    null);
            g2.drawString(" x " + gp.player.keyCount , 74, 60);
            g2.setFont(gameFont.deriveFont(25f));
            g2.drawString(decimalFormat.format(playTime), gp.screenWidth - gp.tileSize * 2 , 60) ;
            if (messageOn) {
                g2.setFont(gameFont.deriveFont(20f));
                g2.drawString(message, gp.tileSize / 2, 150);
                messageDelayCount++ ;
                if (messageDelayCount > 120) {
                    messageDelayCount = 0 ;
                    messageOn = false ;
                }
            }
        }

    }
}
