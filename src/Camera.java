import java.util.Collection;

// This class will create Objects depending on what it reads in from a CSV file and its current location
public class Camera {
	
	final private int SIZE_X = 1280;
	final private int SIZE_Y = 800;
	final private int FOV_X = 75;
	final private int FOV_Y = 47;
	
	private Collection<Object> objects;
	
	public Camera(String dataIn) {
		ImportCSV csv = new ImportCSV(dataIn);
		
		for (String key : csv.getData().keySet()) {
			String[] coordinates = (csv.getData().get(key)).split(",");
			int leftX = Integer.parseInt(coordinates[0]);
			int leftY = Integer.parseInt(coordinates[1]);
			int rightX = Integer.parseInt(coordinates[2]);
			int rightY = Integer.parseInt(coordinates[3]);
			
			Object o = new Object(key, leftX, leftY, rightX, rightY);
			objects.add(o);
		}
	}
	public Object getObjectInfo(Object o) {
		return o;	
	}
}
