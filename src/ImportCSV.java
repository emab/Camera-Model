import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;


public class ImportCSV {
	
	// Store data from file
	HashMap<String,String> objects = new HashMap<String,String>(); 
	
	// Vairbales needed to read file
    BufferedReader br = null;
    String line = "";
    String split = ",";
    
	public ImportCSV(String fileName) {
		try {
		
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] object = line.split(split);
				String objectId = object[0];
				String objectData = object[1];
				for (int i = 2; i < object.length; i++) {
					objectData = objectData + "," + object[i];
				}
				// Store objects
				objects.put(objectId, objectData);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	public HashMap<String, String> getData() {
		return objects;
	}
}
