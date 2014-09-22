// Wave2.java
/*
 * This is the second wave.
 * It is two circle patterns moving
 * back and forth.  
 */
package spaceJunkie;

import java.awt.Color;
import java.awt.Graphics;


import physicsEngine.Physics;
import physicsEngine.PhysicsObject;

public class Wave2 extends Wave {

    PhysicsObject enemyFormation = new PhysicsObject(300, 200, 10);
    double angle = 0;
    double enemyWidth;
    double enemyHeight;
    double enemyRotationRate;
    double enemySpeed;

    @Override
    void draw(Graphics g) {
        drawWave2A(g, new Color(230, 200, 230), (int) this.enemyFormation.getPosition().getX(), (int) this.enemyFormation.getPosition().getY(), this.enemyWidth, this.enemyHeight, 26);
        drawWave2B(g, new Color(230, 200, 230), SpaceJunkieGame.SCREEN_WIDTH - (int) (this.enemyFormation.getPosition().getX()), (int) this.enemyFormation.getPosition().getY(), -this.enemyWidth, this.enemyHeight, 26);
    }

    private void drawWave2A(Graphics g, Color c, int x, int y, double width, double height, double dist) {
        for (int i = 0; i < this.numEnemies / 2; i++) {

            this.enemies[i].setPosition((int) (x + (width * Math.cos(Math.toRadians(dist * i + this.angle)))), (int) (y + (height * Math.sin(Math.toRadians(dist * i + this.angle)))));

            this.enemies[i].alienWeapon.drawGradient(g);

            this.drawExplosion(g, i);
            if (this.enemies[i].isDestroyed) {
                this.enemies[i].getPosition().setX(-1000);
                continue;
            }
            this.drawEnemy(g, c, (int) this.enemies[i].getPosition().getX(), (int) this.enemies[i].getPosition().getY(), this.enemySize);
        }
    }

    private void drawWave2B(Graphics g, Color c, int x, int y, double width, double height, double dist) {
        for (int i = 0; i < this.numEnemies / 2; i++) {

            this.enemies[i + (this.numEnemies / 2)].setPosition((int) (x + (width * Math.cos(Math.toRadians(dist * i + this.angle)))), (int) (y + (height * Math.sin(Math.toRadians(dist * i + this.angle)))));
            this.enemies[i + (this.numEnemies / 2)].alienWeapon.drawGradient(g);

            this.drawExplosion(g, i + (this.numEnemies / 2));
            if (this.enemies[i + (this.numEnemies / 2)].isDestroyed) {
                this.enemies[i + (this.numEnemies / 2)].getPosition().setX(-1000);
                continue;
            }
            this.drawEnemy(g, c, (int) this.enemies[i + (this.numEnemies / 2)].getPosition().getX(), (int) this.enemies[i + (this.numEnemies / 2)].getPosition().getY(), this.enemySize);
        }
    }

    @Override
    void init() {
        this.numEnemies = 28;
        enemies = new JunkieAlien[this.numEnemies];
        for (int i = 0; i < this.numEnemies; i++) {
            enemies[i] = new JunkieAlien(0, 0, 1);
            this.enemies[i].isDestroyed = false;
        }
        this.enemyShotProb = .1f;
        this.enemySize = 18;
        this.enemyShotSpeed = 3;
        this.enemyWidth = 75;
        this.enemyHeight = 75;
        this.enemyFormation.setPosition(300, 200);
        this.enemySpeed = 1.5;
        this.enemyRotationRate = 1.2;
        this.enemyFormation.setVelocity(this.enemySpeed, this.enemySpeed / 2.0);
    }

    @Override
    void move(Physics p) {
        this.waveTime += .001;
        this.angle += this.enemyRotationRate;
        this.enemyFormation.applyPhysics(p, 'f');
        for (int i = 0; i < this.numEnemies; i++) {
            this.enemies[i].applyAlienPhysics(p);
        }

        if (this.enemyFormation.getPosition().getX() >= SpaceJunkieGame.SCREEN_WIDTH - this.enemyWidth || this.enemyFormation.getPosition().getX() <= this.enemyWidth) {
            this.enemyFormation.getVelocity().setX(-this.enemyFormation.getVelocity().getX());
            this.enemyRotationRate = -this.enemyRotationRate;
        }
        if (this.enemyFormation.getPosition().getY() >= 300 || this.enemyFormation.getPosition().getY() <= this.enemyHeight + 20) {
            this.enemyFormation.getVelocity().setY(-this.enemyFormation.getVelocity().getY());
        }
    }

    @Override
    void drawDebugInfo(Graphics g) {
        for (int i = 0; i < this.numEnemies; i++) {
            if (this.enemies[i].isDestroyed) {
                g.setColor(Color.darkGray);
            } else {
                g.setColor(Color.gray);
            }
            g.drawString("Enemy " + (i + 1) + ": " + this.enemies[i].getPosition().toString() + "-------" + this.enemies[i].getVelocity().toString(), 1, 30 + i * 10);
        }
        g.setColor(Color.gray);
        g.drawString("Wave Time: " + this.waveTime, 1, 330);
        g.drawString("Enemy Size: " + this.enemySize, 1, 340);
        g.drawString("Enemy Shot Speed: " + this.enemyShotSpeed, 1, 350);
        g.drawString("Enemy Shot Probability: " + this.enemyShotProb, 1, 360);
        g.drawString("Number of Enemies: " + this.numEnemies, 1, 370);
        g.drawString("Angle: " + this.angle, 1, 380);
    }
}
