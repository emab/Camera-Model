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
			
			double distanceFromCenterPx = calcCenterDistance(leftX, leftY, rightX, rightY);
			
			System.out.println(distanceFromCenterPx);
			double knownWidth = 2 * Integer.parseInt(realObjData[2]);
			System.out.println(knownWidth);
			System.out.println(obj.getWidthPx());
			double distancePerPx = knownWidth / obj.getWidthPx();
			System.out.println(distancePerPx);
			double distanceFromCenter = distanceFromCenterPx * distancePerPx;
			System.out.println(distanceFromCenter);
		}
	}
	
	public static double calcCenterDistance(int leftX, int leftY, int rightX, int rightY) {
		int camCenterX = 400;
		int camCenterY = 640;
		
		int objCenterX = (leftX + rightX) / 2;
		int objCenterY = (leftY + rightY) / 2;
		
		return Math.sqrt(Math.pow((objCenterX - camCenterX), 2) 
				+ Math.pow((objCenterY - camCenterY), 2));
	}
}
