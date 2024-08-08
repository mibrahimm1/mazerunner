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
    Graphics2D g2 ;
    GraphicsEnvironment ge ;
    Font gameFont ;
    BufferedImage KeyIcon ;
    public boolean messageOn = false ;
    public boolean gameOver = false ;
    public int messageDelayCount ;
    public String message = "" ;
    public String dialogueText ;
    double playTime = 0 ;
    public int commandNum = 0 ;


    public void setDialogueText(String dialogueText) {
        this.dialogueText = dialogueText;
    }

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
        this.g2 = g2 ;
        g2.setFont(gameFont.deriveFont(Font.BOLD, 30f));

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen() ;
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
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
                g2.drawString(decimalFormat.format(playTime) + "s", gp.screenWidth - gp.tileSize * 2 , 60) ;
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

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

    }

    public void drawPauseScreen() {
        g2.setFont(gameFont.deriveFont(Font.BOLD, 50f));
        g2.setColor(Color.WHITE);
        String text = "PAUSED" ;
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
        int x = (gp.screenWidth / 2) - (length/2);
        int y = gp.screenHeight / 2 ;
        g2.drawString(text, x, y);

    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2 ;
        int y = gp.tileSize / 2 ;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 2 ;

        drawSubWindow(x,y,width,height);
        g2.setFont(gameFont.deriveFont(Font.PLAIN, 20f));
        g2.setColor(Color.BLACK);

        int stringX = x + ( gp.tileSize / 2 ) ;
        int stringY = y + ( gp.tileSize ) ;
        g2.drawString("\"" + dialogueText + "\"", stringX, stringY);
    }

    public void drawTitleScreen() {
        g2.setFont(gameFont.deriveFont(Font.BOLD, 60f));
        g2.setColor(Color.WHITE);

        // TITLE TEXT
        String text = "MazeRunner" ;
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.GRAY);
        g2.drawString(text,x + 4,y + 4);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        // BLUE BOY
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize ;
        g2.drawImage(gp.player.playerSprites[2][0], x, y, gp.player.playerWidth * 2, gp.player.playerHeight * 2, null );

        // MENU
        g2.setFont(gameFont.deriveFont(Font.PLAIN, 30f));

        text = "NEW GAME" ;
        x = getXforCenteredText(text) ;
        y += gp.tileSize * 4 ;
        g2.drawString(text,x,y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME" ;
        x = getXforCenteredText(text) ;
        y += gp.tileSize ;
        g2.drawString(text,x,y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT" ;
        x = getXforCenteredText(text) ;
        y += gp.tileSize ;
        g2.drawString(text,x,y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }



    }

    public void drawSubWindow(int x, int y, int width, int height) {
        // Set the color for the fill
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        // Set the color for the stroke
        g2.setColor(Color.BLACK); // You can choose any color for the stroke
        g2.setStroke(new BasicStroke(3)); // You can adjust the stroke width
        g2.drawRoundRect(x, y, width, height, 25, 25);
    }

    public void drawSubWindow(int x, int y, int width, int height, Color backgroundColor, Color strokeColor) {
        // Set the color for the fill
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, width, height, 25, 25);

        // Set the color for the stroke
        g2.setColor(strokeColor); // You can choose any color for the stroke
        g2.setStroke(new BasicStroke(3)); // You can adjust the stroke width
        g2.drawRoundRect(x, y, width, height, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth / 2) - (length / 2);
        return x;
    }
}
