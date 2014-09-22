package physicsEngine;

import java.awt.Graphics;

//the RectangleObject class
public class RectangleObject extends PhysicsObject {
	eVector size = new eVector(0,0);	
	
	// constructor
	RectangleObject(Point position, eVector size, double mass){
		this.position = position;
		this.size = size;
		this.setMass(mass);
	}
	
	// constructor
	RectangleObject(int x,int y, double width, double height, double mass){
		this.position = new Point(x,y);
		this.size = new eVector(width,height);
		this.setMass(mass);
	}
	
	// sets the size
	void setSize(int newSize) {
		this.size.xComponent *= newSize;
		this.size.yComponent *= newSize;
	}
	
	// moves object
	void move(double vel, double angle) {
		this.velocity = new eVector(Math.cos(Math.toRadians(angle))*vel, Math.sin(Math.toRadians(angle))*vel);
	}
	
	// draws object
	void drawFilled(Graphics g, int arcWidth,int arcHeight) {
		g.setColor(this.color);
		g.fillRoundRect((int)(this.position.xComponent-(this.size.xComponent/2)), (int)(this.position.yComponent-(this.size.yComponent/2)),(int) this.size.xComponent,(int) this.size.yComponent, arcWidth, arcHeight);
	}
	
	void drawFilled(Graphics g) {
		g.setColor(this.color);
		g.fillRect((int)(this.position.xComponent-(this.size.xComponent/2)), (int)(this.position.yComponent-(this.size.yComponent/2)),(int) this.size.xComponent,(int) this.size.yComponent);
	}
}