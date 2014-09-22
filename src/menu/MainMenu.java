// MainMenu.java

/*
 * This is the MainMenu class.
 * It simply draws a nice menu.
 * It give users option to start the game 
 * or view high scores or instructions. 
 */
package menu;
import gameComponents.GameInput;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


import asteroids.AsteroidsGame;
import physicsEngine.PhysicsObject;

public class MainMenu {
	//------------------------------------------
	static int SCREEN_WIDTH;
	static int SCREEN_HEIGHT;
	//-----------------------------------------
	
	public static int mode = 0;
	static double StarMove = 0;	
	//-----------------------------------------
	// menu variables
	static int menuX;
	static int menuY;
	static MenuOption[] menuOption = new MenuOption[5];
	
	static Font fontSmall = new Font(Font.DIALOG, Font.PLAIN, 10);
	static Font fontNormal = new Font(Font.DIALOG, Font.PLAIN, 12);
	static Font fontLarge = new Font(Font.DIALOG, Font.PLAIN, 32);
	//------------------------------------------
	static String[] scoresInfo;
	static String[] highScores =  new String[20];
	
	public static void init(int width, int height) { // initializer method
		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;
		
		menuX = (int)(SCREEN_WIDTH/2.0-25);
		menuY = (int)(SCREEN_HEIGHT/2.0-50);
		
				
		// set up menu options 
		menuOption[0] = new MenuOption("PLAY SPACE JUNKIE",menuX-25, menuY,12,Color.WHITE);
		menuOption[1] = new MenuOption("PLAY ASTEROIDS",menuX-17, menuY+15,12,Color.WHITE);
		menuOption[2] = new MenuOption("HALL OF FAME",menuX-10, menuY+30,12,Color.WHITE);
		menuOption[3] = new MenuOption("INSTRUCTIONS",menuX-12, menuY+45,12,Color.WHITE);
		menuOption[4] = new MenuOption("EXIT",menuX+18, menuY+60,12,Color.WHITE);
		
		readHighScores();
	}
	
