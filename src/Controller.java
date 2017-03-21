import java.util.HashMap;

public class Controller {
	
	public static void main(String[] args) {
		
		// Importing information about the objects that we know about
		ImportCSV csv = new ImportCSV("res/environment.csv");
		
		// HashMap to store object Ids, their real coordinates and their radius
		HashMap<String,String[]> realObjects = new HashMap<String,String[]>();
		for (String key : csv.getData().keySet()) {
			realObjects.put(key, csv.getData().get(key).split(","));
		}
		
		
		// Camera gets its input after processing image
		Camera c = new Camera(0,0,10);
		c.getData();
		
		// Camera gives data to controller
		HashMap<String,String[]> cameraObjects = c.getObjects();
		
		/*
		// Iterate through the objects it has recognised 
		for (String key : cameraObjects.keySet()) {
			String id = key;
			int leftX = Integer.parseInt(cameraObjects.get(key)[0]);
			int leftY = Integer.parseInt(cameraObjects.get(key)[1]);
			int rightX = Integer.parseInt(cameraObjects.get(key)[2]);
			int rightY = Integer.parseInt(cameraObjects.get(key)[3]);
			
			// Create a new object with the given data
			//Object obj = new Object(id, leftX, leftY, rightX, rightY);
			
			// Find the known data of the object stored with the same id
			String[] realObjData = realObjects.get(id);
			System.out.println("Item id: "+key);
			
			// Calculate the distance in pixels from the centre of the object to the cameras centre point
			double distanceFromCenterPx = calcCenterDistance(leftX, leftY, rightX, rightY);
			System.out.println("Distance from centre in pixels: " + distanceFromCenterPx);
			
			// Get the known width of the object from known data
			double knownWidth = 2 * Double.parseDouble((realObjData[2]));
			System.out.println("Known object width (cm): "+knownWidth);
			
			// If we know the shapes width in cm and pixels we can calculate how much distance is covered by
			// one pixel
			double distancePerPx = Math.abs(knownWidth / obj.getWidthPx());
			System.out.println("Object width in px: "+obj.getWidthPx());
			
			// We can calculate the distance from the centre by multiplying the distance in pixels
			// by the number of pixels we are away
			double distanceFromCenter = Math.abs(distanceFromCenterPx * distancePerPx);
			
			// Store this information in the object. Should do this for all calculated data really
			obj.setDistanceCenter(distanceFromCenter);
			System.out.println("Distance from centre in cm: " + distanceFromCenter);
			
			// Calculate the height of the camera relative to the object
			double height = calcHeight(distancePerPx);

			// Calculate the angle to the object
			double angle = calcAngle(distanceFromCenter, height);
			System.out.println("Calculated height: "+  height  );
			System.out.println("Calculated angle: "+ angle);
			
			// Calculate the distance from the camera
			System.out.println("Calculated distance from camera: "+calcDistance(angle, distanceFromCenter));
			System.out.println("________________________________________________");
			
		}*/
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
