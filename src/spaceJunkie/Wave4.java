// Wave4.java


/*
 * This is the fourth wave.
 * It is a big ellipse.
 * Nothing special.
 * Changes direction at some points.  
 */

package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;

import physicsEngine.Physics;
import physicsEngine.PhysicsObject;


public class Wave4 extends Wave {
	
	// vars-----------------------------------------------------
	PhysicsObject enemyFormation = new PhysicsObject(300,200,10);
	double angle =0;
	double enemyWidth;
	double enemyHeight;
	double enemyRotationRate;
	double enemySpeed;
	//-----------------------------------------------------------
	
	@Override
	void draw(Graphics g) {
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].alienWeapon.drawGradient(g); // draw weapon and projectiles
			
			// create the circle formation and rotate
			this.enemies[i].setPosition((int)(this.enemyFormation.getPosition().getX()+(this.enemyWidth*Math.cos(Math.toRadians(13*i+this.angle)))),(int)(this.enemyFormation.getPosition().getY()+(this.enemyHeight*Math.sin(Math.toRadians(13*i+this.angle)))));		
			
			this.drawExplosion(g, i); // draw the explosion
			if (this.enemies[i].isDestroyed) { // move enemies off screen if destroyed 
				this.enemies[i].getPosition().setX(-1000);
				continue;			
			}
			
			// draw enemy
			this.drawEnemy(g, new Color(255,0,0), (int)this.enemies[i].getPosition().getX(), (int)this.enemies[i].getPosition().getY(),this.enemySize);		
		}
	}

	@Override
	void init() { // initialize the wave
		
		// set variables
		this.numEnemies = 28;
		this.enemies = new JunkieAlien[this.numEnemies];
		for (int i = 0;i< this.numEnemies;i++) {
			this.enemies[i] = new JunkieAlien(0,0,1);
			this.enemies[i].isDestroyed = false;
		}
		this.enemySize = 18;
		this.enemyShotSpeed = 3;
		this.enemyWidth = 310;
		this.enemyHeight = 75;
		this.enemyFormation.setPosition(SpaceJunkieGame.SCREEN_WIDTH/2.0,110);
		this.enemySpeed = 0;
		this.enemyRotationRate = .2;
		this.enemyShotProb = .3f;
		this.enemyFormation.setVelocity(this.enemySpeed,this.enemySpeed);
	}

	@Override
	void move(Physics p) { // move the wave
		this.waveTime += .001; // time progression for this wave
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].applyAlienPhysics(p); // apply the physics to individual enemies
		}
		this.angle += this.enemyRotationRate; // rotation
		this.enemyFormation.applyPhysics(p, 'f'); // apply physics to formation
		
		// adjust rotation and shot speed depending on time
		if (this.waveTime > 3) {
			this.enemyRotationRate = .6;
			this.enemyShotSpeed = 3.5f;
		} else if (this.waveTime > 2) {
			this.enemyRotationRate = -.6;
			this.enemyShotSpeed = 3.5f;
		} else if (this.waveTime > 1) {
			this.enemyRotationRate = .6;
			this.enemyShotSpeed = 3;
		}
	}
	
	@Override
	void drawDebugInfo(Graphics g) { // debugging info
	    for (int i = 0;i<this.numEnemies; i++) {
		if(this.enemies[i].isDestroyed)
		    g.setColor(Color.darkGray);
		else
		    g.setColor(Color.gray);
		g.drawString("Enemy " +(i+1)+ ": "+this.enemies[i].getPosition().toString()+"-------"+this.enemies[i].getVelocity().toString() ,1,30+i*10);
	    }
	    g.setColor(Color.gray);
	    g.drawString("Wave Time: "+this.waveTime,1,330);
	    g.drawString("Enemy Size: "+this.enemySize,1,340);
	    g.drawString("Enemy Shot Speed: "+this.enemyShotSpeed,1,350);
	    g.drawString("Enemy Shot Probability: "+this.enemyShotProb,1,360);
	    g.drawString("Number of Enemies: "+this.numEnemies,1,370);
	}
}