	public static void draw(Graphics g) {
		//-----------------------------------------------------------
		// physics
		for (int i = 0; i < AsteroidsGame.ASTEROID.length; i++) {
			if (mode == 0 || mode == 5 || mode == 7) { 
				AsteroidsGame.ASTEROID[i].applyRotationRate();
				AsteroidsGame.ASTEROID[i].applyPhysics(AsteroidsGame.ASTEROID_PHYSICS, 'f');
			
				if (AsteroidsGame.ASTEROID[i].getPosition().getX() > SCREEN_WIDTH + 150
						|| AsteroidsGame.ASTEROID[i].getPosition().getY() > SCREEN_HEIGHT + 150
						|| AsteroidsGame.ASTEROID[i].getPosition().getX() < -150
						|| AsteroidsGame.ASTEROID[i].getPosition().getY() < -150) {
					AsteroidsGame.ASTEROID[i] = AsteroidsGame.newAsteroid();
				}
			}
			for (int d = 0; d < AsteroidsGame.ASTEROID_FRAGMENT[i].length; d++) {
				if (!AsteroidsGame.ASTEROID_PHYSICS.offscreenCheck(AsteroidsGame.ASTEROID_FRAGMENT[i][d])) {
					AsteroidsGame.ASTEROID_FRAGMENT[i][d].applyRotationRate();
					AsteroidsGame.ASTEROID_FRAGMENT[i][d].applyPhysics(AsteroidsGame.ASTEROID_PHYSICS, 'f');
				}
			}
		}
		//--------------------------------------------------------------
		
		// Space junkie start------------------------------------------------------
		if (mode == 1) {
			moveStars(0,StarMove);
			for (int i=0; i < AsteroidsGame.ASTEROID.length;i++) {
				AsteroidsGame.ASTEROID[i].setYposition(AsteroidsGame.ASTEROID[i].getPosition().getY()+StarMove);
				for (int d = 0; d < AsteroidsGame.ASTEROID_FRAGMENT[i].length; d++) {
					AsteroidsGame.ASTEROID_FRAGMENT[i][d].setYposition(AsteroidsGame.ASTEROID_FRAGMENT[i][d].getPosition().getY()+StarMove);
				}
			}
			if (StarMove < 30) {
				StarMove += .1;
			} else {
				mode = 2;
			}
		} 
		
		if (mode == 2) {
			moveStars(0,StarMove);
			if (StarMove > 0) {
				StarMove -= .05;
			} else {
				StarMove = 0;
				mode = 3;
			}
		}
		//-----------------------------------------------------------------------
		
		// Draw things-----------------------------------------------------------
		drawBackground(g);	
		
		for (int i = 0; i < AsteroidsGame.ASTEROID.length; i++) {
			if (mode == 0 || mode == 5 || mode == 7) {
				if (!AsteroidsGame.ASTEROID_PHYSICS.offscreenCheck(AsteroidsGame.ASTEROID[i])) {
					AsteroidsGame.ASTEROID[i].drawWireframe(g);
				}
			}
			for (int d = 0; d < AsteroidsGame.ASTEROID_FRAGMENT[i].length; d++) {
				if (!AsteroidsGame.ASTEROID_PHYSICS.offscreenCheck(AsteroidsGame.ASTEROID_FRAGMENT[i][d])) {
					AsteroidsGame.ASTEROID_FRAGMENT[i][d].drawWireframe(g);
				}
			}
		}
		//-----------------------------------------------------------------------
		
		// Hall of Fame--------------------------------------------
		if (mode == 5) {
			MenuOption backButton = new MenuOption("BACK",10,490,12,Color.WHITE);
			g.setColor(Color.WHITE);		
			g.setFont(fontNormal);
			g.drawString("SPACE JUNKIE", 5, 20);
			for (int i = 0;i<highScores.length;i++) {
				try {
					g.drawString(highScores[i], 5, 60+i*14);
				} catch (Exception e) {
					// do nothing
				}		
			}
			backButton.draw(g, GameInput.mousePosition);
			if (backButton.mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				mode = 0;
			}
		}
		//---------------------------------------------------------
		
		// Instructions------------------------------------------------
		if (mode == 7) {
			g.setFont(fontNormal);
			g.setColor(Color.white);
			g.drawString("SPACE JUNKIE", 0, 12);
			g.drawString("Use Mouse for movement.", 0, 36);
			g.drawString("Left click to shoot.", 0, 48);
			g.drawString("Press escape to return to menu.", 0, 60);
			g.drawString("Object of the game is to shoot everything you see.", 0, 84);
			g.drawString("You are the orange space ship at the bottom of the screen.", 0, 96);
			MenuOption backButton = new MenuOption("BACK",10,490,12,Color.WHITE);
			backButton.draw(g, GameInput.mousePosition);
			if (backButton.mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				mode = 0;
			}
		}
		//---------------------------------------------------------------
		
		// Main menu-----------------------------------------------------
		if (mode == 0) {
			for (int i = 0; i < menuOption.length; i++) {
				menuOption[i].draw(g, GameInput.mousePosition);
			}
			if (menuOption[0].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				startSpaceJunkieGame();
			}
			if (menuOption[1].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				mode = 4;
			}
			if (menuOption[2].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				mode = 5;
				readHighScores();
			}
			if (menuOption[3].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				mode = 7;
			}
			if (menuOption[menuOption.length-1].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				System.exit(0);
			}
			
			g.setColor(Color.WHITE);
			g.setFont(fontLarge);
			g.drawString("A S T E R O I D     J U N K I E", (int)(SCREEN_HEIGHT/2.0)-145, 100);
		} else if (mode < 5) {
			g.setColor(Color.YELLOW);
			g.setFont(fontSmall);
			g.drawString("PREPARE FOR WAVE 1", 275, 250);
		}
		//---------------------------------------------------------------------
	}
	
	
	
