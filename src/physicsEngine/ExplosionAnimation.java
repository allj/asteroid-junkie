package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;

//the explosion animation class
public class ExplosionAnimation {
	Point position;
	short time;
	short radius; // size of explosion
	public boolean isExploded;
	
	// contructor
	public ExplosionAnimation(int x, int y, int rad) {
		this.position = new Point (x,y);
		this.radius = (short)rad;
		this.time = 0;
		this.isExploded = true;
	}
	
	// draws explosion
	public void draw(Graphics g) {
		this.time += 1;
		if (this.time <50) {
			if(Math.random() <.8) {
				this.drawCircleGradient(g, new Color(255,120,0), (int)this.position.xComponent, (int)this.position.yComponent, (int)(Math.random()*this.radius));
			} 
		} else {
			this.isExploded = false;
		}
	}
	
	// draws with gradient
	public void drawCircleGradient(Graphics g, Color c,int x, int y, double size) {
		// the color specified is the innermost circle
		g.setColor(new Color((int)(c.getRed()*.4),(int)(c.getGreen()*.4),(int)(c.getBlue()*.4)));
		g.fillOval(x-((int)(size/2.0)), y-((int)(size/2.0)), (int)size, (int)size);
		g.setColor(new Color((int)(c.getRed()*.6),(int)(c.getGreen()*.6),(int)(c.getBlue()*.6)));
		g.fillOval(x-((int)((size*.8)/2.0)), y-((int)((size*.8)/2.0)), (int)(size*.8), (int)(size*.8));
		g.setColor(new Color((int)(c.getRed()*.8),(int)(c.getGreen()*.8),(int)(c.getBlue()*.8)));
		g.fillOval(x-((int)((size*.6)/2.0)), y-((int)((size*.6)/2.0)), (int)(size*.6), (int)(size*.6));
		g.setColor(c);
		g.fillOval(x-((int)((size*.4)/2.0)), y-((int)((size*.4)/2.0)), (int)(size*.4), (int)(size*.4));
	}
}

