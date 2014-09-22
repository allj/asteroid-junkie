package physicsEngine;

//the line class, not tested yet
public class Line {
	protected Point P; // point on the line
	protected eVector d; // direction vector
	protected float t; // t is a direction vector multiple
	protected byte dimension = 0; // dimension 2=2d, 3=3d

	// constructs a line in the form r = P + td or (xComponent,yCoordinate,zComponent) = (x0,y0,0) +
	// t(xComponent,yComponent,zComponent)
	public Line(Point p, eVector direction) {
		this.P = p;
		this.d = direction;
		this.dimension = (byte) p.getdimension();
	}
	
	public Line(Point A, Point B) {
		this.P = A;
		this.d = new eVector(A,B);
		this.dimension =  A.getdimension();
	}
	
	public Line(double x1, double y1,double x2,double y2) {
		this.P = new Point(x1,y1);
		this.d = new eVector(new Point(x1,y1),new Point(x2,y2));
		this.dimension = 2;
	}
	

	// converts this line into a string
	public String toString(char form) {
		return P.toString() + " +  t" + d.toString();
	}
}