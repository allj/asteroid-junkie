// Physics.java

/*
 * This is the Physic class.
 * It contains a bunch of physics related methods
 * There's collision detection and object motion
 * methods. Everything should be self explanatory.
 * Floats are used instead of doubles throughout the
 * physics engine in order to improve efficiency.
 */

package physicsEngine;

import java.util.Random;


// the Physics class
// all methods should be self explanatory
public class Physics {
	public float GRAVITY;
	public float FRICTION;
	public float BOUNCE;
	public float WIND;
    
	public short SCREEN_WIDTH;
	public short SCREEN_HEIGHT;
        
	public Physics() {
        this.GRAVITY = .98f;
        this.FRICTION = .99f;
        this.BOUNCE = .4f;
        this.WIND = 0f;
    }

	public Physics(double g, double f, double b, double w, int WW, int WH) {
    	this.GRAVITY = (float)g;
    	this.FRICTION = (float)f;
    	this.BOUNCE = (float)b;
    	this.WIND = (float)w;
    	this.SCREEN_WIDTH = (short)WW;
    	this.SCREEN_HEIGHT = (short)WH;     
    }
       
	public static Point applyMotion (PhysicsObject object) {
    	//Point p = position;
    	object.position.xComponent += object.velocity.xComponent;
    	object.position.yComponent += object.velocity.yComponent;
    	return object.position;
    }
    
	public static eVector stopObject(PhysicsObject object) {
    	object.velocity.xComponent = 0;
    	object.velocity.yComponent = 0;
        return object.velocity;
    }
    
	public static eVector accelerateObject(PhysicsObject object, eVector objectAcceleration) {
    	object.velocity.xComponent += objectAcceleration.xComponent * (1 / object.getMass());
    	object.velocity.yComponent += objectAcceleration.yComponent * (1 / object.getMass());
        return object.velocity;
    }
      
	public eVector applyFriction(PhysicsObject object) {
        object.velocity.xComponent *= this.FRICTION;
        object.velocity.yComponent *= this.FRICTION;
        return object.velocity;
    }
    
	public eVector applyGravity(PhysicsObject object) {
        object.velocity.yComponent += this.GRAVITY;
        return object.velocity;
    }
    
	public static eVector applyBounce (PhysicsObject object, double theta) {
    	//object.velocity = new eVector(object.velocity.xComponent*Math.cos(Math.toRadians(theta+180)),object.velocity.yComponent*Math.sin(Math.toRadians(theta+180)));
    	object.velocity = new eVector(-object.velocity.xComponent,-object.velocity.yComponent);

    	return object.velocity;
    }
    
    // checks whether the box formed by the line between points A and B contains point C
	public static boolean contains(Point A, Point B, Point C) {
    	if ((C.xComponent <= Math.max(A.xComponent, B.xComponent)
				&& C.xComponent >= Math.min(A.xComponent, B.xComponent)
				&& C.yComponent <= Math.max(A.yComponent, B.yComponent)
				&& C.yComponent >= Math.min(A.yComponent, B.yComponent))){
    		return true;
		} else {
			return false;
		}
    }
    
	public static boolean contains(Point A, Point B, PolygonObject object) {
    	for (int i = 0; i< object.numPoints;i++) {
    		if (contains(A,B,object.pointPosition[i])){
    			return true;
    		}
    	}
    	return false;
    }
    
	public static boolean collisionCheck(Point P, Line line, int collisionBuffer) {
    		if (distance(P, line)<=collisionBuffer) {
    			return true;
    		}
    	return false;
    }
    
	public static boolean collisionCheck(Point P, Point A, Point B, int collisionBuffer) {
		if (distance(P, new Line(A,B))<=collisionBuffer) {
			return true;
		}
	return false;
}	
	public static boolean collisionCheck(Point P, RectangleObject object,  int collisionBuffer) {
		if (contains(new Point(object.position.xComponent-(object.size.xComponent/2)-collisionBuffer,object.position.yComponent-(object.size.yComponent/2)-collisionBuffer), new Point(object.position.xComponent+(object.size.xComponent/2)+collisionBuffer,object.position.yComponent+(object.size.yComponent/2)+collisionBuffer),P)) 
			return true;
		
    	return false;
    }
    
