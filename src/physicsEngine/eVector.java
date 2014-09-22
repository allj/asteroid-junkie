package physicsEngine;

// the vector class v = (x,y,z) = (Point A, Point B), not to be confused with the Vector class in java.util, which is a dynamic array
public class eVector extends Point {

	// 3d constructor
	public eVector(double xComponent, double yComponent, double zComponent) {
		super(xComponent, yComponent, zComponent);
	}

	// 2d constructor
	public eVector(double xComponent, double yComponent) {
		super(xComponent, yComponent);
	}

	// Vector constructor using 2 points AB = B - A, BA = A - B
	public eVector(Point A, Point B) {
		this.xComponent = B.xComponent - A.xComponent;
		this.yComponent = B.yComponent - A.yComponent;
		this.zComponent = B.zComponent - A.zComponent;
		if (A.getdimension() == 2 || B.getdimension() == 2)
			this.setdimension(2);
		else if (A.getdimension() == 3 || B.getdimension() == 3)
			this.setdimension(3);
	}

	// returns multiple of this vector
	public eVector getMultiple(double t) {
		eVector newVector = new eVector(this.xComponent * t, this.yComponent * t, this.zComponent * t);
		return newVector;
	}
}
