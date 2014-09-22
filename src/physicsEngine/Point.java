package physicsEngine;

//the point class
public class Point {
	
	// the coordinates, starts at origin
	protected float xComponent = 0;
	protected float yComponent = 0;
	protected float zComponent = 0; 
	
	private byte dimension = 0; // dimension 2=2d, 3=3d

	// empty constructor, will probably give errors if deleted, used by subclasses, there just in case...
	public Point() {
	}

	// 3D constructor
	public Point(double newX, double newY, double newZ) {
		this.xComponent = (float)newX;
		this.yComponent = (float)newY;
		this.zComponent = (float)newZ;
		this.dimension = 3;
	}

	// 2D constructor
	public Point(double newX, double newY) {
		this.xComponent = (float)newX;
		this.yComponent = (float)newY;
		this.dimension = 2;
	}
	
	public Point(Point A, Point B) {
		this.xComponent = A.xComponent+B.xComponent;
		this.yComponent = A.yComponent+B.yComponent;
		this.zComponent = A.zComponent+B.zComponent;
		this.dimension = A.dimension;
	}

	// set methods set the value of the indicated component
	public void setComponents(double x, double y) {
		this.xComponent = (float)x;
		this.yComponent = (float)y;
		this.dimension = 2;
	}
	
	public void setComponents(Point newPoint) {
		this.xComponent = newPoint.xComponent;
		this.yComponent = newPoint.yComponent;
		this.zComponent = newPoint.zComponent;
	}
	
	public void setComponents(double x, double y, double z) {
		this.xComponent = (float)x;
		this.yComponent = (float)y;
		this.yComponent = (float)z;
		this.dimension = 3;
	}
	
	public void setX(double newX) {
		this.xComponent = (float)newX;
	}

	public void setY(double newY) {
		this.yComponent = (float)newY;
	}

	public void setZ(double newZ) {
		this.zComponent = (float)newZ;
		this.dimension = 3;
	}

	// get methods get the value of the indicated component
	public float getX() {
		return this.xComponent;
	}

	public float getY() {
		return this.yComponent;
	}

	public float getZ() {
		return this.zComponent;
	}
	
	// sets the dimension
	public byte getdimension() {
		return this.dimension;	
	}
	
	// sets the dimension
	public void setdimension(int newdimension) {
		this.dimension = (byte)newdimension;
		if (newdimension == 2) 
			this.zComponent = 0; // sets zComponent Component to zero
	}

	// converts this line into a string
	public String toString() {
		// returns the proper format if dimension is only 2, (x,y) instead of (x,y,0)
		if (this.dimension == 2)
			return "(" + String.valueOf(this.getX()) + ", " + String.valueOf(this.getY())
					+ ")";

		return "(" + String.valueOf(this.getX()) + ", " + String.valueOf(this.getY())
				+ ", " + String.valueOf(this.getZ()) + ")";
	}

	// converts to array, not used yet
	public float[] toArray() {
		float[] xyz = new float[3];

		xyz[0] = this.getX();
		xyz[1] = this.getY();
		xyz[2] = this.getZ();

		return xyz;
	}

	// converts point to vector
	public eVector toVector() {
		eVector newVector = new eVector(this.getX(), this.getY(), this.getZ());
		newVector.setdimension(this.dimension);
		return newVector;
	}

	// checks if point is equal to another point
	public boolean equalTo(Point aPoint) {
		if (this.getX() == aPoint.getX() && this.getY() == aPoint.getY() && this.getZ() == aPoint.getZ())
			return true;
		else
			return false;
	}

	// adds points with this point
	public void addPoint(Point A) {
		this.setX(this.getX()+A.getX());
		this.setY(this.getY()+A.getY());
		if (A.dimension == 3)
			this.setX(this.getZ()+A.getZ());
	}

	// adds points with this point
	public void subtractPoint(Point A) {
		this.setX(this.getX()-A.getX());
		this.setY(this.getY()-A.getY());
		if (A.dimension == 3)
			this.setX(this.getZ()-A.getZ());
	}

	// calculates distance using pythagorean theorem
	public float dist(Point A) {
		return (float)(Math.sqrt(Math.pow(this.getX() - A.getX(), 2) + Math.pow(this.getY() - A.getY(), 2)
				+ Math.pow(this.getZ() - A.getZ(), 2)));
	}
	
	// returns the distance of this point to the origin
	public float getMagnitude() {
		return (float)(Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2)
				+ Math.pow(this.getZ(), 2)));
	}
}

