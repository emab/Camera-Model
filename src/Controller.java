import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
	
	public static void main(String[] args) {
		
		List<Object> objects = new ArrayList<Object>();
		
		// Camera gives us its raw data

		Camera c = new Camera(10,1,20);

		objects = c.getProcessedObjects();
		
		int count = 0;
		
		double[] p1 = {0,0,0};
		double[] p2 = {0,0,0};
		double[] p3 = {0,0,0};
		double[] len = {0,0,0};
		
		// Iterate through object data from camera

		for (Object o : objects) {
			if (count < 3) {
				len[count] = o.getDistance();
				
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
		
		trilaterate(p1,p2,p3,len);
		
	}
	
	// Inspiration from https://www.experts-exchange.com/questions/21253179/triangulation.html
		public static void trilaterate(double[] P1, double[] P2, double[] P3, double[] L)
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
            if (L1*L1 - X*X < 0){System.out.println("no solution");}
            double XB = (LB3*LB3 - LB2*LB2 + LB1*LB1 )/(2*LB1 );
            if (LB3*LB3 - XB* XB < 0 ){System.out.println("no solution");}
            double CB=  Math.sqrt(LB3*LB3 - XB* XB );
            if (C1*C1+(XB - X)*(XB - X)< 0){System.out.println("no solution");}
            double D1 = Math.sqrt(C1*C1+(XB - X)*(XB - X));
            double Y = (D1*D1 - L3*L3  + CB*CB  )/(2*CB );
            if (C1*C1 - Y*Y < 0){System.out.println("no solution");}
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

            System.out.println(ansX + " " + ansY + " " + ansZ);
	    }
	
	// This method calculates the distance from the centre of the object to the camera 
	public static double calcDistance(double angle, double centreDistance) {
		double distance = centreDistance / Math.sin(Math.toRadians(angle));
		return distance;
	}
	
	// This method calculates the angle between the centre of the object and the camera
	public static double calcAngle(double centreDistance, double height) {
		double angle = Math.toDegrees(Math.atan(centreDistance/height));
		return angle;
	}
	
	// This method calculates the height of the camera relative to the object
	public static double calcHeight(double distancePerPx) {
		double result = (640 * distancePerPx) / Math.tan(Math.toRadians(75/2));
		return Math.abs(result);
	}
	
	// This method calculates the distance from the centre of the object to the centre point of the
	// cameras image
	public static double calcCenterDistance(int leftX, int leftY, int rightX, int rightY) {
		int camCenterX = 640;
		int camCenterY = 400;
		
		int objCenterX = (leftX + rightX) / 2;
		int objCenterY = (leftY + rightY) / 2;
		
		System.out.println("Centre coord X: "+objCenterX + "  Centre coord Y: "+ objCenterY);
		
		return Math.sqrt( Math.pow((camCenterX - objCenterX), 2) + Math.pow((camCenterY - objCenterY), 2) );
	}
	
	// Another distance method - didn't seem to work
	public static double distanceToObject(double realHeightCm, double imageHeightPx, double objectHeightPx,
			double focalLengthCm, double sensorHeightCm) {
		double distance = (focalLengthCm * realHeightCm * imageHeightPx) / (objectHeightPx * sensorHeightCm); 
		return distance;
	}
}
