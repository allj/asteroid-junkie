// Wave1.java


/*
 * This is the first wave.
 * It is a long line of enemies
 * that move back and forth  
 */

package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;

import physicsEngine.Physics;


public class Wave1 extends Wave {

	@Override
	void draw(Graphics g) {
		for (int i =0; i<this.numEnemies;i++){                                  
			this.enemies[i].alienWeapon.drawGradient(g);                    
			this.drawEnemy(g,new Color(0,150,0),(int)this.enemies[i].getPosition().getX(),(int)this.enemies[i].getPosition().getY(),this.enemySize);
			this.drawExplosion(g, i);
		}
	}

	@Override
	void init() {
		this.waveTime = 0;     
		this.numEnemies = 28;
		enemies = new JunkieAlien[this.numEnemies];
		this.enemySize = 14;
		this.enemyShotSpeed = 3;
		this.enemyShotProb = .3f;
		for (int i =0; i<this.numEnemies;i++){
			enemies[i] = new JunkieAlien(0,0,1);
			this.enemies[i].isDestroyed = false;
			this.enemies[i].setPosition(10+i*22,40+i*5);
			this.enemies[i].getVelocity().setX(1);
		}
	}

	@Override
	void move(Physics p) {
		this.waveTime += .001;
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].applyAlienPhysics(p);
			if (this.enemies[i].isDestroyed) {
				this.enemies[i].setXposition(-1000);
				continue;                       
			}
			if(this.enemies[i].getPosition().getX() >= SpaceJunkieGame.SCREEN_WIDTH 
					|| this.enemies[i].getPosition().getX() <= 0) {
				this.enemies[i].getVelocity().setX(-this.enemies[i].getVelocity().getX());
			}                       
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
