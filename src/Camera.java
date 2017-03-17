
import java.util.HashMap;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	// Focal length in cm
	private final double focalLength = 0.28;
	
	// Sensor height in cm
	private final double sensorHeight = 0.635;
	
	// Hashmap to store objects that the data that the camera sees. Contains the id and the coordinates
	private HashMap<String,String[]> objects = new HashMap<String,String[]>();

	private String fileDest;
	
	public Camera(String dataIn) {
		this.fileDest = dataIn;

		}
	
	// Uses the ImportCSV class to import data from CSV file. In the real model this would just be the
	// inputstream of the camera converted into usable data
	public void getData() {
		ImportCSV csv = new ImportCSV(fileDest);
		
		for (String key : csv.getData().keySet()) {
			String[] coordinates = (csv.getData().get(key)).split(",");
			objects.put(key, coordinates);
		}
	}
	
	// getters
	public HashMap<String,String[]> getObjects() {
		return objects;
	}

	public double getFocalLength() {
		return focalLength;
	}

	public double getSensorHeight() {
		return sensorHeight;
	}
	
}