	static void readHighScores() { // read and sort highscores
		scoresInfo = FileIO.stringToArray(FileIO.readFile("HighScores.junkie"),",;");
		String[] names = new String[(int)(scoresInfo.length/2.0)];
		int[] scores = new int[(int)(scoresInfo.length/2.0)];
		for (int i = 0, j = 0;i<scoresInfo.length;i+=2,j++) {
			try {
				names[j] = scoresInfo[i];
				scores[j] = Integer.parseInt(scoresInfo[i+1]);
			} catch (Exception e) {
				// do nothing
			}
		}
		//-----------------
		// sorts the scores
		// bubble sorting
		for (int i=0;i<scores.length;i++) {
			for (int j=0;j<scores.length;j++) {
				if (scores[i] >scores[j]) {
					// swaps values
					scores[i] += scores[j];
					scores[j] = scores[i]-scores[j];
					scores[i] -= scores[j];
					
					String temp = names[i];
					names[i] = names[j];
					names[j] = temp;
				}
			}
		}
		//-----------------
		for (int i = 0,j=0;i<highScores.length;i++,j+=2) {
			
			try {
				highScores[i] = (i+1)+". "+names[i]+" - "+scores[i];
			} catch (Exception e) {
				// do nothing
			}
		}
	}
	
	
	static void moveStars(double x, double y) {
		//--------------------------------------------
		// BACKGROUND
		// this moves the stars
		for (int i = 0;i< AsteroidsGame.STAR.length;i++) {
			AsteroidsGame.STAR[i].setPosition(AsteroidsGame.STAR[i].getPosition().getX()+x*AsteroidsGame.STAR[i].getMass(), AsteroidsGame.STAR[i].getPosition().getY()+y*AsteroidsGame.STAR[i].getMass());
			if (AsteroidsGame.STAR[i].getPosition().getY() > SCREEN_HEIGHT + 5) { // if AsteroidsGame.STAR moves off screen
				int c = (int)(Math.random()*100); // random colour
				AsteroidsGame.STAR[i] = new PhysicsObject((int)(Math.random()*SCREEN_WIDTH),(int)(-Math.random()*10),(Math.random()*3)+.1);
				AsteroidsGame.STAR[i].setColor(new Color(c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100)));
			}
			if (AsteroidsGame.STAR[i].getPosition().getY() < -5) { // if AsteroidsGame.STAR moves off screen
				int c = (int)(Math.random()*100); // random colour
				AsteroidsGame.STAR[i] = new PhysicsObject((int)(Math.random()*SCREEN_WIDTH),(int)(SCREEN_HEIGHT+Math.random()*10),(Math.random()*3)+.1);
				AsteroidsGame.STAR[i].setColor(new Color(c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100)));
			}
			if (AsteroidsGame.STAR[i].getPosition().getX() < -5) { // if AsteroidsGame.STAR moves off screen
				int c = (int)(Math.random()*100); // random colour
				AsteroidsGame.STAR[i] = new PhysicsObject((int)(SCREEN_WIDTH+Math.random()*10),(int)(Math.random()*SCREEN_HEIGHT),(Math.random()*3)+.1);
				AsteroidsGame.STAR[i].setColor(new Color(c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100)));
			}
			if (AsteroidsGame.STAR[i].getPosition().getX() > SCREEN_WIDTH + 5) { // if AsteroidsGame.STAR moves off screen
				int c = (int)(Math.random()*100); // random colour
				AsteroidsGame.STAR[i] = new PhysicsObject((int)(-Math.random()*SCREEN_WIDTH),(int)(Math.random()*SCREEN_HEIGHT),(Math.random()*3)+.1);
				AsteroidsGame.STAR[i].setColor(new Color(c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100)));
			}
		}
		//--------------------------------------------
	}
	

	
	
	
	static void drawBackground(Graphics g) {
		// draws nice twinkling stars
		double random = Math.random();
		if (random < .025) {
			AsteroidsGame.STAR[(int)(Math.random()*AsteroidsGame.STAR.length)].setColor(255,255,255);
		} else if (random <.1) {
			int c = (int)(Math.random()*100); // random colour
			AsteroidsGame.STAR[(int)(Math.random()*AsteroidsGame.STAR.length)].setColor(new Color(c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100),c+100-(int)(Math.random()*100)));
		}
		for (int i = 0;i< AsteroidsGame.STAR.length;i++) {
			g.setColor(AsteroidsGame.STAR[i].getColor());
			g.fillOval((int)AsteroidsGame.STAR[i].getPosition().getX(),(int)AsteroidsGame.STAR[i].getPosition().getY(),2,2);
		}
	}
	
	static void startSpaceJunkieGame() { // starts space junkie
		mode = 1;
		for (int i =0; i < AsteroidsGame.ASTEROID.length;i++) {	
			if (!AsteroidsGame.ASTEROID_PHYSICS.offscreenCheck(AsteroidsGame.ASTEROID[i])) {	
				for (int d = 0; d < AsteroidsGame.ASTEROID_FRAGMENT[i].length; d++) {
					AsteroidsGame.ASTEROID_FRAGMENT[i][d] = AsteroidsGame.newDestroyedRoid(AsteroidsGame.ASTEROID[i].getPosition());
				}
				AsteroidsGame.ASTEROID[i] = AsteroidsGame.newAsteroid();			
			}
			AsteroidsGame.ASTEROID[i].setHp(0);
			AsteroidsGame.ASTEROID[i].setXposition(-1000);
		}
	}
}

