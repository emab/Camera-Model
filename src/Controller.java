import java.util.ArrayList;
import java.util.List;

public class Controller {
	
	public static void main(String[] args) {
		
		List<Object> objects = new ArrayList<Object>();
		
		// Camera gives us its raw data

		Camera c = new Camera(1,1,25,90);

		objects = c.getProcessedObjects();
		
		int count = 0;
		
		double[] p1 = {0,0,0};
		double[] p2 = {0,0,0};
		double[] p3 = {0,0,0};
		double[] len = {0,0,0};
		
		// Iterate through object data from camera

		for (Object o : objects) {
			if (count < 3) {
				len[count] = getDistance(o.getRadius(), o.getWidthPx());
				
				if (count == 0) {
					p1[0] = o.getAdjX();
					p1[1] = o.getAdjY();
					p1[2] = 0;
					calcAngle(o, c);
				}
				if (count == 1) {
					p2[0] = o.getAdjX();
					p2[1] = o.getAdjY();
					p2[2] = 0;
				}
				if (count == 2) {
					p3[0] = o.getAdjX();
					p3[1] = o.getAdjY();
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
		
	public static double getDistance(double objR, double pixelWidth) {
		double dist = objR / Math.tan(Math.toRadians((75*pixelWidth)/320)/2);
		return dist;
	}
	
	public static void calcAngle(Object o, Camera c) {
		// calculate known angle to an object if facing north
		
//		System.out.println(o.getX());
//		System.out.println(o.getY());
//		System.out.println(o.getAdjX());
//		System.out.println(o.getAdjY());
		
		//double angle = Math.toDegrees(Math.atan2(o.getX(), o.getY()));
		
		
		
		//System.out.println(angle(o.getX(), o.getY()));
		// calculate observed angle
		//double observedAngle = Math.toDegrees(Math.atan((o.getAdjX() / o.getAdjY())));
		//System.out.println(angle(o.getAdjX(), o.getAdjY()));
		
		System.out.println("North calculated rotation: "+angle(o.getX(), o.getY()));
		System.out.println("Recalculated rotation: "+angle(o.getAdjX(), o.getAdjY()));
		
		double calcRotation = angle(o.getX(), o.getY()) - angle(o.getAdjX(), o.getAdjY());
		System.out.println("Calculated rotation: "+Math.abs(calcRotation)%360);
	}
	
	public static double angle(double x, double y) {
		if (x > 0 && y > 0) {
			return 360 - Math.toDegrees(Math.atan(x/y));
		}
		if (x > 0 && y < 0) {
			return 180 - Math.toDegrees(Math.atan(x/y));
		}
		if (x < 0 && y > 0) {
			return Math.abs(Math.toDegrees(Math.atan(x/y)));
		}
		if (x < 0 && y < 0) {
			return 180 - Math.toDegrees(Math.atan(x/y));
		}
		System.out.println("here");
		return 0;
	}
}
