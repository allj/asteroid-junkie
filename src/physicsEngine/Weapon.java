package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;

//the weapon class
public class Weapon {
	public Projectile[] projectile;
	public float rateOfFire;
	public float fireTime = 0;
	public float damage;
	public float heat;
	public float maxHeat = 100;
	public float heatRate = 7;
	public float coolRate = .1f;
	public short shotNum;
	public short clipSize;
	public boolean isFired = false;
	public boolean isOverheated = false;
	
	
	// constructor
	public Weapon(double rof, int cs, double d,eVector ps){
		this.rateOfFire = (float)rof;
		this.clipSize = (short)cs;
		this.damage = (float)d;
		projectile = new Projectile[cs];
		for (int i=0;i<this.clipSize;i++) {
			projectile[i] = new Projectile(0,0,1,ps);
		}
	}
	
	// fires weapon
	public void fire(double power,double angle){
		if (this.fireTime == 0 && !this.isOverheated) {
			this.heat += this.heatRate;
			this.projectile[this.shotNum].fire(angle, power);
			this.shotNum++;
			this.isFired = true;
		}
	}
	
	// sets heat rate
	public void setHeatRate(double newRate){
		this.heatRate = (float)newRate;
	}
	
	// sets cool rate
	public void setCoolRate(double newRate){
		this.coolRate = (float)newRate;
	}
	
	// sets rate of fire
	public void setRateOfFireRate(double newRate){
		this.rateOfFire = (float)newRate;
	}
	
	// sets clip size
	public void setClipSize(int newClipSize){
		this.clipSize = (short)newClipSize;
	}
	
	// sets clip size
	public void setDamage(double newDamageAmount){
		this.damage = (float)newDamageAmount;
	}
	
	// sets projectile color
	public void setProjectileColor(Color c){
		for (int n = 0; n < this.clipSize;n++){
			try {
				this.projectile[n].setColor(c);
			} catch (Exception e) {
				this.projectile[n].setColor(Color.WHITE);
			}
		}
	}
	
	// applys the heating
	void applyHeat() {
		if (this.heat > this.maxHeat){
			this.isOverheated = true;
			this.heat = this.maxHeat;
		} else if (this.heat < 0){
			this.heat = 0;
		}
		if (this.isOverheated && this.heat < 25){
			this.isOverheated = false;
		}
		if (this.heat > 0){
			this.heat -= this.coolRate;
		}
	}
	
	// uses the weapon
	public void useWeapon(Physics p, int x, int y){
		if (this.isFired){
			this.fireTime ++;
		}
		if (this.fireTime > this.rateOfFire) {
	        this.fireTime = 0;
	        this.isFired = false;
		}
	    if (this.shotNum >= this.clipSize){
	       this.shotNum = 0;
	    }
	    for (int n = 0; n < this.clipSize;n++){
	    	Physics.applyMotion(this.projectile[n]);
	    	if (this.projectile[n].getPosition().getX() > p.SCREEN_WIDTH 
	    			|| this.projectile[n].getPosition().getY() < 0 
	    			|| this.projectile[n].getPosition().getX() < 0 
	    			|| this.projectile[n].getPosition().getY() > p.SCREEN_HEIGHT) {
	            resetProjectile(n,x,y);
	    	}
	        if (this.projectile[n].isFired == false){
	            this.projectile[n].setPosition(x,y);
	        }
	    }
	    this.applyHeat();
	}
	
	// resets the projectile
	public void resetProjectile(int n, int x, int y){
		this.projectile[n].setPosition(x,y);
        Physics.stopObject(this.projectile[n]);
        this.projectile[n].isFired = false;
	}
	
	// draws with gradient
	public void drawGradient(Graphics g){
		for (int i= 0;i<this.clipSize;i++) {
			if (this.projectile[i].isFired) {
				g.setColor(new Color((int)(this.projectile[i].color.getRed()*.45),(int)(this.projectile[i].color.getGreen()*.45),(int)(this.projectile[i].color.getBlue()*.45)));
				g.fillOval((int)this.projectile[i].position.xComponent-(int)(this.projectile[i].size.xComponent/2.0), 
						(int)this.projectile[i].position.yComponent-(int)(this.projectile[i].size.xComponent/2.0),
						(int)this.projectile[i].size.xComponent, 
						(int)this.projectile[i].size.yComponent);
				g.setColor(new Color((int)(this.projectile[i].color.getRed()*.6),(int)(this.projectile[i].color.getGreen()*.6),(int)(this.projectile[i].color.getBlue()*.6)));
				g.fillOval((int)this.projectile[i].position.xComponent-(int)(this.projectile[i].size.xComponent/3.0), 
						(int)this.projectile[i].position.yComponent-(int)(this.projectile[i].size.xComponent/3.0),
						(int)(this.projectile[i].size.xComponent/1.5), 
						(int)(this.projectile[i].size.xComponent/1.5));
				g.setColor(this.projectile[i].color);
				g.fillOval((int)this.projectile[i].position.xComponent-(int)(this.projectile[i].size.xComponent/4.0), 
						(int)this.projectile[i].position.yComponent-(int)(this.projectile[i].size.xComponent/4.0),
						(int)(this.projectile[i].size.xComponent/2.0), 
						(int)(this.projectile[i].size.xComponent/2.0));
			}
		}
	}
	
	// draws projectile
	public void draw(Graphics g){
		for (int i= 0;i<this.clipSize;i++) {
			if (this.projectile[i].isFired) {
				g.setColor(this.projectile[i].color);
				g.fillOval((int)this.projectile[i].position.xComponent-(int)(this.projectile[i].size.xComponent/2.0), 
						(int)this.projectile[i].position.yComponent-(int)(this.projectile[i].size.xComponent/2.0),
						(int)this.projectile[i].size.xComponent, 
						(int)this.projectile[i].size.yComponent);
			}
		}
	}
}
