import java.util.ArrayList;
import java.util.List;

public class Controller {
	
	public static void main(String[] args) {
		
		List<Object> objects = new ArrayList<Object>();	
		
		//Initialise camera
<<<<<<< HEAD
		double camX= 0;
		double camY= 0;
		double camZ= 17;
		double camTheta = 70;
=======

<<<<<<< HEAD
		double camX= -1;
		double camY= 1.5;
		double camZ= 5;
		double camTheta = 300;
=======
		double camX= 5;
		double camY= 5;
		double camZ= 10;
		double camTheta = 25;
>>>>>>> origin/master
>>>>>>> origin/master
		

		
		System.out.println("          Starting with camera location X:"+camX+" Y:"+camY+" Z:"+camZ);
		System.out.println("                 Angle of direction: "+camTheta);
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		
		Camera c = new Camera(camX,camY,camZ,camTheta);

		// Camera processes the objects it can see
		objects = c.getProcessedObjects();
		
		// Check we have enough objects in order to do calculations on location
		if (objects.size() < 3) {
			System.out.println("Cannot see enough objects to find location...");
		} else {
			int count = 0;
			
			// Prepare variables to hold coordinates for each object.
			double[] p1 = {0,0,0};
			double[] p2 = {0,0,0};
			double[] p3 = {0,0,0};
			double[] len = {0,0,0};
			
			// Iterate through object data from camera

			for (Object o : objects) {
				if (count < 3) {
					//Distance to each object is calculated
					len[count] = getDistance(o.getRadius(), o.getWidthPx(),c);
					
					// Populate the arrays holding coordinate information. Assuming that the Z value for all objects is 0 
					if (count == 0) {
						p1[0] = o.getX();
						p1[1] = o.getY();
						p1[2] = 0;
						
					}
					if (count == 1) {
						p2[0] = o.getX();
						p2[1] = o.getY();
						p2[2] = 0;
						
					}
					if (count == 2) {
						p3[0] = o.getX();
						p3[1] = o.getY();
						p3[2] = 0;

					}
				}
				count++;
			}
			
			//Using the coords and the distances to each, we can calculate our current camera position
			double[] coords = trilaterate(p1,p2,p3,len);
			
			//Using our current camera position and information from the camera, we can work out our angle from north
			calcAngle(objects.get(0), objects.get(1), objects.get(2), c, coords);
		}
		
	}
	
