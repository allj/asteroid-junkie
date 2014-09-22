package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;

import physicsEngine.Physics;


public class Wave8 extends Wave {
	@Override
	void draw(Graphics g) {
		// draw enemies
		for (int i =0; i<this.numEnemies;i++){                                  
			this.enemies[i].alienWeapon.drawGradient(g);                    
			this.drawEnemy(g,new Color(255,255,0),(int)this.enemies[i].getPosition().getX(),(int)this.enemies[i].getPosition().getY(),this.enemySize);                       
			this.drawExplosion(g, i);
		}
		
	}

	@Override
	void init() {
		// set variables
		this.waveTime = 0;     
		this.numEnemies = 28;
		enemies = new JunkieAlien[this.numEnemies];
		for (int i = 0;i< this.numEnemies;i++) {
			enemies[i] = new JunkieAlien(0,0,1);
		}
		this.enemySize = 16;
		this.enemyShotSpeed = 3;
		this.enemyShotProb = .2f;
		
		// set velocity
		for (int i =0; i<this.numEnemies;i++){
			this.enemies[i].isDestroyed = false;
			this.enemies[i].setPosition(300,100);
			if (Math.random() <.5) {
				this.enemies[i].getVelocity().setX(1);
			} else {
				this.enemies[i].getVelocity().setX(-1);
			}
			
			if (Math.random() <.5) {
				this.enemies[i].getVelocity().setY(1);
			} else {
				this.enemies[i].getVelocity().setY(-1);
			}
		}
		
		//---------------------------------------------------
		// set up the smiley face pattern
		for (int i =0; i<this.numEnemies-10;i++){
			this.enemies[i].setPosition(100+75*Math.cos(Math.toRadians(i*20)),200+75*Math.sin(Math.toRadians(i*20)));
		}
		this.enemies[this.numEnemies-10].setPosition(70,170);
		this.enemies[this.numEnemies-9].setPosition(130,170);
		for (int i =this.numEnemies-8; i<this.numEnemies;i++){
			this.enemies[i].setPosition(100+45*Math.cos(Math.toRadians(i*25+225)),205+45*Math.sin(Math.toRadians(i*25+225)));
		}
		//--------------------------------------------------
	}

	@Override
	void move(Physics p) {
		this.waveTime += .001;
		if (this.waveTime > 1) {
			for (int i =0; i<this.numEnemies;i++){
				this.enemies[i].applyAlienPhysics(p);
				if (this.enemies[i].isDestroyed) {
					this.enemies[i].getPosition().setX(-1000);
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
				
				// neutralizes the effect of the physics without affecting the Velocity
				// Velocity is needed to remain constant, but we don't want things to move just yet
				this.enemies[i].getPosition().setX(this.enemies[i].getPosition().getX()- this.enemies[i].getVelocity().getX());
				this.enemies[i].getPosition().setY(this.enemies[i].getPosition().getY()- this.enemies[i].getVelocity().getY());
				
				// move enemies .25 pixels to the right
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
	void drawDebugInfo(Graphics g) { // draw debug info
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
