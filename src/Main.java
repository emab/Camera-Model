import java.util.Collection;

public class Main {
	
	
	public static void main(String[] args) {
		
		// Importing information about the objects that we know about.
		ImportCSV csv = new ImportCSV("res/environment.csv");
		
		// Camera gets its input from environment
		Camera c = new Camera("res/input.csv");
		
		// Camera gives data to controller
		Collection<Object> cameraObjects = c.getObjects();
		
		String[][] objInfo = new String[cameraObjects.size()][3];
		
		int count = 0;
		
		// Iterate through the objects it has recognised and calculate some values about them
		while (cameraObjects.iterator().hasNext()) {
			int leftX = cameraObjects.iterator().next().getTopLeft_X();
			int leftY = cameraObjects.iterator().next().getTopLeft_Y();
			int rightX = cameraObjects.iterator().next().getBottomRight_X();
			int rightY = cameraObjects.iterator().next().getBottomRight_Y();
		}
	}

}
