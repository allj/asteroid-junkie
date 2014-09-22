// HighScoreScreen.java

/*
 * This the HighScoreScreen class.
 * It show the user a screen in which they
 * can enter their name so their high score can be 
 * stored. This class serves no other purpose.
 */

package menu;

import gameComponents.GameInput;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class HighScoreScreen {

	
	static int menuX = 100;
	static int menuY = 300;
	static MenuOption[] letter = new MenuOption[26];
	static MenuOption enterButton = new MenuOption ("OK",280,490,50,Color.white);
	static MenuOption spaceButton = new MenuOption ("SPACE",380,490,35,Color.white);
	static MenuOption backSpaceButton = new MenuOption ("DEL",190,490,35,Color.white);
	static String playerName;
	static int playerScore;
	static int bonusScore;
	static Font fontLarge = new Font(Font.DIALOG, Font.PLAIN, 32);
	static Font fontMedium = new Font(Font.DIALOG, Font.PLAIN, 16);
	
	public static void init(int score, int bonus) {
		for (int i =0; i < letter.length;i++) {
			if(i < 8)
				letter[i] = new MenuOption(""+(char)(65+i),menuX+i*60, menuY,28,Color.WHITE);
			if(i >= 8 && i<18)
				letter[i] = new MenuOption(""+(char)(65+i),menuX+i*60-540, menuY+60,28,Color.WHITE);
			if(i >= 18)
				letter[i] = new MenuOption(""+(char)(65+i),menuX+i*60-1080, menuY+120,28,Color.WHITE);
		}
		bonusScore = bonus;
		playerScore = score;
		playerName = "";
	}
	
	public static void draw(Graphics g) {
		g.setColor(new Color(180,0,180));
		g.fillRect(0, 240, 655, 5);
		g.setFont(fontMedium);
		g.drawString("FINAL SCORE : "+playerScore+" + ("+playerScore+" * "+round(((double)bonusScore/(double)playerScore*100),1)+"% Accuracy) = "+(playerScore+bonusScore), 0, 18);

		g.drawString("ENTER NAME: ", 0, 235);
		for (int i =0; i < letter.length;i++) {
			letter[i].draw(g, GameInput.mousePosition); // draw the letter
			if (letter[i].mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
				playerName += letter[i].text; // add specified letter
				GameInput.mouseButton1 = false; // simulate mouse being released, prevents repeating letters
			}
		}
		
		if (enterButton.mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
			if (playerName.length() == 0) { // if user does not enter a name
				playerName = "Anonymous";
			}
			// write info to file
			FileIO.writeStringToFile(FileIO.readFile("HighScores.junkie")+playerName+","+(playerScore+bonusScore)+";", "HighScores.junkie");
			
			MainMenu.mode = 0; // go to main menu
		}
		
		if (backSpaceButton.mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
			try { // try to delete a letter
				playerName = playerName.substring(0, playerName.length()-1);
				GameInput.mouseButton1 = false; // simulate mouse being released, prevents repeating letters
			} catch (Exception e) {
				// do nothing
			}
		}
		
		if (spaceButton.mouseOver(GameInput.mousePosition) && GameInput.mouseButton1) {
			playerName += ' '; // add a space
			GameInput.mouseButton1 = false; // simulate mouse being released, prevents repeating letters
		}
		g.setColor(Color.GREEN);
		g.setFont(fontMedium);
		g.drawString(playerName, 110, 235);
		enterButton.draw(g, GameInput.mousePosition);
		spaceButton.draw(g, GameInput.mousePosition);
		backSpaceButton.draw(g, GameInput.mousePosition);
	}
	
	static double round(double num, int numDecimalPlaces) { // round decimal
		num = Math.round(num * Math.pow(10, numDecimalPlaces))
				/ Math.pow(10, numDecimalPlaces);
		return num;
	}
}
