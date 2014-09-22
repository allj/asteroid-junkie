// GameWindow.java

/* 
 * This is the GameWindow class.
 * It is responsible for opening a window in
 * which the GameScreen can be added. 
 */
//------------------------
// import required packages
package gameComponents;

import java.awt.*;
import java.applet.Applet;
import javax.swing.*;

@SuppressWarnings("serial") // suppress eclipse warnings
public class GameWindow extends Applet {

    // Screen width and height
    public static int SCREEN_WIDTH = 650;
    public static int SCREEN_HEIGHT = 500;
    // the frame that holds all other components
    static JFrame frame = new JFrame("ASTEROID JUNKIE");

    @Override
    public void init() { // the applet initializer method
        // this initializes the applet and adds all needed components
        this.setLayout(new BorderLayout()); // set layout to border layout
        GameScreen theGame = new GameScreen(); // create the game screen
        theGame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT); // sets the size of the game screen
        theGame.setVisible(true); // show the game screen
        this.add(theGame); // add the game screen
        frame.pack(); // pack in window around game screen
    }

    public static void main(String args[]) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make program terminate on exit
        Applet applet = new GameWindow(); // create this applet
        frame.add(applet);	// add it to the frame
        applet.init(); // initialize the applet
        frame.pack(); // pack in frame
        frame.setSize(new Dimension(SCREEN_WIDTH + 10, SCREEN_HEIGHT + 30)); // set the size of the frame
        frame.setResizable(false); // prevent user from resizing window
        frame.setVisible(true); // show the frame
    }
}




