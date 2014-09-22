package physicsEngine;

//the Projectile class
public class Projectile extends PhysicsObject {
	boolean isFired = false;
	eVector size;
	
	// constructor
	Projectile(int x,int y, double m, eVector s){
		super(x,y,m);
		this.size = s;
	}
	
	// launches projectile
	void fire(double angle, double power){
		this.velocity = new eVector(Math.cos(Math.toRadians(angle))*power,Math.sin(Math.toRadians(angle))*power);
		this.isFired = true;
	}
}
