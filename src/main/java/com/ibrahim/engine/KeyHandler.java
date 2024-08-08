package com.ibrahim.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp ;
    public KeyHandler(GamePanel gp) {
        this.gp = gp ;
    }
    public boolean upPressed, downPressed, leftPressed, rightPressed, openObject, enterPressed;

    public boolean scrollUp, scrollDown ;
    public void setDefault() {
        upPressed = false ;
        downPressed = false ;
        leftPressed = false ;
        rightPressed = false ;
        openObject = false ;
        enterPressed = false ;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();      // Returning ASCII Code

        if (code == KeyEvent.VK_ENTER) {
            if (enterPressed) {
                enterPressed = false ;
            } else {
                enterPressed = true ;
            }
        }

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.UserInterface.commandNum > 0) {
                    gp.UserInterface.commandNum-- ;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.UserInterface.commandNum < 2) {
                    gp.UserInterface.commandNum++ ;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.UserInterface.commandNum) {
                    case 0:
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        setDefault();
                        break;
                    case 1:
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        } else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true ;

            }
            if (code == KeyEvent.VK_S) {
                downPressed = true ;

            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true ;

            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true ;
            }
            if (code == KeyEvent.VK_Q) {
                openObject = true ;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_UP) {
                scrollUp = false ;
            }
            if (code == KeyEvent.VK_DOWN) {
                scrollDown = false ;
            }
        } else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = false ;

            }
            if (code == KeyEvent.VK_S) {
                downPressed = false ;

            }
            if (code == KeyEvent.VK_A) {
                leftPressed = false ;

            }
            if (code == KeyEvent.VK_D) {
                rightPressed = false ;
            }
            if (code == KeyEvent.VK_Q) {
                openObject = false ;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState ;

            }

        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_R) {
                gp.restart() ;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState ;
            }
        }
    }
}
