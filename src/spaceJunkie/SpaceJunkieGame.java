// SpaceJunkieGame.java

/*
 * This is the SpaceJunkieGame class.
 * This is the actual game code.
 * Nothing else is in this file.
 * Its sole purpose is to provide the game
 * methods so it can be invoked by other classes,
 * specifically the GameCode class.
 */
package spaceJunkie;
// import required packages--------------------
import gameComponents.GameInput;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import menu.HighScoreScreen;
import menu.MainMenu;
import physicsEngine.*;
//---------------------------------------------

public class SpaceJunkieGame {
    //-----------------------------------------------------------
    // VARIABLES
    //-----------------------------------------------------------

    // screen dimensions
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;
    static double playerSpeed; // the speed in which the player moves
    static int playerLives; // number of times a player can die
    static Ship player; // the player
    static Physics JUNKIE_PHYSICS;
    static int waveNum; // the current wave number
    static boolean waveSwitch = false; // checks if the wave is in the process of being switched
    static float temp; //  temporary storage variable
    static float gameTime; // the time
    static Wave wave; // the enemies
    static PhysicsObject[] star = new PhysicsObject[100]; // star background
    static final Font fontSmall = new Font(Font.DIALOG, Font.PLAIN, 10);
    static boolean debug; // debugger
    static int playerShots; // number of shots fired
    static int playerHits; // number shots that hit
    //-----------------------------------------------------------
    // END VARIABLES
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // THE GAME METHODS
    //-----------------------------------------------------------
    public static void init(int width, int height) { // initializes the game
        // set variables
        debug = false;
        if (debug) // if debug is on
        {
            System.out.println("\nENTERED DEBUG MODE\n");
        }

        // set width and height
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        // set the physics, nothing fancy, just gives access to collision detection methods
        JUNKIE_PHYSICS = new Physics(.98, 1, .4, 0, SCREEN_WIDTH,
                SCREEN_HEIGHT);

        for (int i = 0; i < star.length; i++) { // for each star
            // set star values
            star[i] = new PhysicsObject((int) (Math.random() * SCREEN_WIDTH), (int) (Math.random() * SCREEN_HEIGHT), (Math.random() * 3) + .1);
            star[i].setColor(Color.GRAY);
        }
        gameTime = 0;
        temp = 0;
        //--------------------------------------------------------------
        // set up the shape of the ship
        Point[] shipPoints = new Point[1]; // only one point is needed
        shipPoints[0] = new Point(0, 0); // the shape will be a circle with one central point

        playerSpeed = 2; // set speed to 2
        playerLives = 3; // set lives to 3

        // sets up the ship vars
        player = new Ship(shipPoints, new Point(100, SCREEN_HEIGHT - 10), 1, 5, 10); // create splayer ship
        player.setColor(255, 165, 0); // set color
        player.setWeapon(20, 3, 1, new eVector(7, 7)); // create a new weapon
        player.shipWeapon.setProjectileColor(new Color(0, 255, 255)); // set projectile color
        player.shipWeapon.setHeatRate(10); // set the rate at which weapon heats up
        //---------------------------------------------------------------
        waveNum = 1; // set wave number to 1
        switchWaves();  // switches to corresponding wave
        wave.init(); // initialize the wave
    }

    public static void run() {
        // this must be run in a loop
        getInput(); // receive input
        applyPhysics(); // apply the physics calculations
        applyGameCalculations(); // apply the game calculations
    }

    public static void draw(Graphics g) { // draws stuff
        // everything here draws the indicated thing
        g.setFont(fontSmall);
        if (debug) {
            drawDebugInfo(g);
        }
        drawBackground(g);
        drawInfo(g);
        wave.draw(g);
        if (player.isDead && playerLives > 0) { // if player is dead
            if ((int) (gameTime * 1000) % 5 == 0) // makes flashing animation
            {
                drawPlayer(g, Color.WHITE, (int) player.getPosition().getX(), (int) player.getPosition().getY());
            }
        } else if (playerLives > 0) { // draws player normally
            drawPlayer(g, player.getColor(), (int) player.getPosition().getX(), (int) player.getPosition().getY());
        }
    }