	// Inspiration from https://www.experts-exchange.com/questions/21253179/triangulation.html
	public static double[] trilaterate(double[] P1, double[] P2, double[] P3, double[] L)
    {
		//define station points and distances
        double x1 = P1[0];
        double y1 = P1[1];
        double z1 = P1[2];

        double x2 = P2[0];
        double y2 = P2[1];
        double z2 = P2[2];

        double x3 = P3[0];
        double y3 = P3[1];
        double z3 = P3[2];

        double L1 = L[0];
        double L2 = L[1];
        double L3 = L[2];
        
        //caluculate coords in plane of stations
        double LB1 = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
        double LB2 = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2) + (z3 - z2) * (z3 - z2));
        double LB3 = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3) + (z1 - z3) * (z1 - z3));

        double X = (L1*L1  - L2*L2  + LB1*LB1)/(2*LB1 );
        double C1 = Math.sqrt (L1*L1 - X*X);
        if (L1*L1 - X*X < 0){System.out.println("no solution1");}
        double XB = (LB3*LB3 - LB2*LB2 + LB1*LB1 )/(2*LB1 );
        if (LB3*LB3 - XB* XB < 0 ){System.out.println("no solution2");}
        double CB=  Math.sqrt(LB3*LB3 - XB* XB );
        if (C1*C1+(XB - X)*(XB - X)< 0){System.out.println("no solution3");}
        double D1 = Math.sqrt(C1*C1+(XB - X)*(XB - X));
        double Y = (D1*D1 - L3*L3  + CB*CB  )/(2*CB );
        if (C1*C1 - Y*Y < 0){System.out.println("no solution4");}
        double Z = Math.sqrt(C1 * C1 - Y * Y);
        
        
       	//Now transform X,Y,Z to x,y,z
        //Find the unit vectors in X,Y,Z
        double Xx = (x2-x1);
        double Xy = (y2-y1);
        double Xz = (z2-z1);
        double Xl = Math.sqrt(Xx*Xx+Xy*Xy+Xz*Xz);
        Xx = Xx / Xl;
        Xy = Xy / Xl;
        Xz = Xz / Xl;


        double t =- ((x1-x3)*(x2-x1)+(y1-y3)*(y2-y1)+(z1-z3)*(z2-z1))/(LB1*LB1);
        double Yx = (x1+(x2-x1)*t-x3);
        double Yy = (y1+(y2-y1)*t-y3);
        double Yz = (z1+(z2-z1)*t-z3);
        double Yl = Math.sqrt(Yx*Yx+Yy*Yy+Yz*Yz);
        Yx =- (Yx/Yl);
        Yy =- (Yy/Yl);
        Yz =- (Yz/Yl);

        double Zx = (Xy * Yz - Xz * Yy);
        double Zy = (Xz * Yx - Xx * Yz);
        double Zz = (Xx * Yy - Xy * Yx);

        double x = (x1 + X * Xx + Y * Yx + Z * Zx);
        double y = (y1 + X * Xy + Y * Yy + Z * Zy);
        double z = Math.abs(z1 + X * Xz + Y * Yz + Z * Zz);
        
        double[] coords = {x,y,z};
        
        System.out.println("Estimated camera coordinates: "+ x + " " + y + " " + z);
        
        return coords;
    }
	
	// This method calculates distance by assuming object is a sphere, so can use trigonometry to calculate the distance
	public static double getDistance(double objR, double pixelWidth, Camera c) {
		/* The angle to the object is calculated by taking the FOV of the camera and using a ratio of the object width in pixels
		 * to the total image size in pixels to work out the objects arc. The arc/2 is therefore the angle needed to continue with
		 * the distance calculation.
		 */
		double dist = objR / Math.tan(Math.toRadians((c.getxFOV()*pixelWidth)/c.getxResolution())/2);
		return dist;
	}
	
	/* This method calculates the angle the camera has been rotated by. This is done by comparing the
	 * angles to each objects before and after their rotation. We know where objects should appear in the cameras
	 * image, and also know where they have actually appeared. By taking an average we can get rid of some errors that
	 * occur from estimating the objects central point on the screen.
	 */
	public static void calcAngle(Object o1, Object o2, Object o3, Camera c, double[] coords) {
		
		System.out.println("using objects: " + o1.getId() +o2.getId() +o3.getId());
		
		
		/*This is all information about the camera. In a real model this may have to be predetermined
		 * but in this model it can take a camera of any resolution and FOV
		 */
		long resX = c.getxResolution();
		long resY = c.getyResolution();
		double xFOV = c.getxFOV();
		double yFOV = c.getyFOV();
		double xCam = coords[0];
		double yCam = coords[1];
		double zCam = coords[2];
		
		// Below is very similar to how pixel coordinates are calculated in the Camera class
		double xMax = zCam * Math.tan(Math.toRadians(xFOV/2));
		double yMax = zCam * Math.tan(Math.toRadians(yFOV/2));

		
		double pixelOriginX = -xMax;
		double pixelOriginY = -yMax;
		
		double avgXPixelVal = resX/(2*xMax);
		double avgYPixelVal = resY/(2*yMax);
		
		double obj1[] = translate(xCam, yCam, o1.getX(), o1.getY());
		double obj2[] = translate(xCam, yCam, o2.getX(), o2.getY());
		double obj3[] = translate(xCam, yCam, o3.getX(), o3.getY());
		
		long obj1x = Math.round((obj1[0] - pixelOriginX) * avgXPixelVal);
		long obj1y = Math.round((obj1[1] - pixelOriginY) * avgYPixelVal);
		long obj2x = Math.round((obj2[0] - pixelOriginX) * avgXPixelVal);
		long obj2y = Math.round((obj2[1] - pixelOriginY) * avgYPixelVal);
		long obj3x = Math.round((obj3[0] - pixelOriginX) * avgXPixelVal);
		long obj3y = Math.round((obj3[1] - pixelOriginY) * avgYPixelVal);
		
		
		double calcangle1 = angleChange(obj1x,obj1y,o1.getCenterX(),o1.getCenterY(),resX/2, resY/2);
		double calcangle2 = angleChange(obj2x,obj2y,o2.getCenterX(),o2.getCenterY(),resX/2, resY/2);
		double calcangle3 = angleChange(obj3x,obj3y,o3.getCenterX(),o3.getCenterY(),resX/2, resY/2);
		
<<<<<<< HEAD
		System.out.printf("obj translated: %s %s obj pixel non rotated: %s %s rotated: %s %s calculated angle: %s\n",obj1[0],obj1[1],obj1x,obj1y,o1.getCenterX(),o1.getCenterY(),calcangle1);
		System.out.printf("obj translated: %s %s obj pixel non rotated: %s %s rotated: %s %s calculated angle: %s\n",obj2[0],obj2[1],obj2x,obj2y,o2.getCenterX(),o2.getCenterY(),calcangle2);
		System.out.printf("obj translated: %s %s obj pixel non rotated: %s %s rotated: %s %s calculated angle: %s\n",obj3[0],obj3[1],obj3x,obj3y,o3.getCenterX(),o3.getCenterY(),calcangle3);
		
=======
		System.out.printf("Calculated angles: %s    %s    %s",calcangle1,calcangle2,calcangle3);
>>>>>>> origin/master
		System.out.println("__________________________________________________________");
		System.out.println("Estimated angle: "+((calcangle1) + (calcangle2) + (calcangle3))/3 );
	}
	
	// Method same as Camera class, used to translate points
	public static double[] translate(double xCam, double yCam, double x, double y) {
		double adjX = 0;
		double adjY = 0;
		
		if ((xCam > 0 && yCam > 0) || (xCam > 0 && yCam < 0) || (xCam > 0 && yCam == 0) || (xCam == 0 && yCam > 0)) {
			adjX = x - xCam;
			adjY = y - yCam;
		} else if ((xCam < 0 && yCam < 0) || (xCam == 0 && yCam < 0)){
			adjX = x - xCam;
			adjY = y + yCam;
		} else if (xCam < 0 && yCam >= 0) {
			adjX = x + xCam;
			adjY = y + yCam;
		} else if (xCam == 0 && yCam == 0) {
			adjX = x;
			adjY = y;
		}
		double[] translated = {adjX,adjY};
		return translated;
	}
	
	// This method calculates the change in angle between two given points
	static double angleChange(double initial_x, double initial_y, double new_x, double new_y, double camX, double camY) {
		// First calculate the angle to the initial point going counter clockwise (matching camera)
		double initial_angle = angle(initial_x, initial_y,camX,camY);
		//Then we calculate the angle to the other object
		double rotated_angle = angle(new_x, new_y,camX,camY);
		System.out.printf("\nINITIAL: %s   ROTATED: %s\n",initial_angle,rotated_angle);
		//If the rotated angle appears to be less than the original angle, we need to fix the calculation by adding 360
<<<<<<< HEAD
//		if (rotated_angle < intial_angle) {
//			rotated_angle = 360 + rotated_angle;
//		}
		if (rotated_angle - intial_angle > 180) {
			return rotated_angle - intial_angle - 360;
		}
		if (rotated_angle - intial_angle < -180) {
			return rotated_angle - intial_angle + 360;
		}
		return rotated_angle - intial_angle;
=======
		if (rotated_angle < initial_angle) {
			rotated_angle = 360 + rotated_angle;
		}
		return Math.abs(rotated_angle - initial_angle);
>>>>>>> origin/master
	}
	
	// Used to return an angle that is correct for points in the different quadrants of the image
	public static double angle(double x, double y, double camX, double camY) {
		if (x > camX && y > camY) {
			return Math.toDegrees(Math.atan((x-camX)/(y-camY)));
		}
		else if (x > camX && y < camY) {
			return 90 + Math.toDegrees(Math.atan((camY-y)/(x-camX)));
		}
		else if (x < camX && y > camY) {
			return 270 + Math.abs(Math.toDegrees(Math.atan((camY-y)/(camX-x))));
		}
		else if (x < camX && y < camY) {
			return 180 + Math.toDegrees(Math.atan((camX-x)/(camY-y)));
		}
		else if (x == camX && y > camY) {
			return 0;
		}
		else if (x == camX && y < camY) {
			return 180;
		}
		else if (y == camY && x > camX) {
			return 90;
		}
		else if (y == camY && x < camX) {
			return 270;
		}
		else {
			System.out.println("something broke");
			return 0;
		}
	}
}
