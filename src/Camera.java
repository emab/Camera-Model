
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	final private int SIZE_X = 1280;
	final private int SIZE_Y = 800;
	final private int FOV_X = 75;
	final private int FOV_Y = 47;
	
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
}
