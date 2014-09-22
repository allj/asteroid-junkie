package physicsEngine;

//the rectangle class
public class Rectangle {
	Point position = new Point();
	short height;
	short width;
	
	Rectangle(int x,int y,int w,int h) {
		position = new Point (x,y);
		width = (short)w;
		height = (short)h;
	}
}
