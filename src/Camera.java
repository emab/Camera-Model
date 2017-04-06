
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	// These are the cameras true coordinates which the model will know
	private double camX;
	private double camY;
	private double camZ;
	
	// Angle in which the camera is pointing
	private double camTheta;
	
	// Details about camera which affect some of the calculations
	final private double xFOV = 75;
	final private double yFOV = 47;
	final private double xResolution = 320;
	final private double yResolution = 200;
	
	private List<Object> processedObjects = new ArrayList<Object>();
	
	// Hashmap to store objects that the data that the camera sees. Contains the id and the coordinates
	private HashMap<String,String[]> objects = new HashMap<String,String[]>();

	private String fileDest = "res/objects.csv";
	
	public Camera(double camX, double camY, double camZ, double theta) {
			this.camX = camX;
			this.camY = camY;
			this.camZ = camZ;
			this.camTheta = theta;
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
		double xMax = camZ * Math.tan(Math.toRadians(xFOV/2));
		double yMax = camZ * Math.tan(Math.toRadians(yFOV/2));
		
		double pixelOriginX = camX - xMax;
		double pixelOriginY = camY - yMax;
		
		System.out.println("pixelOriginX: "+pixelOriginX);
		System.out.println("pixelOriginY: "+pixelOriginY);
		
		double avgXPixelVal = xResolution/(2*xMax);
		double avgYPixelVal = yResolution/(2*yMax);
		System.out.println("Avg x pixel value: "+avgXPixelVal);
		System.out.println("Avg y pixel value: "+avgYPixelVal);
		
		for (String key : objects.keySet()) {
			
			double objX = Double.parseDouble(objects.get(key)[0]);
			double objY = Double.parseDouble(objects.get(key)[1]);
			double objR = Double.parseDouble(objects.get(key)[2]);
			
			double adjX=0;
			double adjY=0;
			
			if ((camX > 0 && camY > 0) || (camX > 0 && camY < 0) || (camX > 0 && camY == 0) || (camX == 0 && camY > 0)) {
				adjX = objX - camX;
				adjY = objY - camY;
				System.out.println("here");
			} else if ((camX < 0 && camY < 0) || (camX == 0 && camY < 0)){
				System.out.println("here");
				adjX = objX - camX;
				adjY = objY + camY;
			} else if (camX == 0 && camY == 0) {
				adjX = objX;
				adjY = objY;
			}
			
			System.out.println("Original X: "+objX);
			System.out.println("Original Y: "+objY);
			
			double rotatedAdjX = adjX * Math.cos(Math.toRadians(camTheta)) - adjY * Math.sin(Math.toRadians(camTheta));
			double rotatedAdjY = adjY * Math.cos(Math.toRadians(camTheta)) + adjX * Math.sin(Math.toRadians(camTheta));
			

			System.out.println("Rotated and translated X: "+rotatedAdjX);
			System.out.println("Rotated and translated Y: "+rotatedAdjY);
			
			System.out.println("y maximum: "+yMax);
			System.out.println("x maximum: "+xMax);
			
			if (xMax > Math.abs(rotatedAdjX) && yMax > Math.abs(rotatedAdjY)) {
				System.out.println("Can see object: " + key);
				

				double distance = Math.sqrt( Math.pow((camX - adjX), 2) + Math.pow((camY - adjY), 2) 
				+ Math.pow((camZ), 2));
				double objArc = 2 * Math.toDegrees((Math.atan( objR / distance )));			
				double pixelWidth = (objArc/xFOV) * xResolution;
				
				long xCentreNonRotated = Math.round((rotatedAdjX - pixelOriginX) * avgXPixelVal);
				long yCentreNonRotated = Math.round((rotatedAdjY - pixelOriginY) * avgYPixelVal);
				
				long xCentre = Math.round(xCentreNonRotated * Math.cos(Math.toRadians(camTheta)) - yCentreNonRotated * Math.sin(Math.toRadians(camTheta)));
				long yCentre = Math.round(yCentreNonRotated * Math.cos(Math.toRadians(camTheta)) - xCentreNonRotated * Math.sin(Math.toRadians(camTheta)));
				System.out.println("xpixel: "+xCentre);
				System.out.println("ypixel: "+yCentre);
				
				processedObjects.add(new Object(key, rotatedAdjX, rotatedAdjY, objR, distance, 
						pixelWidth, xCentre, yCentre, objArc, objX, objY));
				
			} else {
				System.out.println("Cannot see object: " + key);
			}
			System.out.println("__________________________________________________________");
		}
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
