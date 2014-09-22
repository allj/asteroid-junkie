package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;

//the ship class
public class Ship extends PolygonObject {
	public boolean isDead = false;
	private float angle = 0;
	private float orientation=0;
	public float hp=1; // hit points
	public float score=0;
	public Weapon shipWeapon = new Weapon(10,20,1, new eVector(2,2));
	
	// constructor
	public Ship(Point[] shapePoints, Point centralPoint, int numberOfPoints, double shipSize, double shipMasss) {
		super(shapePoints,centralPoint,numberOfPoints,shipSize,shipMasss);
	}
	
	// sets the weapon
	public void setWeapon(double rof, int cs, double d, eVector ps){
		this.shipWeapon = new Weapon(rof,cs,d,ps);
	}
	
	// sets hp
	public void setHp(double newHp){
		this.hp = (float)newHp;
	}
	
	// moves ship forward
	public void applyThrust(double acc, double angle) {
		this.accelerate(acc*Math.cos(Math.toRadians(angle)), acc*Math.sin(Math.toRadians(angle)));
	}
	
	// applies breaks
	public void applyBrakes(double b) {
		this.velocity.xComponent *= b;
		this.velocity.yComponent *= b;
	}
	
	// gets direction object is facing
	public float getDirection() {
		float d = this.orientation +this.angle;
		if (d >= 360)
			d = d-360;
        if (d < 0)
        	d = 360+d;
		return d;
	}
	
	// sets the direction the object should be pointing
	public void setOrientation(double theta) {
		this.orientation = (float)theta;
	}
	
	// sets the angle
	public void setAngle(double theta) {
		this.angle = (float)theta;
	}
	
	// gets the angle
	public double getAngle() {
		return this.angle;
	}
	
	// keeps angle within 360 degrees
	void angleReset() {
		if (this.angle >= 360)
			this.angle = this.angle-360;
        if (this.angle < 0)
        	this.angle = 360+this.angle;
	}
	
	// draws the thrust
	public void drawThrust(Graphics g) {
		byte dist = (byte)(2*Math.random()+18);
		if (Math.random() <.5) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawLine((int)this.getRealPointPosition(1).xComponent, (int)this.getRealPointPosition(1).yComponent, (int)(this.position.xComponent+dist*Math.cos(Math.toRadians(180+this.getDirection()))), (int)(this.position.yComponent+dist*Math.sin(Math.toRadians(180+this.getDirection()))));
		g.drawLine((int)this.getRealPointPosition(2).xComponent, (int)this.getRealPointPosition(2).yComponent, (int)(this.position.xComponent+dist*Math.cos(Math.toRadians(180+this.getDirection()))), (int)(this.position.yComponent+dist*Math.sin(Math.toRadians(180+this.getDirection()))));
		if (Math.random() <.5) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawLine((int)this.getRealPointPosition(1).xComponent, (int)this.getRealPointPosition(1).yComponent, (int)(this.position.xComponent+(dist*.75)*Math.cos(Math.toRadians(180+this.getDirection()))), (int)(this.position.yComponent+(dist*.75)*Math.sin(Math.toRadians(180+this.getDirection()))));
		g.drawLine((int)this.getRealPointPosition(2).xComponent, (int)this.getRealPointPosition(2).yComponent, (int)(this.position.xComponent+(dist*.75)*Math.cos(Math.toRadians(180+this.getDirection()))), (int)(this.position.yComponent+(dist*.75)*Math.sin(Math.toRadians(180+this.getDirection()))));
	}
	
	// applies the physics
	public void applyShipPhysics (Physics p) {
		this.applyPhysics(p, 'f');	
		this.rotate(this.angle);
		this.angleReset();
		this.shipWeapon.useWeapon(p, (int)this.position.xComponent, (int)this.position.yComponent);
	}
}
