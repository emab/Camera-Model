
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

				processedObjects.add(new Object(key, objX, objY, objR, distance, pixelWidth, xCentre, yCentre));
				
			} else {
				System.out.println("Cannot see object: " + key);
			}
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