    //-----------------------------------------------------------
    // END GAME METHODS
    //-----------------------------------------------------------
    private static void applyGameCalculations() {
        if (waveSwitch && gameTime - temp > 1.1) { // switch wave after certain time has passed
            waveNum++; // move up a wave
            switchWaves(); // switch to corresponding wave
            wave.init(); // initialize wave
            temp = 0; // reset temp
            if (debug) {
                System.out.println("\nSwitched to wave " + waveNum + "\n");
            }
        }
        if (!waveSwitch) { // if wave not being switched
            for (int i = 0; i < wave.numEnemies; i++) {
                if (wave.enemies[i].isDestroyed) { // check if enemies have been destroyed
                    waveSwitch = true; // wave starts to switch
                } else {
                    waveSwitch = false;
                    break;
                }
            }
            if (waveSwitch) {
                temp = gameTime; // start the timer
            }
        }

        if (gameTime - temp > .5 && playerLives > 0) { // if player is dead but game is not over
            player.isDead = false; // resets player
        }

        if (playerLives <= 0 && gameTime - temp > 1) { // if game is over
            MainMenu.mode = 6; // set menu to high score
            HighScoreScreen.init((int) player.score, (int) (((double) playerHits / (double) playerShots) * (int) player.score));
            init(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        gameTime += .001;
    }

    private static void getInput() {
        // mouse------------------------------------------------------------------
        // moves player's x position to the position of the mouse
        if (GameInput.mousePosition.getX() > player.getPosition().getX()) {
            player.setXposition(player.getPosition().getX() + playerSpeed);
        }
        if (GameInput.mousePosition.getX() < player.getPosition().getX()) {
            player.setXposition(player.getPosition().getX() - playerSpeed);
        }
        if (GameInput.mouseButton1 && !player.isDead && playerLives > 0) {
            player.shipWeapon.fire(3, 270);
            playerShots += 1;
            GameInput.mouseButton1 = false;
        }
        //------------------------------------------------------------------------
        if (GameInput.EXIT) { // exit if player presses escape
            MainMenu.mode = 0;
            init(SCREEN_WIDTH, SCREEN_HEIGHT);
        }
    }

    private static void applyPhysics() {
        // PHYSICS----------------------------------
        player.applyShipPhysics(JUNKIE_PHYSICS);
        try { // try to move and fire wave
            wave.move(JUNKIE_PHYSICS); // move the wave
            wave.fire(); // fire enemy weapons
        } catch (Exception e) { // if there is an error in wave
            // reset wave number to 1
            waveNum = 1;
            wave = new Wave1();
            wave.init();
        }
        //-------------------------------------------

        // COLLISION DETECTION-------------------------------------------------------------------

        // check for collision between screen edges and player-------
        if (player.getPosition().getX() > SCREEN_WIDTH - 5) {
            player.setXposition(SCREEN_WIDTH - 5); // right edge
        }
        if (player.getPosition().getX() < 5) {
            player.setXposition(5); // left edge
        }
        //-----------------------------------------------------------

        // checks for collisions between enemy and player-----------------------------------------
        for (int i = 0; i < wave.numEnemies; i++) {
            for (int j = 0; j < player.shipWeapon.clipSize; j++) {
                // check for collision between player projectiles and enemies
                if (Physics.distance(player.shipWeapon.projectile[j].getPosition(), wave.enemies[i].getPosition()) < wave.enemySize / 2.0 + 3.5 && !wave.enemies[i].isDestroyed) {
                    wave.enemies[i].hp -= (int) player.shipWeapon.damage; // enemies take damage
                    if (wave.enemies[i].hp <= 0) { // if enemy health is zero
                        wave.enemies[i].isDestroyed = true; // destroy enemy
                        wave.enemies[i].explode(25); // create explosion
                        if (debug) {
                            System.out.println("Destroyed enemy " + (i + 1));
                        }
                    }

                    player.shipWeapon.resetProjectile(j, (int) player.getPosition().getX(), (int) player.getPosition().getX()); // reset projectile
                    player.score += 10 * waveNum; // add to score
                    playerHits++; // add hit
                }
                for (int k = 0; k < wave.enemies[i].alienWeapon.clipSize; k++) {
                    // check for collision between player and enemy projectiles
                    if (Physics.distance(player.shipWeapon.projectile[j].getPosition(), wave.enemies[i].alienWeapon.projectile[k].getPosition()) < 8) {
                        wave.enemies[i].alienWeapon.resetProjectile(k, (int) wave.enemies[i].getPosition().getX(), (int) wave.enemies[i].getPosition().getY());
                        player.shipWeapon.resetProjectile(j, (int) player.getPosition().getX(), (int) player.getPosition().getX());
                    }

                    // check for collision between enemy projectile and player
                    if (Physics.distance(player.getPosition(), wave.enemies[i].alienWeapon.projectile[k].getPosition()) < 15 && !player.isDead && playerLives > 0) {
                        player.isDead = true; // kill player
                        temp = gameTime; // start timer
                        playerLives--; // decrease lives
                        if (debug) {
                            System.out.println("Player died...");
                        }
                    }
                }
            }
        }
        //----------------------------------------------------------------------------------------


        // BACKGROUND--------------------------------------------
        // this moves the stars
        for (int i = 0; i < star.length; i++) {
            star[i].setYposition(star[i].getPosition().getY() + star[i].getMass()); // moves the stars, uses mass to determine speed
            if (star[i].getPosition().getY() > SCREEN_HEIGHT + 5) { // if star moves off screen
                star[i] = new PhysicsObject((int) (Math.random() * SCREEN_WIDTH), (int) (-Math.random() * 10), (Math.random() * 3) + .1);
            }
        }
        //--------------------------------------------
    }

    private static void switchWaves() { // switch to corresponding wave
        switch (waveNum) { // check the wave number
            case 1:
                wave = new Wave1();
                waveSwitch = false;
                break;
            case 2:
                wave = new Wave2();
                waveSwitch = false;
                break;
            case 3:
                wave = new Wave3();
                waveSwitch = false;
                break;
            case 4:
                wave = new Wave4();
                waveSwitch = false;
                break;
            case 5:
                wave = new Wave5();
                waveSwitch = false;
                break;
            case 6:
                wave = new Wave6();
                waveSwitch = false;
                break;
            case 7:
                wave = new Wave7();
                waveSwitch = false;
                break;
            case 8:
                wave = new Wave8();
                waveSwitch = false;
                break;
            case 9:
                wave = new Wave9();
                waveSwitch = false;
                break;
            default:
                waveNum = 0;
                break;
        }
    }

    //||||||||||||||||||||||||||||||||||||||||||||||||
    // DRAW ||||||||||||||||||||||||||||||||||||||||||
    //||||||||||||||||||||||||||||||||||||||||||||||||
    // these methods simply draw the indicated objects
    private static void drawDebugInfo(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("PLAYER LIVES: " + playerLives, 0, SCREEN_HEIGHT - 15);
        g.drawString("WAVE SWITCH: " + waveSwitch, 0, SCREEN_HEIGHT - 25);
        g.drawString("TEMP DIF: " + (gameTime - temp), 0, SCREEN_HEIGHT - 35);
        g.drawString("TEMP: " + (temp), 0, SCREEN_HEIGHT - 45);
        g.drawString("PLAYER SHOTS: " + playerShots, 0, SCREEN_HEIGHT - 55);
        g.drawString("PLAYER HITS: " + playerHits, 0, SCREEN_HEIGHT - 65);
        g.drawString("GAME TIME: " + gameTime, (int) (SCREEN_WIDTH / 2.0) - 55, SCREEN_HEIGHT - 5);
        g.drawString("> >   D E B U G    M O D E   < <", (int) (SCREEN_WIDTH / 2.0) - 75, (int) (SCREEN_HEIGHT / 2.0) - 40);
        if (GameInput.mouseButton1) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.gray);
        }
        g.drawString("MOUSE: " + GameInput.mousePosition.toString(), 0, SCREEN_HEIGHT - 5);
        wave.drawDebugInfo(g);
    }

