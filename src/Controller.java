import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
	
	private int actualHeight;
	private int calculatedHeight;
	
	
	public static void main(String[] args) {
		
		// Importing information about the objects that we know about.
		ImportCSV csv = new ImportCSV("res/environment.csv");
		HashMap<String,String[]> realObjects = new HashMap<String,String[]>();
		for (String key : csv.getData().keySet()) {
			realObjects.put(key, csv.getData().get(key).split(","));
		}
		
		
		// Camera gets its input from environment
		Camera c = new Camera("res/input.csv");
		c.getData();
		
		// Camera gives data to controller
		HashMap<String,String[]> cameraObjects = c.getObjects();
		
		String[][] objInfo = new String[cameraObjects.size()][3];
		
		int count = 0;
		
		// Iterate through the objects it has recognised and calculate some values about them
		for (String key : cameraObjects.keySet()) {
			String id = key;
			int leftX = Integer.parseInt(cameraObjects.get(key)[0]);
			int leftY = Integer.parseInt(cameraObjects.get(key)[1]);
			int rightX = Integer.parseInt(cameraObjects.get(key)[2]);
			int rightY = Integer.parseInt(cameraObjects.get(key)[3]);
			
			Object obj = new Object(id, leftX, leftY, rightX, rightY);
			
			String[] realObjData = realObjects.get(id);
			
			System.out.println("Item id: "+key);
			
			double distanceFromCenterPx = calcCenterDistance(leftX, leftY, rightX, rightY);
			System.out.println("Distance from centre in pixels: " + distanceFromCenterPx);
			
			double knownWidth = 2 * Double.parseDouble((realObjData[2]));
			double distancePerPx = Math.abs(knownWidth / obj.getWidthPx());
			
			
			
			double distanceFromCenter = Math.abs(distanceFromCenterPx * distancePerPx);
			
			obj.setDistanceCenter(distanceFromCenter);
			
			System.out.println("Distance from centre in cm: " + distanceFromCenter);
			
			//System.out.println(distanceToObject(8, 800, 300, 0.28, 0.635));
			
			System.out.println("Calculated height: "+  calcHeight(distancePerPx)  );
			
			// This isn't the right way to work out the angles!
//			double xCenter = (leftX + rightX) / 2;
//			double yCenter = (leftY + rightY) / 2;
//			double xdistFraction = Math.abs((640 - xCenter)) / 640;
//			double Xangle = Math.toDegrees(Math.atan((      (100.0 * xdistFraction   ) / 130.32     )));
//			System.out.println("Angle on X axis: " + Xangle);
//			
//			double ydistFraction = Math.abs((400 - yCenter)) / 400;
//			System.out.println(ydistFraction);
//			double Yangle = Math.toDegrees(Math.atan(( (56.66 * ydistFraction) / 130.32    )));
//			System.out.println("Angle on Y axis: " + Yangle);
//			
//			double averageAngle = (Xangle + Yangle)/2;
//			System.out.println("Average angle to object: " + averageAngle);
			System.out.println("________________________________________________");
		}
	}
	
	public static double calcHeight(double distancePerPx) {
		double result = (640 * distancePerPx) / Math.tan(75/2);
		return Math.abs(result);
	}
	
	public static double calcCenterDistance(int leftX, int leftY, int rightX, int rightY) {
		int camCenterX = 640;
		int camCenterY = 400;
		
		int objCenterX = (leftX + rightX) / 2;
		int objCenterY = (leftY + rightY) / 2;
		
		System.out.println(objCenterX + "  "+ objCenterY);
		
		return Math.sqrt( Math.pow((camCenterX - objCenterX), 2) + Math.pow((camCenterY - objCenterY), 2) );
	}
	
	public static double distanceToObject(double realHeightCm, double imageHeightPx, double objectHeightPx,
			double focalLengthCm, double sensorHeightCm) {
		double distance = (focalLengthCm * realHeightCm * imageHeightPx) / (objectHeightPx * sensorHeightCm); 
		return distance;
	}
}
