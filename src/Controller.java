import java.util.ArrayList;
import java.util.List;

public class Controller {
	
	public static void main(String[] args) {
		
		List<Object> objects = new ArrayList<Object>();
		
		
//		for (int i=0; i<5; i++) {
//			for (int j=0; j<5; j++) {
//				for (int angle=0; angle <= 30; angle = angle + 10) {
//					System.out.println("X: "+i+" Y: "+j+" Angle: "+angle);
//					Camera c = new Camera(i,j,15,angle);
//					
//					objects = c.getProcessedObjects();
//					
//					int count = 0;
//					
//					double[] p1 = {0,0,0};
//					double[] p2 = {0,0,0};
//					double[] p3 = {0,0,0};
//					double[] len = {0,0,0};
//					
//					// Iterate through object data from camera
//
//					for (Object o : objects) {
//						if (count < 3) {
//							len[count] = getDistance(o.getRadius(), o.getWidthPx(),c);
//							
//							if (count == 0) {
//								p1[0] = o.getX();
//								p1[1] = o.getY();
//								p1[2] = 0;
//								
//							}
//							if (count == 1) {
//								p2[0] = o.getX();
//								p2[1] = o.getY();
//								p2[2] = 0;
//								
//							}
//							if (count == 2) {
//								p3[0] = o.getX();
//								p3[1] = o.getY();
//								p3[2] = 0;
//
//							}
//						}
//						count++;
//					}
//					
//					long[] coords = trilaterate(p1,p2,p3,len);
//					
//					calcAngle(objects.get(0), objects.get(1), objects.get(2), c, coords);
//					System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
//				}
//			}
//		}
		
		
		//Camera gives us its raw data
		
		Camera c = new Camera(4,4,15,10);

		objects = c.getProcessedObjects();
		
		int count = 0;
		
		double[] p1 = {0,0,0};
		double[] p2 = {0,0,0};
		double[] p3 = {0,0,0};
		double[] len = {0,0,0};
		
		// Iterate through object data from camera

		for (Object o : objects) {
			if (count < 3) {
				len[count] = getDistance(o.getRadius(), o.getWidthPx(),c);
				
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
		
		long[] coords = trilaterate(p1,p2,p3,len);
		
		calcAngle(objects.get(0), objects.get(1), objects.get(2), c, coords);
		
	}
	
	// Inspiration from https://www.experts-exchange.com/questions/21253179/triangulation.html
	public static long[] trilaterate(double[] P1, double[] P2, double[] P3, double[] L)
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
        double z = (z1 + X * Xz + Y * Yz + Z * Zz);

        long ansX = Math.round(x);
        long ansY = Math.round(y);
        long ansZ = Math.abs(Math.round(z));
        
        long[] coords = {ansX,ansY,ansZ};

        System.out.println("Estimated camera coordinates: "+ansX + " " + ansY + " " + ansZ);
        
        return coords;
    }
		
	public static double getDistance(double objR, double pixelWidth, Camera c) {
		double dist = objR / Math.tan(Math.toRadians((c.getxFOV()*pixelWidth)/c.getxResolution())/2);
		return dist;
	}
	
	public static void calcAngle(Object o1, Object o2, Object o3, Camera c, long[] coords) {
		long resX = c.getxResolution();
		long resY = c.getyResolution();
		double xFOV = c.getxFOV();
		double yFOV = c.getyFOV();
		double xCam = coords[0];
		double yCam = coords[1];
		double zCam = coords[2];
		
		double xMax = zCam * Math.tan(Math.toRadians(xFOV/2));
		double yMax = zCam * Math.tan(Math.toRadians(yFOV/2));
		
		double pixelOriginX = -xMax;
		double pixelOriginY = -yMax;
		
		double avgXPixelVal = resX/(2*xMax);
		double avgYPixelVal = resY/(2*yMax);
		
		double obj1[] = translate(xCam, yCam, o1.getX(), o1.getY());
		double obj2[] = translate(xCam, yCam, o2.getX(), o2.getY());
		double obj3[] = translate(xCam, yCam, o3.getX(), o3.getY());
		
		System.out.println("translated not rotated: "+obj1[0]+" "+ obj1[1]);
		
		long obj1x = Math.round((obj1[0] - pixelOriginX) * avgXPixelVal);
		long obj1y = Math.round((obj1[1] - pixelOriginY) * avgYPixelVal);
		
		System.out.println("x and y to pixels: "+obj1x+ " "+obj1y);
		System.out.println("x and y rotated pixels: "+o1.getCenterX()+" "+o1.getCenterY());
		
		long obj2x = Math.round((obj2[0] - pixelOriginX) * avgXPixelVal);
		long obj2y = Math.round((obj2[1] - pixelOriginY) * avgYPixelVal);
		long obj3x = Math.round((obj3[0] - pixelOriginX) * avgXPixelVal);
		long obj3y = Math.round((obj3[1] - pixelOriginY) * avgYPixelVal);
		
		
		
		double calcangle1 = Math.abs(angle(Math.abs(o1.getCenterX()-resX/2),Math.abs(o1.getCenterY()-resY/2))) - 
				Math.abs(angle(Math.abs(obj1x-resX/2),Math.abs(obj1y - resY/2)));
		System.out.println("Calculated angle 1: "+calcangle1);
		
		double calcangle2 = Math.abs(angle(Math.abs(o2.getCenterX()-resX/2),Math.abs(o2.getCenterY()-resY/2))) - 
				Math.abs(angle(Math.abs(obj2x-resX/2),Math.abs(obj2y - resY/2)));
		System.out.println("Calculated angle2: "+calcangle2);
		
		double calcangle3 = Math.abs(angle(Math.abs(o3.getCenterX()-resX/2),Math.abs(o3.getCenterY()-resY/2))) - 
				Math.abs(angle(Math.abs(obj3x-resX/2),Math.abs(obj3y - resY/2)));
		System.out.println("Calculated angle3: "+calcangle3);
		
		System.out.println("______________");
		System.out.println("Estimated angle: "+(Math.abs(calcangle1) + Math.abs(calcangle2) + Math.abs(calcangle3))/3 );
	}
	
	public static double[] translate(double xCam, double yCam, double x, double y) {
		double adjX = 0;
		double adjY = 0;
		if ((xCam > 0 && yCam > 0) || (xCam > 0 && yCam < 0) || (xCam > 0 && yCam == 0) || (xCam == 0 && yCam > 0)) {
			adjX = x - xCam;
			adjY = y - yCam;
		} else if ((xCam < 0 && yCam < 0) || (xCam == 0 && yCam < 0)){
			adjX = x - xCam;
			adjY = y + yCam;
		} else if (xCam == 0 && yCam == 0) {
			adjX = x;
			adjY = y;
		}
		double[] translated = {adjX,adjY};
		return translated;
	}
	
	public static double angle2(double x, double y) {
		return Math.toDegrees(Math.atan(x/y));
	}
	
	public static double angle(double x, double y) {
		if (x > 160 && y > 100) {
			return 360 - Math.toDegrees(Math.atan(x/y));
		}
		else if (x > 160 && y < 100) {
			return 180 - Math.toDegrees(Math.atan(x/y));
		}
		else if (x < 160 && y > 100) {
			return Math.abs(Math.toDegrees(Math.atan(x/y)));
		}
		else if (x < 160 && y < 100) {
			return 180 - Math.toDegrees(Math.atan(x/y));
		}
		else if (x == 160 && y > 100) {
			return 0;
		}
		else if (x == 160 && y < 100) {
			return 180;
		}
		else if (y == 160 && x > 100) {
			return 270;
		}
		else if (y == 160 && x < 100) {
			return 90;
		}
		else {
			System.out.println("something broke");
			return 0;
		}
	}
}