    static void drawBackground(Graphics g) {
        // draws nice twinkling stars
        if (Math.random() < .1) {
            int c = (int) (Math.random() * 100); // random colour
            star[(int) (Math.random() * star.length)].setColor(new Color(c + 100 - (int) (Math.random() * 100), c + 100 - (int) (Math.random() * 100), c + 100 - (int) (Math.random() * 100)));
        }
        for (int i = 0; i < star.length; i++) {
            g.setColor(star[i].getColor());
            g.fillOval((int) star[i].getPosition().getX(), (int) star[i].getPosition().getY(), 2, 2);
        }
    }

    private static void drawInfo(Graphics g) { // draws info
        // INFO
        g.setColor(new Color(200, 0, 200));
        g.fillRect(0, 15, SCREEN_WIDTH + 2, 3);

        g.drawString("SCORE: ", 1, 11);
        g.drawString("WAVE: ", 150, 11);
        g.drawString("LIVES: ", 300, 11);
        g.drawString("GUN TEMPERATURE:", SCREEN_WIDTH - 220, 11);
        g.setColor(new Color(0, 180, 0));
        g.drawString("" + (int) player.score, 50, 11);
        g.drawString("" + waveNum, 190, 11);
        g.drawString("" + playerLives, 340, 11);

        g.setColor(new Color(0, 255, 0));
        g.fillRect(SCREEN_WIDTH - 101, 5, (int) (player.shipWeapon.maxHeat * 1.0), 4);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(SCREEN_WIDTH - 101, 5, (int) (player.shipWeapon.heat * 1.0), 4);
        if (player.shipWeapon.isOverheated) {
            if ((int) (gameTime * 1000) % 2 == 0) {
                g.setColor(new Color(255, 255, 255));
            } else {
                g.setColor(new Color(255, 0, 0));
            }
            g.fillRect(SCREEN_WIDTH - 101, 5, (int) (player.shipWeapon.maxHeat * 1.0), 4);
        }

        if (playerLives <= 0) {
            g.setColor(Color.RED);
            g.drawString("G A M E    O V E R", (int) (SCREEN_WIDTH / 2.0 - 45), (int) (SCREEN_HEIGHT / 2.0));
        } else if (player.isDead) {
            g.setColor(Color.YELLOW);
            g.drawString("YOU ARE DEAD", (int) (SCREEN_WIDTH / 2.0 - 30), (int) (SCREEN_HEIGHT / 2.0) - 12);
            g.drawString("YOU HAVE " + playerLives + " LIVES LEFT", (int) (SCREEN_WIDTH / 2.0 - 55), (int) (SCREEN_HEIGHT / 2.0));
        }

        if (waveSwitch) {
            g.setColor(new Color(0, 255, 0));
            g.drawString("PREPARE FOR WAVE " + (waveNum + 1) + " IN " + (int) ((1.1 - (gameTime - temp)) * 10), (int) (SCREEN_WIDTH / 2.0 - 65), (int) (SCREEN_HEIGHT / 2.0));
        }
    }

    private static void drawPlayer(Graphics g, Color c, int x, int y) {
        player.shipWeapon.drawGradient(g);

        g.setColor(new Color((int) (c.getRed() * .4), (int) (c.getGreen() * .4), (int) (c.getBlue() * .4)));
        g.fillOval(x - 6, y - 12, 5, 18);
        g.fillOval(x + 1, y - 12, 5, 18);
        g.fillOval(x - 8, y - 8, 16, 16);
        g.setColor(new Color((int) (c.getRed() * .6), (int) (c.getGreen() * .6), (int) (c.getBlue() * .6)));
        g.fillOval(x - 6, y - 6, 12, 12);
        g.setColor(new Color((int) (c.getRed() * .8), (int) (c.getGreen() * .8), (int) (c.getBlue() * .8)));
        g.fillOval(x - 4, y - 4, 8, 8);
        g.setColor(c);
        g.fillOval(x - 3, y - 3, 6, 6);
    }
    //||||||||||||||||||||||||||||||||||||||||||||||||
    // END DRAW ||||||||||||||||||||||||||||||||||||||
    //||||||||||||||||||||||||||||||||||||||||||||||||
}
