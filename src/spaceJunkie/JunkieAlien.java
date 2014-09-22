// JunkieAlien.java

/*
 * This class is the JunkieAlien class
 * These are the enemies that the
 * player will face. The Wave classes
 * contain an array of these aliens.
 */
package spaceJunkie;

import java.awt.Color;

import physicsEngine.Physics;
import physicsEngine.PhysicsObject;
import physicsEngine.Weapon;
import physicsEngine.eVector;
import physicsEngine.ExplosionAnimation;

//the JunkieAlien Class
class JunkieAlien extends PhysicsObject {
	Weapon alienWeapon;	
	double size = 10;
	int hp = 1; // hit points
	boolean isDestroyed = false;
	ExplosionAnimation explosion;
	
	// contructor
	public JunkieAlien(int x,int y, double m) {
		super(x,y,m);
		alienWeapon = new Weapon(100,1,1, new eVector(9,9));
		this.alienWeapon.setProjectileColor(new Color(220,0,220));
	}
	
	// explodes alien
	public void explode(int r) {
		this.explosion = new ExplosionAnimation((int)this.getPosition().getX(),(int)this.getPosition().getY(),r);
	}
	
	// sets weapon
	public void setWeapon(double rof, int cs, double d){
		this.alienWeapon = new Weapon(rof,cs,d,new eVector(9,9));
		this.alienWeapon.setProjectileColor(new Color(220,0,220));
	}
	
	// applies physics
	public void applyAlienPhysics(Physics p) {
		this.applyPhysics(p, 'f'); // applies specified physics
		this.alienWeapon.useWeapon(p, (int)this.getPosition().getX(), (int)this.getPosition().getY()); // use the weapon
	}
}

