
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
	final private long xResolution = 640;
	final private long yResolution = 400;
	
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
		
		// need to work out max and min values for potential objects at a given height camZ
		double xMax = camZ * Math.tan(Math.toRadians(xFOV/2));
		double yMax = camZ * Math.tan(Math.toRadians(yFOV/2));
		
		// this works out the coordinate of the bottom left corner of the camera viewpoint
		double pixelOriginX = -xMax;
		double pixelOriginY = -yMax;
		
		// this works out the distance a pixel covers in a given direction
		double avgXPixelVal = xResolution/(2*xMax);
		double avgYPixelVal = yResolution/(2*yMax);
		
		for (String key : objects.keySet()) {
			
			// get the object details
			double objX = Double.parseDouble(objects.get(key)[0]);
			double objY = Double.parseDouble(objects.get(key)[1]);
			double objR = Double.parseDouble(objects.get(key)[2]);
			
			double adjX=0;
			double adjY=0;
			
			// this is used to translate the objects position - translates so that the origin is the camera centre point
			if ((camX > 0 && camY > 0) || (camX > 0 && camY < 0) || (camX > 0 && camY == 0) || (camX == 0 && camY > 0)) {
				adjX = objX - camX;
				adjY = objY - camY;
			} else if ((camX < 0 && camY < 0) || (camX == 0 && camY < 0)){
				adjX = objX - camX;
				adjY = objY + camY;
			} else if (camX < 0 && camY >= 0) {
				adjX = objX + camX;
				adjY = objY + camY;
			} else if (camX == 0 && camY == 0) {
				adjX = objX;
				adjY = objY;
			}
			
			// we then also need to rotate the objects coordinates to represent what the camera would see. This means they rotate in the opposite direction
			double rotatedAdjX = adjX * Math.cos(Math.toRadians(-camTheta)) - adjY * Math.sin(Math.toRadians(-camTheta));
			double rotatedAdjY = adjX * Math.sin(Math.toRadians(-camTheta)) + adjY * Math.cos(Math.toRadians(-camTheta));
			
			
			// this checks to see if the object would be in view of the camera in its rotated position
			if (xMax > Math.abs(rotatedAdjX) && yMax > Math.abs(rotatedAdjY)) {
				System.out.println("Can see object: " + key);
				
				// distance doesn't need rotated coordinates as that distance wouldn't change from camera
				double distance = Math.sqrt( Math.pow((camX - objX), 2) + Math.pow((camY - objY), 2) 
				+ Math.pow((camZ), 2));
				// calculates the arc the object would take up in the view of camera (assuming it was a sphere)
				double objArc = 2 * Math.toDegrees((Math.atan( objR / distance )));	
				// calculates the width in pixels of the object
				double pixelWidth = (objArc/xFOV) * xResolution;
				// converts the coordinate of object to be relative to the bottom corner of the camera view
				long xCentre = Math.round((rotatedAdjX - pixelOriginX) * avgXPixelVal);
				long yCentre = Math.round((rotatedAdjY - pixelOriginY) * avgYPixelVal);
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
	
	public double getxFOV() {
		return xFOV;
	}

	public double getyFOV() {
		return yFOV;
	}

	public long getxResolution() {
		return xResolution;
	}

	public long getyResolution() {
		return yResolution;
	}

	public List<Object> getProcessedObjects() {
		return processedObjects;
	}
}
