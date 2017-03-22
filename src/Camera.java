
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	// These are the cameras true coordinates which the model will know
	private double camX;
	private double camY;
	private double camZ;
	
	private double xFOV = 75;
	private double yFOX = 47;
	
	private List<Object> processedObjects = new ArrayList<Object>();
	
	// Hashmap to store objects that the data that the camera sees. Contains the id and the coordinates
	private HashMap<String,String[]> objects = new HashMap<String,String[]>();

	private String fileDest = "res/objects.csv";
	
	public Camera(double camX, double camY, double camZ) {
			this.camX = camX;
			this.camY = camY;
			this.camZ = camZ;
			
			getData();
		}
	
	// Uses the ImportCSV class to import data from CSV file. In the real model this would just be the
	// inputstream of the camera converted into usable data
	private void getData() {
		ImportCSV csv = new ImportCSV(fileDest);
		
		for (String key : csv.getData().keySet()) {
			String[] data = (csv.getData().get(key)).split(",");
			objects.put(key, data);
		}
		
		// need to work out max and min values for potential objects at a given height X
		double xMax = camZ * Math.tan(Math.toRadians(75/2));
		double yMax = camZ * Math.tan(Math.toRadians(47/2));
		
		for (String key : objects.keySet()) {
			
			double objX = Double.parseDouble(objects.get(key)[0]);
			double objY = Double.parseDouble(objects.get(key)[1]);
			double objR = Double.parseDouble(objects.get(key)[2]);
			
			if (xMax > Math.abs(objX) && yMax > Math.abs(objY)) {
				System.out.println("Can see object: " + key);
				

				double distance = Math.sqrt( Math.pow((camX - objX), 2) + Math.pow((camY - objY), 2) 
				+ Math.pow((camZ), 2));
				double objArc = 2 * Math.toDegrees((Math.atan( objR / distance )));
				double pixelWidth = (objArc/75) * 320;
				double xCentre = objX - camX;
				double yCentre = objY - camY;
				
				System.out.println(key);
				System.out.println(objX);
				System.out.println(objY);
				System.out.println(distance);

				
				
				processedObjects.add(new Object(key, objX, objY, objR, distance, pixelWidth, xCentre, yCentre));
				
			} else {
				System.out.println("Cannot see object: " + key);
			}
		}
		
		double a[] = {2,5,0};
		double b[] = {1,1,0};
		double cd[] = {-3,-1,0};
		double d[] = {0,0,0};
		int i=0;
		
		for (Object o : processedObjects) {
			d[i] = o.getDistance();
			i++;
		}
		
		trilaterate2(a, b, cd, d);
	}
	
	public static void trilaterate2(double[] P1, double[] P2, double[] P3, double[] L)
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
                    //document.write(' Zx='+Zx.toFixed(5)+' Zy='+Zy.toFixed(5)+' Zz='+Zz.toFixed(5)+'<br>')

                    double x = (x1 + X * Xx + Y * Yx + Z * Zx);
                    double y = (y1 + X * Xy + Y * Yy + Z * Zy);
                    double z = (z1 + X * Xz + Y * Yz + Z * Zz);

                    long ansX = Math.round((x1 + X * Xx + Y * Yx - Z * Zx));
                    long ansY = Math.round((y1 + X * Xy + Y * Yy - Z * Zy));
                    long ansZ = Math.round((z1 + X * Xz + Y * Yz - Z * Zz));

                    System.out.println(ansX + " " + ansY + " " + ansZ);
                    
    }
	
	// getters
	public double getCamX() {
		return camX;
	}

	public double getCamY() {
		return camY;
	}
	
	public double getCamZ() {
		return camZ;
	}
	
	public List<Object> getProcessedObjects() {
		return processedObjects;
	}
}
