package physicsEngine;

import java.awt.Color;
import java.awt.Graphics;

//the PolygonObject class
public class PolygonObject extends PhysicsObject {

    protected Point[] pointPosition;
    protected Point[] initialPointPosition;
    protected float size = 0;
    protected short numPoints = 0;

    // constructor
    public PolygonObject() {
    }

    // constructor
    public PolygonObject(Point[] pos, Point c, int np, double s, double m) {
        this.numPoints = (short) np;
        this.position = new Point(c.xComponent, c.yComponent);

        this.initialPointPosition = new Point[this.numPoints];
        this.pointPosition = new Point[this.numPoints];

        this.size = (float) s;
        this.setMass(m);

        for (int i = 0; i < this.numPoints; i++) {
            this.initialPointPosition[i] = new Point(pos[i].xComponent * s, pos[i].yComponent * s);
            this.pointPosition[i] = new Point(pos[i].xComponent * s, pos[i].yComponent * s);
        }
    }

    // rotates object
    public void rotate(double theta) {
        for (int i = 0; i < this.numPoints; i++) {
            this.pointPosition[i].xComponent = (int) (this.initialPointPosition[i].getMagnitude() * (Math.cos(Math.toRadians(this.getInitialPointAngle(i) + theta))));
            this.pointPosition[i].yComponent = (int) (this.initialPointPosition[i].getMagnitude() * (Math.sin(Math.toRadians(this.getInitialPointAngle(i) + theta))));
        }
    }

    // sets size of object
    public double getSize() {
        return this.size;
    }

    public void setSize(double s) {
        for (int i = 0; i < this.numPoints; i++) {
            this.initialPointPosition[i].xComponent /= this.size;
            this.initialPointPosition[i].yComponent /= this.size;
            this.pointPosition[i].xComponent /= this.size;
            this.pointPosition[i].yComponent /= this.size;
            this.initialPointPosition[i].xComponent *= s;
            this.initialPointPosition[i].yComponent *= s;
            this.pointPosition[i].xComponent *= s;
            this.pointPosition[i].yComponent *= s;
        }
        this.size = (float) s;
    }

    // gets initial angle of point to centre
    public double getInitialPointAngle(int n) {
        return Physics.calculateAngle((int) (this.initialPointPosition[n].xComponent),
                (int) (this.initialPointPosition[n].yComponent),
                0,
                0);
    }

    // gets current angle of point to centre
    public double getPointAngle(int n) {
        return Physics.calculateAngle((int) (this.pointPosition[n].xComponent),
                (int) (this.pointPosition[n].yComponent),
                0,
                0);
    }

    // gets the actual position of any point on this polygon
    public Point getRealPointPosition(int n) {
        return new Point(this.position, this.pointPosition[n]);
    }

    // gets the actual position of any  initial point on this polygon
    public Point getRealInitialPointPosition(int n) {
        return new Point(this.initialPointPosition[n], this.pointPosition[n]);
    }

    public void sortPoints() {
    }

    // draws only an outline
    public void drawOutline(Graphics g) {
        g.setColor(this.color);
        for (int i = 0; i < this.numPoints; i++) {
            if (i == this.numPoints - 1) {
                g.drawLine((int) (this.position.xComponent + this.pointPosition[this.numPoints - 1].xComponent),
                        (int) (this.position.yComponent + this.pointPosition[this.numPoints - 1].yComponent),
                        (int) (this.position.xComponent + this.pointPosition[0].xComponent),
                        (int) (this.position.yComponent + this.pointPosition[0].yComponent));
            } else {
                g.drawLine((int) (this.position.xComponent + this.pointPosition[i].xComponent),
                        (int) (this.position.yComponent + this.pointPosition[i].yComponent),
                        (int) (this.position.xComponent + this.pointPosition[i + 1].xComponent),
                        (int) (this.position.yComponent + this.pointPosition[i + 1].yComponent));
            }
        }
        g.fillOval((int) this.position.xComponent, (int) this.position.yComponent, 2, 2);
    }

    // draws a wire frame
    public void drawWireframe(Graphics g) {
        g.setColor(this.color);
        for (int i = 0; i < this.numPoints; i++) {
            for (int n = 0; n < this.numPoints; n++) {
                if (i == n || n > i) {
                    continue;
                }
                g.drawLine((int) (this.getRealPointPosition(i).xComponent),
                        (int) (this.getRealPointPosition(i).yComponent),
                        (int) (this.getRealPointPosition(n).xComponent),
                        (int) (this.getRealPointPosition(n).yComponent));
            }
        }
        for (int i = 0; i < this.numPoints; i++) {
            g.drawLine((int) (this.getRealPointPosition(i).xComponent),
                    (int) (this.getRealPointPosition(i).yComponent),
                    (int) (this.position.xComponent),
                    (int) (this.position.yComponent));
        }

        //g.setColor(color.RED);
        //g.fillOval((int)this.position.xComponent-2, (int)this.position.yComponent-2, 5, 5);
    }

    public void drawFilled2(Graphics g) {
        Physics p = new Physics();
        Point[] tempPoints = new Point[this.numPoints];
        int[] x = new int[this.numPoints];
        int[] y = new int[this.numPoints];
        x = Physics.sort(x, 0, numPoints - 1);
        y = Physics.sort(y, 0, numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            tempPoints[i] = this.getRealPointPosition(i);
            x[i] = (int) tempPoints[i].xComponent;
            y[i] = (int) tempPoints[i].yComponent;
            //System.out.println(tempPoints[i].toString());
        }
        g.setColor(this.color);
        g.fillPolygon(x, y, this.numPoints);
        tempPoints = p.sort(tempPoints, this.position, 0, this.numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            x[i] = (int) tempPoints[i].xComponent;
            y[i] = (int) tempPoints[i].yComponent;
        }
        g.setColor(this.color);
        g.fillPolygon(x, y, this.numPoints);
        for (int i = 0; i < numPoints; i++) {
            g.setColor(Color.RED);
            g.fillOval((int) this.getRealPointPosition(i).xComponent, (int) this.getRealPointPosition(i).yComponent, 2, 2);
        }
        g.setColor(Color.yellow);
        g.fillOval((int) this.position.xComponent, (int) this.position.yComponent, 2, 2);
    }

    public void drawFilled(Graphics g) {
        int[] x = new int[this.numPoints];
        int[] y = new int[this.numPoints];
        for (int i = 0; i < numPoints; i++) {
            x[i] = (int) this.getRealPointPosition(i).xComponent;
            y[i] = (int) this.getRealPointPosition(i).yComponent;
            //System.out.println(tempPoints[i].toString());
        }
        g.setColor(this.color);
        g.fillPolygon(x, y, this.numPoints);
        for (int i = 0; i < numPoints; i++) {
            g.setColor(Color.RED);
            g.fillOval((int) this.getRealPointPosition(i).xComponent, (int) this.getRealPointPosition(i).yComponent, 2, 2);
        }
        g.setColor(Color.yellow);
        g.fillOval((int) this.position.xComponent, (int) this.position.yComponent, 2, 2);
    }
}
