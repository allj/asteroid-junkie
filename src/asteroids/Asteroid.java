package asteroids;

import physicsEngine.Point;
import physicsEngine.PolygonObject;

//the asteroid class
public class Asteroid extends PolygonObject {
	float angle = 0;
	float rotationRate = 0;
	float hp = 1; // hit points
	
	// constructor
	public Asteroid(Point[] pos, Point c, int np, double s, double m){
		super(pos,c,np,s,m);		
	}
	
	// sets the rotation rate
	public void setRotationRate(double newRotationRate){
		this.rotationRate = (float)newRotationRate;
	}
	
	// sets the hp of object
	public void setHp(double newHp){
		this.hp = (float)newHp;
	}
	
	// rotates object
	public void applyRotationRate(){
		this.angle += this.rotationRate;
		this.angleReset();
		this.rotate(this.angle);
	}
	
	// resets angle
	public void angleReset() {
		if (this.angle >= 360)
			this.angle = this.angle-360;
        if (this.angle < 0)
        	this.angle = 360+this.angle;
	}
}
