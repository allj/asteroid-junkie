//GameCode.java

/*
 * This is the GameCode class.
 * This is where all game logic is put.
 * All methods here are invoked by the
 * GameScreen class.
 */
package gameComponents;

//~--- non-JDK imports --------------------------------------------------------
import asteroids.AsteroidsGame;

import menu.HighScoreScreen;
import menu.MainMenu;

import spaceJunkie.SpaceJunkieGame;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;

public class GameCode {

    private static int refreshDelay = 10;    // the amount of time in milliseconds to delay a thread

    static void init() {
        // initializes menu and games
        MainMenu.init(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
        SpaceJunkieGame.init(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
        AsteroidsGame.init(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
    }

    static void run() {
        // runs the game provided that the menu is in the correct mode
        switch (MainMenu.mode) {
            case 3:
                SpaceJunkieGame.run();
                refreshDelay = 5;
                break;
            case 4:
                AsteroidsGame.run();
                break;
            default:
                refreshDelay = 10;
                break;
        }
    }

    static void draw(Graphics g) {
        switch (MainMenu.mode) {    // draws the corresponding thing
            case 3:
                SpaceJunkieGame.draw(g);
                break;
            case 4:
                AsteroidsGame.draw(g);
                break;
            case 6:
                HighScoreScreen.draw(g);
                break;
            default:
                MainMenu.draw(g);
                break;
        }
    }

    static void delay() {
        try {
            // Stop thread for specified time
            Thread.sleep(refreshDelay);
        } catch (InterruptedException ex) {
            // do nothing
        }
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);    // set thread to max priority
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

