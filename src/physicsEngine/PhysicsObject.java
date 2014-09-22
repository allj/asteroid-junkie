package physicsEngine;

import java.awt.Color;

//the PhysicsObject class
public class PhysicsObject {
	protected Point position = new Point(0,0);
	protected eVector velocity = new eVector(0,0);
	protected float mass = 0;
	protected Color color = Color.BLACK;
	
	public PhysicsObject() {
		
	}
	
	public PhysicsObject(int x,int y, double m) {
		this.position = new Point(x,y);
		this.setMass(m);
	}
	
	public void setPosition(double x, double y) {
		this.position.setComponents(x, y);
	}
	
	public void setXposition(double x) {
		this.position.setX(x);
	}
	
	public void setYposition(double y) {
		this.position.setY(y);
	}
	
	public void setZposition(double z) {
		this.position.setZ(z);
	}
	
	public void setPosition(Point p) {
		this.position.setComponents(p);
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public void setVelocity(double x, double y) {
		this.velocity.setComponents(x, y);
	}
	
	public void setVelocity(eVector newVelocity) {
		this.velocity.setComponents(newVelocity);
	}
	
	public eVector getVelocity() {
		return this.velocity;
	}
	

	public void setMass(double newMass) {
		this.mass = (float)newMass;
	}

	public float getMass() {
		return this.mass;
	}

	public void setColor(Color c) {
		this.color = c;
	}
	
	public void setColor(int r, int g, int b) {
		this.color = new Color(r,g,b);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void accelerate(double x,double y) {
		this.velocity = Physics.accelerateObject(this, new eVector (x,y));
	}
	
	public void applyPhysics (Physics p, char type) {
		this.position = Physics.applyMotion(this);
		if (type == 'f') {
			this.velocity = p.applyFriction(this);
		} else if (type == 'g') {
			this.velocity = p.applyGravity(this);
		}
	}
}