	public static boolean collisionCheck(PhysicsObject object, Line line, int collisionBuffer) {
		if (collisionCheck(object.position,line,collisionBuffer)) 
			return true;
		
    	return false;
    }
    
	public static boolean collisionCheck(RectangleObject object, Line line, int collisionBuffer) {
    	if (collisionCheck(object.position,line,collisionBuffer)) 
			return true;
		if (collisionCheck(new Point(object.position,new Point(object.size.xComponent/2.0,object.size.yComponent/2.0)),line,collisionBuffer)) 
			return true;
		if (collisionCheck(new Point(object.position,new Point(-object.size.xComponent/2.0,object.size.yComponent/2.0)),line,collisionBuffer)) 
			return true;
		if (collisionCheck(new Point(object.position,new Point(object.size.xComponent/2.0,-object.size.yComponent/2.0)),line,collisionBuffer)) 
			return true;
		if (collisionCheck(new Point(object.position,new Point(-object.size.xComponent/2.0,-object.size.yComponent/2.0)),line,collisionBuffer)) 
			return true;
		
    	return false;
    }
    
	public static boolean collisionCheck(RectangleObject object, Point A, Point B, int collisionBuffer) {
    	boolean collision = false;
    	if (contains(A,B,object.position)==false) {
    		collision = false;
    	} else {
    		if (collisionCheck(object.position,new Line(A,B),collisionBuffer)) 
    			return true;
    	}
    	
    	if (contains(A,B,new Point(object.position,new Point(object.size.xComponent/2.0,object.size.yComponent/2.0)))==false) {
    		collision = false;
    	} else {
    		if (collisionCheck(new Point(object.position,new Point(object.size.xComponent/2.0,object.size.yComponent/2.0)),new Line(A,B),collisionBuffer)) 
    			return true;
    	}
    	
    	if (contains(A,B,new Point(object.position,new Point(-object.size.xComponent/2.0,-object.size.yComponent/2.0)))==false) {
    		collision = false;
    	} else {
    		if (collisionCheck(new Point(object.position,new Point(-object.size.xComponent/2.0,-object.size.yComponent/2.0)),new Line(A,B),collisionBuffer)) 
    			return true;
    	}
    	
    	if (contains(A,B,new Point(object.position,new Point(-object.size.xComponent/2.0,object.size.yComponent/2.0)))==false) {
    		collision = false;
    	} else {
    		if (collisionCheck(new Point(object.position,new Point(-object.size.xComponent/2.0,object.size.yComponent/2.0)),new Line(A,B),collisionBuffer)) 
    			return true;
    	}
		
    	if (contains(A,B,new Point(object.position,new Point(object.size.xComponent/2.0,-object.size.yComponent/2.0)))==false) {
    		collision = false;
    	} else {
    		if (collisionCheck(new Point(object.position,new Point(object.size.xComponent/2.0,-object.size.yComponent/2.0)),new Line(A,B),collisionBuffer)) 
    			return true;
    	}
		
    	return collision;
    }
    
	public static boolean collisionCheck(PhysicsObject object, Point A, Point B, int collisionBuffer) {
    	if (Physics.contains(A,B,object.position)==false) {
			return false;
    	}
		if (collisionCheck(object.position,A,B,collisionBuffer)) {
			return true;
		}
		
    	return false;
    }
    
