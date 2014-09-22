
//GameScreen.java

/*
* This is the GameScreen class.
* Its sole purpose is to create a rectangular area that
* represents the screen that a game runs in.
* It contains several listener methods and
* creates a thread. The GameCode file is run within this
* class.
 */
package gameComponents;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameScreen extends Canvas
        implements Runnable, KeyListener, ActionListener, MouseListener, MouseMotionListener {
    private Graphics doubleBufferGraphics;    // the off screen graphics

    // double buffer variables
    private Image doubleBufferImage;    // the off screen image in which things can be drawn

    public GameScreen() {
        GameCode.init();

        // adds required listeners
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.requestFocus();

        Thread th = new Thread(this);    // creates thread

        th.start();    // Start thread
    }

    // -------------------------------------------------------------
    // Overridden methods
    // they are self explanatory

    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent e) {
        GameInput.mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GameInput.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GameInput.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GameInput.mouseReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        GameInput.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        GameInput.keyReleased(e);
    }

    // -----------------------------------------------------------

    public void run() {    // runs the game code
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);    // set thread to min priority

        while (true) {
            GameCode.run();            // run code
            this.repaint();            // repaint the screen
            GameCode.delay();          // delay thread
        }
    }

    public void paint(Graphics g) {    // draws game
        GameCode.draw(g);
    }

    // the double buffer method, used to eliminate flickering
    // it essentially draws the next frame of animation off screen first, then draws it on screen
    public void update(Graphics g) {

        // Initialize double buffer
        if (doubleBufferImage == null) {    // if there is no double buffer image
            doubleBufferImage    = createImage(this.getSize().width, this.getSize().height);    // creates an off screen image
            doubleBufferGraphics = doubleBufferImage.getGraphics();    // gets the graphics thing for the off screen image
        }

        // clears screen in background
        doubleBufferGraphics.setColor(getBackground());    // set the graphics colour to the background of this canvas
        doubleBufferGraphics.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // draws stuff in background
        doubleBufferGraphics.setColor(getForeground());
        paint(doubleBufferGraphics);    // paints things from the off screen image

        // draws image
        g.drawImage(doubleBufferImage, 0, 0, this);    // draws the off screen image to the current screen
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
