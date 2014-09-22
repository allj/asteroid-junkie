// Wave9.java


/*
 * This is the final wave.
 * It is only one enemy, but this time
 * the enemy is a giant box that fires in 
 * the direction of the player. When it touches
 * the edge of the screen its switches direction
 * randomly.  
 */
package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;

import physicsEngine.Physics;


public class Wave9 extends Wave {
	
	double enemySpeed;
	
	@Override
	void draw(Graphics g) {
		for (int i =0; i<this.numEnemies;i++){                                  
			this.enemies[i].alienWeapon.drawGradient(g);                    
			this.drawEnemySquare(g,new Color(0,150,50),(int)this.enemies[i].getPosition().getX(),(int)this.enemies[i].getPosition().getY(),this.enemySize);                       
			this.drawExplosion(g, i);
		}
	}

	@Override
	void init() {
		this.waveTime = 0;     
		this.numEnemies = 1;
		enemies = new JunkieAlien[this.numEnemies];
		this.enemySize = 30;
		this.enemyShotSpeed = 3;
		this.enemySpeed = 1;
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i] = new JunkieAlien(0,0,1);
			this.enemies[i].setWeapon(65, 40, 1);
			this.enemies[i].isDestroyed = false;
			this.enemies[i].getPosition().setX(300);
			this.enemies[i].getPosition().setY(100);
			if (Math.random() <.5) {
				this.enemies[i].getVelocity().setX(this.enemySpeed);
			} else {
				this.enemies[i].getVelocity().setX(-this.enemySpeed);
			}
			
			if (Math.random() <.5) {
				this.enemies[i].getVelocity().setY(this.enemySpeed);
			} else {
				this.enemies[i].getVelocity().setY(-this.enemySpeed);
			}
			this.enemies[i].hp = 100;
		}
		this.enemyShotProb = .9f;
	}

	@Override
	void move(Physics p) {
		this.waveTime += .001;
		
		
		if (this.waveTime > 1) {
			for (int i =0; i<this.numEnemies;i++){
				this.enemies[i].applyAlienPhysics(p);
				if (this.enemies[i].isDestroyed) {
					this.enemies[i].getVelocity().setX(-1000);
					continue;                       
				}
				if(this.enemies[i].getPosition().getX() >= SpaceJunkieGame.SCREEN_WIDTH 
						|| this.enemies[i].getPosition().getX() <= 0) {
					if(this.enemies[i].getPosition().getX() >= SpaceJunkieGame.SCREEN_WIDTH) {
						this.enemies[i].getPosition().setX(SpaceJunkieGame.SCREEN_WIDTH -1);
					} else {
						this.enemies[i].getPosition().setX(1);
					}
					this.enemies[i].getVelocity().setX(-this.enemies[i].getVelocity().getX());
					if (Math.random() <.5) {
						this.enemies[i].getVelocity().setY(1);
					} else {
						this.enemies[i].getVelocity().setY(-1);
					}
				}
				
				if(this.enemies[i].getPosition().getY() >= SpaceJunkieGame.SCREEN_HEIGHT -200 
						|| this.enemies[i].getPosition().getY() <= 30) {
					if(this.enemies[i].getPosition().getY() >= SpaceJunkieGame.SCREEN_HEIGHT -200) {
						this.enemies[i].getPosition().setY(SpaceJunkieGame.SCREEN_HEIGHT -201);
					} else {
						this.enemies[i].getPosition().setY(31);
					}
					
					if (Math.random() <.5) {
						this.enemies[i].getVelocity().setX(1);
					} else {
						this.enemies[i].getVelocity().setX(-1);
					}
					this.enemies[i].getVelocity().setY(-this.enemies[i].getVelocity().getY());
				}
			}
		} else {
			for (int i =0; i<this.numEnemies;i++){
				this.enemies[i].applyAlienPhysics(p);
				
				// neutralizes the effect of the physics without affecting the velocity
				// velocity is needed to remain constant, but we don't want things to move just yet
				this.enemies[i].getPosition().setX(this.enemies[i].getPosition().getX() - this.enemies[i].getVelocity().getX());
				this.enemies[i].getPosition().setY(this.enemies[i].getPosition().getY() - this.enemies[i].getVelocity().getY());
				
				// move enemies 1 pixel to the right
				this.enemies[i].getPosition().setX(this.enemies[i].getPosition().getX() + .25);
				
				// move destroyed enemies off screen
				if (this.enemies[i].isDestroyed) {
					this.enemies[i].getPosition().setX(-1000);
					continue;                       
				}
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
	
	@Override
	void fire() { // fire projectile directly at player
		this.enemies[(int)(Math.random()*this.numEnemies)].alienWeapon.fire(this.enemyShotSpeed, Physics.calculateAngle(SpaceJunkieGame.player.getPosition(),this.enemies[0].getPosition()));
	}
}
