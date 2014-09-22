package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;

import physicsEngine.Physics;


public abstract class Wave {
	int numEnemies;
	JunkieAlien[] enemies;  
	float enemyShotSpeed;
	float enemyShotProb;
	float enemySize;
	float waveTime = 0;
	
	abstract void init();
	abstract void move(Physics p);
	abstract void draw(Graphics g);
	abstract void drawDebugInfo(Graphics g);
	
	void fire() {
		if (Math.random() < this.enemyShotProb)
			this.enemies[(int)(Math.random()*this.numEnemies)].alienWeapon.fire(this.enemyShotSpeed, 90);
	}
	
	void drawEnemy(Graphics g, Color c,int x, int y, double size) {
		g.setColor(new Color((int)(c.getRed()*.4),(int)(c.getGreen()*.4),(int)(c.getBlue()*.4)));
		g.fillOval(x-((int)(size/2.0)), y-((int)(size/2.0)), (int)size, (int)size);
		g.setColor(new Color((int)(c.getRed()*.6),(int)(c.getGreen()*.6),(int)(c.getBlue()*.6)));
		g.fillOval(x-((int)((size*.8)/2.0)), y-((int)((size*.8)/2.0)), (int)(size*.8), (int)(size*.8));
		g.setColor(new Color((int)(c.getRed()*.8),(int)(c.getGreen()*.8),(int)(c.getBlue()*.8)));
		g.fillOval(x-((int)((size*.6)/2.0)), y-((int)((size*.6)/2.0)), (int)(size*.6), (int)(size*.6));
		g.setColor(c);
		g.fillOval(x-((int)((size*.4)/2.0)), y-((int)((size*.4)/2.0)), (int)(size*.4), (int)(size*.4));
		
		g.setColor(Color.BLACK);
		g.fillOval(x-6, y-6, (int)(size*.2), (int)(size*.2));
		g.fillOval(x+2, y-6, (int)(size*.2), (int)(size*.2));
	}
	
	void drawEnemySquare(Graphics g, Color c,int x, int y, double size) {
		g.setColor(new Color((int)(c.getRed()*.4),(int)(c.getGreen()*.4),(int)(c.getBlue()*.4)));
		g.fillRect(x-((int)(size/2.0)), y-((int)(size/2.0)), (int)size, (int)size);
		g.setColor(new Color((int)(c.getRed()*.6),(int)(c.getGreen()*.6),(int)(c.getBlue()*.6)));
		g.fillRect(x-((int)((size*.8)/2.0)), y-((int)((size*.8)/2.0)), (int)(size*.8), (int)(size*.8));
		g.setColor(new Color((int)(c.getRed()*.8),(int)(c.getGreen()*.8),(int)(c.getBlue()*.8)));
		g.fillRect(x-((int)((size*.6)/2.0)), y-((int)((size*.6)/2.0)), (int)(size*.6), (int)(size*.6));
		g.setColor(c);
		g.fillRect(x-((int)((size*.4)/2.0)), y-((int)((size*.4)/2.0)), (int)(size*.4), (int)(size*.4));
		
		g.setColor(Color.BLACK);
		g.fillOval(x-6, y-6, (int)(size*.2), (int)(size*.2));
		g.fillOval(x+2, y-6, (int)(size*.2), (int)(size*.2));
	}
	
	void drawExplosion(Graphics g,int i) {
		try {
			if(this.enemies[i].explosion.isExploded) {
				this.enemies[i].explosion.draw(g);
			}
		} catch (Exception e) {
			// do nothing
		}
	}
}
