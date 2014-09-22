
//GameInput.java

/*
* This is the GameInput class.
* All input booleans are here.
* This class is used for input in
* all other classes that require it.
* The GameScreen class directly invokes
* the methods here for a particular listener
 */
package gameComponents;

//~--- non-JDK imports --------------------------------------------------------

import physicsEngine.Point;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.*;

public class GameInput {

    // the following code is just basic input stuff
    // it should all be self explanatory
    public static boolean P1LEFT        = false;
    public static boolean P1RIGHT       = false;
    public static boolean P1UP          = false;
    public static boolean P1FIRE        = false;
    public static boolean P1DOWN        = false;
    public static boolean P2LEFT        = false;
    public static boolean P2RIGHT       = false;
    public static boolean P2UP          = false;
    public static boolean P2FIRE        = false;
    public static boolean P2DOWN        = false;
    public static boolean EXIT          = false;
    public static boolean mouseButton1  = false;
    public static boolean mouseButton2  = false;
    public static boolean mouseButton3  = false;
    public static Point   mousePosition = new Point(0, 0);

    public static void keyTyped(KeyEvent e) {}

    public static void actionPerformed(ActionEvent e) {}

    public static void keyPressed(KeyEvent e) {

        // player2
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            P1LEFT = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            P1RIGHT = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            P1UP = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            P1DOWN = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            P1FIRE = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            P2LEFT = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            P2RIGHT = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            P2UP = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            P2DOWN = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            P2FIRE = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            EXIT = true;
        }
    }

    public static void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            P1LEFT = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            P1RIGHT = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            P1UP = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            P1DOWN = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            P1FIRE = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            P2LEFT = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            P2RIGHT = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            P2UP = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            P2DOWN = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            P2FIRE = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            EXIT = false;
        }
    }

    public static void mouseClicked(MouseEvent e) {}

    public static void mouseEntered(MouseEvent e) {}

    public static void mouseExited(MouseEvent e) {}

    public static void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            mouseButton1 = true;
        }

        if (e.getButton() == 2) {
            mouseButton2 = true;
        }

        if (e.getButton() == 3) {
            mouseButton3 = true;
        }
    }

    public static void mouseMoved(MouseEvent e) {
        mousePosition = new Point(e.getPoint().x, e.getPoint().y);
    }

    public static void mouseDragged(MouseEvent e) {
        mousePosition = new Point(e.getPoint().x, e.getPoint().y);
    }

    public static void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            mouseButton1 = false;
        }

        if (e.getButton() == 2) {
            mouseButton2 = false;
        }

        if (e.getButton() == 3) {
            mouseButton3 = false;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
