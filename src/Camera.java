
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	// Focal length in cm
	private final double focalLength = 0.28;
	
	// Sensor height in cm
	private final double sensorHeight = 0.635;
	
	private HashMap<String,String[]> objects = new HashMap<String,String[]>();

	private String fileDest;
	
	public Camera(String dataIn) {
		this.fileDest = dataIn;

		}
	
	public void getData() {
		ImportCSV csv = new ImportCSV(fileDest);
		
		for (String key : csv.getData().keySet()) {
			String[] coordinates = (csv.getData().get(key)).split(",");
			objects.put(key, coordinates);
		}
	}
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