	public static boolean collisionCheck(PhysicsObject object, PolygonObject object2, int collisionBuffer) {
    	for (int i = 0; i< object2.numPoints;i++) {
    		for (int j = 0; j< object2.numPoints;j++) {
    			if (j>=i) break;
	    		if (Physics.collisionCheck(object, object2.getRealPointPosition(i), object2.getRealPointPosition(j), collisionBuffer)) {
	    			return true;
	    		}
    		}
    		if (collisionCheck(object, object2.position, object2.getRealPointPosition(i), collisionBuffer)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public static boolean collisionCheck(PolygonObject object, Line line, int collisionBuffer) {
    	for (int i = 0; i< object.numPoints;i++) {
    		if (Physics.distance(object.getRealPointPosition(i), line)<=collisionBuffer) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public static boolean collisionCheck(PolygonObject object, Point A, Point B, int collisionBuffer) {	
    	for (int i = 0; i< object.numPoints;i++) {
    		if (Physics.contains(A,B,object.getRealPointPosition(i))==false)
    			continue;
    		if (Physics.collisionCheck(object.getRealPointPosition(i), A, B, collisionBuffer))
    			return true;    		
    	}
    	return false;
    }
    
	public boolean collisionCheck(PolygonObject object, PolygonObject object2, int collisionBuffer) {
    	for (int i = 0; i< object2.numPoints;i++) {
    		for (int j = 0; j< object2.numPoints;j++) {
    			if (i==j||j>i) continue;
	    		if (Physics.collisionCheck(object, object2.getRealPointPosition(i), object2.getRealPointPosition(j), collisionBuffer)) {
	    			return true;
	    		}
    		}
    		if (Physics.collisionCheck(object, object2.position, object2.getRealPointPosition(i), collisionBuffer)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public static double distance(Point A, Line L){
		float c = -(-L.d.yComponent*L.P.xComponent)-(L.d.xComponent*L.P.yComponent);
    	return Math.abs(((-L.d.yComponent*A.xComponent)+(L.d.xComponent*A.yComponent)+ c))/(float)Math.sqrt(Math.pow(L.d.xComponent,2)+Math.pow(L.d.yComponent,2)) ;
    }
    
	public static double distance(Point A, Point B){
    	return Math.sqrt(Math.pow(Math.abs(A.xComponent-B.xComponent), 2)+Math.pow(Math.abs(A.yComponent-B.yComponent), 2));
    }
    
	public boolean offscreenCheck(PhysicsObject object){
    	boolean isOffscreen = false;
    		if (object.position.xComponent<0 
    				|| object.position.yComponent<0
    				|| object.position.yComponent>this.SCREEN_HEIGHT
    				|| object.position.xComponent>this.SCREEN_WIDTH) {
    			isOffscreen = true;
    		} else {
    			return false;
    		}
    	return isOffscreen;
    }
    
	public boolean offscreenCheck(PolygonObject object){
    	boolean isOffscreen = false;
    	for (int i =0;i< object.numPoints;i++) {
    		if (object.getRealPointPosition(i).xComponent<0 
    				|| object.getRealPointPosition(i).yComponent<0
    				|| object.getRealPointPosition(i).yComponent>this.SCREEN_HEIGHT
    				|| object.getRealPointPosition(i).xComponent>this.SCREEN_WIDTH) {
    			isOffscreen = true;
    		} else {
    			return false;
    		}
    	}
    	if (object.position.xComponent<0 
				|| object.position.yComponent<0
				|| object.position.yComponent>this.SCREEN_HEIGHT
				|| object.position.xComponent>this.SCREEN_WIDTH) {
			isOffscreen = true;
		} else {
			return false;
		}
    	return isOffscreen;
    }
    
	public static float calculateAngle(int xPosition2, int yPosition2, int xPosition1,
			int yPosition1) {
		float theta = 0.0f;
		// quadrant 1
		if (xPosition2 >= xPosition1 && yPosition2 <= yPosition1) {
			try {
				theta = (float)(Math.atan((float) (yPosition1 - yPosition2)
						/ (float) (xPosition2 - xPosition1)));
				theta = (float)Math.toDegrees(theta);
			} catch (Exception e) {
				theta = 0;
			}
			// quadrant 2
		} else if (xPosition2 <= xPosition1 && yPosition2 <= yPosition1) {
			try {
				theta = (float)(Math.atan((float) (yPosition1 - yPosition2)
						/ (float) (xPosition1 - xPosition2)));
				theta = (float)Math.toDegrees(theta);
			} catch (Exception e) {
				theta = 0;
			}
			theta = (90 - theta) + 90;
			// quadrant 3
		} else if (xPosition2 <= xPosition1 && yPosition2 >= yPosition1) {
			try {
				theta = (float)(Math.atan((float) (yPosition2 - yPosition1)
						/ (float) (xPosition1 - xPosition2)));
				theta = (float)Math.toDegrees(theta);
			} catch (Exception e) {
				theta = 0;
			}
			theta += 180;
		}
		// quadrant 4
		else if (xPosition2 >= xPosition1 && yPosition2 >= yPosition1) {
			try {
				theta = (float)(Math.atan((float) (yPosition2 - yPosition1)
						/ (float) (xPosition2 - xPosition1)));
				theta = (float)Math.toDegrees(theta);
			} catch (Exception e) {
				theta = 0;
			}
			theta = (90 - theta) + 270;
		}
		theta = 360 -theta;
		return theta;
	}
    
	public static float calculateAngle(Point P2, Point P1) {
		int xPosition2 = (int)P2.xComponent;
		int yPosition2 = (int)P2.yComponent;
		
		int xPosition1 = (int)P1.xComponent;
		int yPosition1 = (int)P1.yComponent;
		
		return calculateAngle(xPosition2, yPosition2, xPosition1, yPosition1);
	}

	public static double angleSpeed(double theta, double velocity, char xy) {
		double speed = 0;
		if (xy == 'x') {
			speed = Math.cos(Math.toRadians(theta)) * velocity;
		} else if (xy == 'y') {
			speed = Math.sin(Math.toRadians(theta)) * velocity;
		}
		return speed;
	}
	
	public static int generateRandomInt(int low, int high) {
		Random rand = new Random();
        int r = 0;

	        if (low > high) {
	            r = 0;
	        } else if (low > 0 && high > 0) {
	            do {
	                r = rand.nextInt(high);
	            } while (r < low);
	        } else if (low < 0 && high < 0) {
	            do {
	                r = rand.nextInt(-low);
	            } while (r < -high);
	            r = -r;
	        } else if (low < 0 && high > 0) {
	            if (rand.nextInt(1000) > 500) {
	                r = rand.nextInt(high);
	            } else {
	                r = rand.nextInt(-low);
	                r = -r;
	            }
	        } else {
	            if (high > 0) {
	                r = rand.nextInt(high);
	            } else if (low < 0) {
	                r = rand.nextInt(-low);
	                r = -r;
	            } else {
	                r = rand.nextInt(high);
	            }
	        }
	        /*if (r == 0) {
	        	if (low < 0) {
	        		if (Math.random() <.5) {
	        			r -=1;
	        		} else {
	        			r+=1;
	        		}
	        	} else {
	        		r +=1;
	        	}
	        }*/
 
        return r;
    }
    
	public static double[] sort(double[] numList, int small, int big) {
		double[] list = numList;
		int pivot = small;
		int i = small;
		int j = big;
		int temp = 0;
		int temp2 = 0;
		double temp3 = 0;
		/*
		 * int loopNum = 0; System.out.println(" ");
		 * System.out.println("First Element:" + small);
		 * System.out.println("Last Element:" + big); System.out.println(" ");
		 * System.out.println("Begin Loop");
		 */
		while (true) {
			/*
			 * loopNum++; System.out.println("LOOP: " + loopNum);
			 * System.out.println("pivot:" + pivot + ", " +
			 * list.elementAt(pivot)); try { System.out.println("i:" + i + ", "
			 * + list.elementAt(i)); System.out.println("j:" + j + ", " +
			 * list.elementAt(j)); } catch (Exception e) {
			 * System.out.println("ERROR: " + e.getMessage()); }
			 */
			if (i > j)
				break;
			if (list[i] >= list[pivot]) {
				temp = i;
			} else {
				i += 1;
			}
			if (list[j] <= list[pivot]) {
				temp2 = j;
			} else {
				j -= 1;
			}
			if (temp == i && temp2 == j) {
				// System.out.println("Before: " + list);
				temp3 = list[temp];
				list[temp] = list[temp2];
				list[temp2] = temp3;
				
				i += 1;
				j -= 1;
				// System.out.println("Switched " + list.elementAt(temp)
				// + " with " + list.elementAt(temp2));
				// System.out.println("After: " + list);
			}
			temp = 0;
			temp2 = -1;
			temp3 = -1;
			// System.out.println("------");
		}
		// System.out.println("End loop");
		// System.out.println(" ");
		if (small < j) {
			list = sort(list, small, j);
		}
		if (i <= big) {
			list = sort(list, i, big);
		}
		return list;
	}
    
	public static int[] sort(int[] numList, int small, int big) {
    	int[] list = numList;
		int pivot = small;
		int i = small;
		int j = big;
		int temp = 0;
		int temp2 = 0;
		int temp3 = 0;
		/*
		 * int loopNum = 0; System.out.println(" ");
		 * System.out.println("First Element:" + small);
		 * System.out.println("Last Element:" + big); System.out.println(" ");
		 * System.out.println("Begin Loop");
		 */
		while (true) {
			/*
			 * loopNum++; System.out.println("LOOP: " + loopNum);
			 * System.out.println("pivot:" + pivot + ", " +
			 * list.elementAt(pivot)); try { System.out.println("i:" + i + ", "
			 * + list.elementAt(i)); System.out.println("j:" + j + ", " +
			 * list.elementAt(j)); } catch (Exception e) {
			 * System.out.println("ERROR: " + e.getMessage()); }
			 */
			if (i > j)
				break;
			if (list[i] >= list[pivot]) {
				temp = i;
			} else {
				i += 1;
			}
			if (list[j] <= list[pivot]) {
				temp2 = j;
			} else {
				j -= 1;
			}
			if (temp == i && temp2 == j) {
				// System.out.println("Before: " + list);
				temp3 = list[temp];
				list[temp] = list[temp2];
				list[temp2] = temp3;
				
				i += 1;
				j -= 1;
				// System.out.println("Switched " + list.elementAt(temp)
				// + " with " + list.elementAt(temp2));
				// System.out.println("After: " + list);
			}
			temp = 0;
			temp2 = -1;
			temp3 = -1;
			// System.out.println("------");
		}
		// System.out.println("End loop");
		// System.out.println(" ");
		if (small < j) {
			list = sort(list, small, j);
		}
		if (i <= big) {
			list = sort(list, i, big);
		}
		return list;
	}
    
	public Point[] sort(Point[] pointList, Point centre, int small, int big) {
    	Point[] P = pointList;
		int pivot = small;
		int i = small;
		int j = big;
		int temp = 0;
		int temp2 = 0;
		Point temp3;
		/*
		 * int loopNum = 0; System.out.println(" ");
		 * System.out.println("First Element:" + small);
		 * System.out.println("Last Element:" + big); System.out.println(" ");
		 * System.out.println("Begin Loop");
		 */
		while (true) {
			/*
			 * loopNum++; System.out.println("LOOP: " + loopNum);
			 * System.out.println("pivot:" + pivot + ", " +
			 * list.elementAt(pivot)); try { System.out.println("i:" + i + ", "
			 * + list.elementAt(i)); System.out.println("j:" + j + ", " +
			 * list.elementAt(j)); } catch (Exception e) {
			 * System.out.println("ERROR: " + e.getMessage()); }
			 */
			if (i > j)
				break;
			if (Physics.calculateAngle(P[i],centre) >= Physics.calculateAngle(P[pivot],centre)) {
				temp = i;
			} else {
				i += 1;
			}
			if (Physics.calculateAngle(P[j],centre) <= Physics.calculateAngle(P[pivot],centre)) {
				temp2 = j;
			} else {
				j -= 1;
			}
			if (temp == i && temp2 == j) {
				// System.out.println("Before: " + list);
				temp3 = P[temp];
				P[temp] = P[temp2];
				P[temp2] = temp3;
				
				i += 1;
				j -= 1;
				// System.out.println("Switched " + list.elementAt(temp)
				// + " with " + list.elementAt(temp2));
				// System.out.println("After: " + list);
			}
			temp = 0;
			temp2 = -1;
			//temp3 = -1;
			// System.out.println("------");
		}
		// System.out.println("End loop");
		// System.out.println(" ");
		if (small < j) {
			P = sort(P,centre, small, j);
		}
		if (i <= big) {
			P = sort(P,centre, i, big);
		}
		return P;
	}
}

