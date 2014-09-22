// Wave3.java


/*
 * This is the third wave.
 * It is a group of enemies that moves
 * in a rectangular motion along the edges
 * of the screen.  
 */

package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;


import physicsEngine.Physics;
import physicsEngine.PhysicsObject;


public class Wave3 extends Wave {
	
	PhysicsObject enemyFormation = new PhysicsObject(300,200,10);
	double enemySpeed;
	
	@Override
	void draw(Graphics g) {
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].alienWeapon.drawGradient(g);
			this.drawExplosion(g, i);
			if (this.enemies[i].isDestroyed) {
				this.enemies[i].getPosition().setX(-1000);
				continue;			
			}
			
			// set up the shape
			if(i>=22) {
				drawEnemy(g,new Color(0,255,0),(int)this.enemyFormation.getPosition().getX()+i*20-421,(int)this.enemyFormation.getPosition().getY()+60,this.enemySize);
				this.enemies[i].setPosition(this.enemyFormation.getPosition().getX()+i*20-421,this.enemyFormation.getPosition().getY()+60);
			} else if (i>=14) {
				drawEnemy(g,new Color(0,255,0),(int)this.enemyFormation.getPosition().getX()+i*20-280,(int)this.enemyFormation.getPosition().getY()+40,this.enemySize);
				this.enemies[i].setPosition(this.enemyFormation.getPosition().getX()+i*20-280,this.enemyFormation.getPosition().getY()+40);
			} else if (i>=6) {
				drawEnemy(g,new Color(0,255,0),(int)this.enemyFormation.getPosition().getX()+i*20-120,(int)this.enemyFormation.getPosition().getY()+20,this.enemySize);
				this.enemies[i].setPosition(this.enemyFormation.getPosition().getX()+i*20-120,this.enemyFormation.getPosition().getY()+20);
			} else {
				drawEnemy(g,new Color(0,255,0),(int)this.enemyFormation.getPosition().getX()+i*20+20,(int)this.enemyFormation.getPosition().getY(),this.enemySize);
				this.enemies[i].setPosition(this.enemyFormation.getPosition().getX()+i*20+20,this.enemyFormation.getPosition().getY());
			}
		}
	}

	@Override
	void init() {
		this.numEnemies = 28;
		enemies = new JunkieAlien[this.numEnemies];
		for (int i = 0;i< this.numEnemies;i++) {
			enemies[i] = new JunkieAlien(0,0,1);
			this.enemies[i].isDestroyed = false;
		}
		this.enemyShotProb=.5f;
		this.enemySize = 18;
		this.enemyShotSpeed = 3;
		this.enemyFormation.setPosition(20,50);
		this.enemySpeed = .5;
		this.enemyFormation.setVelocity(this.enemySpeed,0);
	}

	@Override
	void move(Physics p) {
		this.waveTime += .001;
		this.enemyFormation.applyPhysics(p, 'f');
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].applyAlienPhysics(p);
		}
		
		// move in rectangular motion
		if(this.enemyFormation.getPosition().getX() >= SpaceJunkieGame.SCREEN_WIDTH -150) {
			this.enemyFormation.setVelocity(0,this.enemySpeed);
		} 
			
		if(this.enemyFormation.getPosition().getY() >= SpaceJunkieGame.SCREEN_HEIGHT -150) {
			this.enemyFormation.setVelocity(-this.enemySpeed,0);
		}
		
		if(this.enemyFormation.getPosition().getX() < 10) {
			this.enemyFormation.setVelocity(0,-this.enemySpeed);
		}
		
		if(this.enemyFormation.getPosition().getY() < 30) {
			this.enemyFormation.getPosition().setY(30);
			this.enemyFormation.setVelocity(this.enemySpeed,0);
		}
		
		if(this.waveTime>4) {
			this.enemySpeed = 2.0;
		} else if (this.waveTime>2) {
			this.enemySpeed = 1.5;
		}
	}
	
	@Override
	void drawDebugInfo(Graphics g) {
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
