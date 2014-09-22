
// This file is not part of actual Space Junkie game, which is my ISP
// pay no attention to this file
// This file is not part of actual Space Junkie game, which is my ISP
// pay no attention to this file
// This file is not part of actual Space Junkie game, which is my ISP
// pay no attention to this file
// This file is not part of actual Space Junkie game, which is my ISP
// pay no attention to this file
// This file is not part of actual Space Junkie game, which is my ISP
// pay no attention to this file

package asteroids;

import gameComponents.GameInput;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import menu.MainMenu;

import physicsEngine.Physics;
import physicsEngine.PhysicsObject;
import physicsEngine.Point;
import physicsEngine.Ship;
import physicsEngine.eVector;

public class AsteroidsGame {
	//****************************************************************************
	// THE VARIABLES 
	//****************************************************************************
	public static short SCREEN_WIDTH, SCREEN_HEIGHT;
	public static PhysicsObject[] STAR = new PhysicsObject[100]; // starry background
	public static Physics ASTEROID_PHYSICS;
	public static Ship PLAYER;
	public static Asteroid ASTEROID[] = new Asteroid[10], ASTEROID_FRAGMENT[][] = new Asteroid[ASTEROID.length][5];
	public static float HIGHSCORE, GAME_TIME, TEMP_TIME;
	public static Font FONT_SMALL = new Font(Font.DIALOG, Font.PLAIN, 10);
	//****************************************************************************
	// END VARIABLES 
	//****************************************************************************
	
	
	//****************************************************************************
	// THE GAME METHODS 
	//****************************************************************************
	public static void init(int width, int height) {
		SCREEN_WIDTH = (short)width;
		SCREEN_HEIGHT = (short)height;
		
		for (short i = 0;i< STAR.length;i++) {
			short c = (short)(Math.random()*100);
			STAR[i] = new PhysicsObject((short)(Math.random()*SCREEN_WIDTH),(short)(Math.random()*SCREEN_HEIGHT),(Math.random()*3)+.1);
			STAR[i].setColor(new Color(c+100-(short)(Math.random()*100),c+100-(short)(Math.random()*100),c+100-(short)(Math.random()*100)));
		}
		HIGHSCORE = 0;
		GAME_TIME = 0;
		//--------------------------------------
		// set up ship
		Point[] SpaceShipShape = new Point[3];
		SpaceShipShape[0] = new Point(0, 1);
		SpaceShipShape[1] = new Point(.5, -.5);
		SpaceShipShape[2] = new Point(-.5, -.5);
		PLAYER = new Ship(SpaceShipShape, new Point(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0), 3, 15, 2);
		PLAYER.setColor(0, 255, 255);
		PLAYER.setHp(100);
		PLAYER.setWeapon(30, 5, 20, new eVector(4, 4));
		PLAYER.shipWeapon.setHeatRate(8);
		PLAYER.shipWeapon.setCoolRate(.1);
		PLAYER.shipWeapon.setProjectileColor(new Color(0, 255, 0));
		PLAYER.setOrientation(90);
		SpaceShipShape = null;
		//--------------------------------------
		
		
		ASTEROID_PHYSICS = new Physics(.98, 1, .4, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		for (short i = 0; i < ASTEROID.length; i++) {
			ASTEROID[i] = newAsteroid();
			for (short d = 0; d < ASTEROID_FRAGMENT[i].length; d++) {
				ASTEROID_FRAGMENT[i][d] = newDestroyedRoid(new Point(-1000,0));
				ASTEROID_FRAGMENT[i][d].setVelocity(0, 0);
			}
		}
	}

	public static  void run() {
		getInput();
		applyPhysics();
		applyGameCalculations();
	}
	
	public static void draw(Graphics g) {	
		drawBackground(g);
		if(PLAYER.isDead) { // if player is dead
			if((int)(GAME_TIME*1000) % 2 ==0) { // makes flashing animation
				drawPlayer(g);
			}
		} else {
			drawPlayer(g);
		}
		drawAsteroids(g);
		drawInfo(g);
	}
	
	//****************************************************************************
	// END GAME METHODS 
	//****************************************************************************
	
	static void applyPhysics() {
		PLAYER.applyShipPhysics(ASTEROID_PHYSICS);
		collisionDetection(PLAYER);
		
		for (short i = 0; i < ASTEROID.length; i++) {
			ASTEROID[i].applyRotationRate();
			ASTEROID[i].applyPhysics(ASTEROID_PHYSICS, 'f');
			if (ASTEROID[i].getPosition().getX() > SCREEN_WIDTH + 150
					|| ASTEROID[i].getPosition().getY() > SCREEN_HEIGHT + 150
					|| ASTEROID[i].getPosition().getX() < -150
					|| ASTEROID[i].getPosition().getY() < -150
					|| ASTEROID[i].hp <= 0) {
				ASTEROID[i] = newAsteroid();
			}

			for (short d = 0; d < ASTEROID_FRAGMENT[i].length; d++) {
				if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID_FRAGMENT[i][d])) {
					ASTEROID_FRAGMENT[i][d].applyRotationRate();
					ASTEROID_FRAGMENT[i][d].applyPhysics(ASTEROID_PHYSICS, 'f');
				}
			}
		}
	}
	
	static void applyGameCalculations() {
		if (PLAYER.score > HIGHSCORE) {
			HIGHSCORE = PLAYER.score;
		}
		if (GAME_TIME - TEMP_TIME >.5) { // if player is dead but game is not over
			PLAYER.isDead = false; // resets player
		}
		GAME_TIME +=.001;
		PLAYER.score += .04;
	}
	
	static void getInput() {
		if (GameInput.EXIT) {
			MainMenu.mode = 0;
			init(SCREEN_WIDTH, SCREEN_HEIGHT);
		}

		if (GameInput.P2LEFT) {
			PLAYER.setAngle(PLAYER.getAngle() - 1.5);
		}
		if (GameInput.P2RIGHT) {
			PLAYER.setAngle(PLAYER.getAngle() + 1.5);
		}
		if (GameInput.P2UP) {
			PLAYER.applyThrust(.04, PLAYER.getDirection());
		}
		if (GameInput.P2FIRE) {
			PLAYER.shipWeapon.fire(5, PLAYER.getDirection());
		}
	}
	
	//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// DRAW METHODS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	
	static void drawInfo(Graphics g) {
		g.setFont(FONT_SMALL);
		g.setColor(new Color(150, 150, 150));
		g.fillRect(1, 14, 125, 5);
		g.setColor(Color.WHITE);
		g.drawString("SCORE: " +(short) PLAYER.score, 1, 11);
		g.setColor(PLAYER.getColor());
		g.fillRect(1, 14, (short)( PLAYER.hp*1.25), 5);

		g.setColor(new Color(0, 255, 0));
		g.fillRect(1, 20, (short) (PLAYER.shipWeapon.maxHeat*1.25), 5);
		g.setColor(new Color(255, 0, 0));
		g.fillRect(1, 20, (short) (PLAYER.shipWeapon.heat*1.25), 5);
		if (PLAYER.shipWeapon.isOverheated) {
			if ((short)(GAME_TIME*1000) % 2 ==0)
				g.setColor(new Color(255, 255, 255));
			else			
				g.setColor(new Color(255, 0, 0));
			g.fillRect(1, 20, (short) (PLAYER.shipWeapon.maxHeat*1.25), 5);
		}
		g.setColor(new Color(155, 155, 155));
		g.drawString("HIGH SCORE: " + (short)HIGHSCORE, 1, 35);
	}
	
	static void drawAsteroids(Graphics g) {
		for (short i = 0; i < ASTEROID.length; i++) {
			if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID[i])) {
				ASTEROID[i].drawWireframe(g);
			}
			for (short d = 0; d < ASTEROID_FRAGMENT[i].length; d++) {
				if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID_FRAGMENT[i][d])) {
					ASTEROID_FRAGMENT[i][d].drawWireframe(g);
				}
			}
		}
	}
	
	static void drawPlayer(Graphics g) {
		PLAYER.drawWireframe(g);
		PLAYER.shipWeapon.draw(g);
		if (GameInput.P2UP) {
			PLAYER.drawThrust(g);
		}	
	}
	
	static void drawBackground(Graphics g) {
		// draws nice twinkling stars
		float random = (float)Math.random();
		if (random < .025) {
			STAR[(short)(Math.random()*STAR.length)].setColor(255,255,255);
		} else if (random <.1) {
			short c = (short)(Math.random()*100); // random colour
			STAR[(short)(Math.random()*STAR.length)].setColor(new Color(c+100-(short)(Math.random()*100),c+100-(short)(Math.random()*100),c+100-(short)(Math.random()*100)));
		}
		for (short i = 0;i< STAR.length;i++) {
			g.setColor(STAR[i].getColor());
			g.fillOval((short)STAR[i].getPosition().getX(),(short)STAR[i].getPosition().getY(),2,2);
		}
	}
	//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// END DRAW METHODS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	//|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

	static void collisionDetection(Ship object) {
		if (object.hp <= 0) {
			TEMP_TIME = GAME_TIME;
			PLAYER.isDead = true;
			shipReset(object,
					(short) (SCREEN_WIDTH / 2.0),
					(short) (SCREEN_HEIGHT / 2.0));
		}
		for (short n = 0; n < ASTEROID.length; n++) {
			if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID[n])) {
				if (ASTEROID_PHYSICS.collisionCheck(object, ASTEROID[n], 2) && !PLAYER.isDead) {
					object.hp -= ASTEROID[n].getSize();
					for (short d = 0; d < ASTEROID_FRAGMENT[n].length; d++) {
						ASTEROID_FRAGMENT[n][d] = newDestroyedRoid(ASTEROID[n].getPosition());
					}
					ASTEROID[n] = newAsteroid();

				}
			}
			for (short d = 0; d < ASTEROID_FRAGMENT[n].length; d++) {
				if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID_FRAGMENT[n][d])) {
					if (ASTEROID_PHYSICS.collisionCheck(object,
							ASTEROID_FRAGMENT[n][d], 2)&& !PLAYER.isDead) {
						object.hp -= ASTEROID_FRAGMENT[n][d].getSize();
						ASTEROID_FRAGMENT[n][d] = newAsteroid();
						ASTEROID_FRAGMENT[n][d].setVelocity(0, 0);
					}
				}
			}
		}

		for (short i = 0; i < object.shipWeapon.clipSize; i++) {
			for (short n = 0; n < ASTEROID.length; n++) {
				if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID[n])) {
					if (Physics.collisionCheck(
							object.shipWeapon.projectile[i], ASTEROID[n], 4)) {
					
						object.shipWeapon.resetProjectile(i,
								(short) object.getPosition().getX(),
								(short) object.getPosition().getY());
						ASTEROID[n].hp -= object.shipWeapon.damage;
						if (ASTEROID[n].hp <= 0) {
							for (short d = 0; d < ASTEROID_FRAGMENT[n].length; d++) {
								ASTEROID_FRAGMENT[n][d] = newDestroyedRoid(ASTEROID[n].getPosition());
							}
							object.score += ASTEROID[n].getSize();
						}
					}
				}
				for (short d = 0; d < ASTEROID_FRAGMENT[n].length; d++) {
					if (!ASTEROID_PHYSICS.offscreenCheck(ASTEROID_FRAGMENT[n][d])) {
						if (Physics.collisionCheck(
								object.shipWeapon.projectile[i],
								ASTEROID_FRAGMENT[n][d], 2)) {
							object.shipWeapon.resetProjectile(i,
									(short) object.getPosition().getX(),
									(short) object.getPosition().getY());
							ASTEROID_FRAGMENT[n][d].hp -= object.shipWeapon.damage;
							if (ASTEROID_FRAGMENT[n][d].hp <= 0) {
								object.score += ASTEROID[n].getSize();
								ASTEROID_FRAGMENT[n][d] = newAsteroid();
								ASTEROID_FRAGMENT[n][d].setVelocity(0, 0); 
							}
						}
					}
				}
			}
		}

		if (ASTEROID_PHYSICS.offscreenCheck(object)) {
			if (object.getPosition().getX() > SCREEN_WIDTH+20) {
				object.setXposition(-20);
			}
			if (object.getPosition().getY() > SCREEN_HEIGHT+20) {
				object.setYposition(-20);
			}
			if (object.getPosition().getX() < -20) {
				object.setXposition(SCREEN_WIDTH+20);
			}
			if (object.getPosition().getY() < -20) {
				object.setYposition(SCREEN_HEIGHT+20);
			}
		}

	}

	public static Asteroid newDestroyedRoid(Point pos) {
		Point[] P = new Point[5];
		short color = (short)Physics.generateRandomInt(100, 215);

		for (short n = 0; n < P.length; n++) {
			P[n] = new Point(Physics.generateRandomInt(-10, 10) / 10.0,
					Physics.generateRandomInt(-10, 10) / 10.0);
		}

		Asteroid a = new Asteroid(P,new Point((short) (Math.random() * 500),
				(short) (Math.random() * 500)), P.length, Physics.generateRandomInt(
				5, 15), 2);
		a.setPosition(pos);
		a.setVelocity(new eVector(Physics.generateRandomInt(-15, 15) / 10.0,
				Physics.generateRandomInt(-15, 15) / 10.0));

		a.setRotationRate(Physics.generateRandomInt(-20, 20) / 10.0);
		a.setColor(color, color, color);
		a.setHp(a.getSize());
		return a;
	}

	public static Asteroid newAsteroid() {
		Point[] P = new Point[5];
		short color = (short) Physics.generateRandomInt(100, 215);
		short side = (short) (Math.random() * 100);
		short min = 2;
		short max;
		short r;
		
		if (Math.random() < .2) {
			max = 20;
			r = 14;
		} else {
			max = 14;
			r = 10;
		}
		for (short n = 0; n < P.length; n++) {
			P[n] = new Point(Physics.generateRandomInt(-10, 10) / 10.0,
					Physics.generateRandomInt(-10, 10) / 10.0);
		}
		Asteroid a = new Asteroid(P, new Point((short) (Math.random() * 500),
				(short) (Math.random() * 500)), P.length, Physics.generateRandomInt(
				45, 80), 2);
		if (side < 25) {
			a.setPosition(Physics.generateRandomInt(-125, -100),
					(short) (Math.random() * 550));
			a.setVelocity(new eVector(
					Physics.generateRandomInt(min, max) / 10.0, Physics
							.generateRandomInt(-r, r) / 10.0));
		} else if (side >= 25 && side < 50) {
			a.setPosition(Physics.generateRandomInt(
					SCREEN_WIDTH + 100, SCREEN_WIDTH + 125),
					(short) (Math.random() * 550));
			a.setVelocity(new eVector(
					Physics.generateRandomInt(-max, -min) / 10.0,
					Physics.generateRandomInt(-r, r) / 10.0));
		} else if (side >= 50 && side < 75) {
			a.setPosition((short) (Math.random() * 650), Physics
					.generateRandomInt(SCREEN_HEIGHT + 100,
							SCREEN_HEIGHT + 125));
			a.setVelocity(new eVector(
					Physics.generateRandomInt(-r, r) / 10.0, Physics
							.generateRandomInt(-max, -min) / 10.0));
		} else if (side >= 75 && side < 100) {
			a.setPosition((short) (Math.random() * 550), Physics
					.generateRandomInt(-125, -100));
			a.setVelocity(new eVector(
					Physics.generateRandomInt(-r, r) / 10.0, Physics
							.generateRandomInt(min, max) / 10.0));
		} else {
			a.setPosition(Physics.generateRandomInt(-125, -100),
					(short) (Math.random() * 550));
			a.setVelocity(new eVector(
					Physics.generateRandomInt(min, max) / 10.0, Physics
							.generateRandomInt(-r, r) / 10.0));
		}
		a.setRotationRate(Physics.generateRandomInt(-12, 12) / 10.0);
		a.setColor(color, color, color);
		a.setHp(a.getSize());
		return a;
	}

	static void shipReset(Ship s, short x, short y) {
		s.setPosition(new Point(x, y));
		s.setVelocity(0, 0);
		s.score = 0;
		s.setHp(100);
	}
}
