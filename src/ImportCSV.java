import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;


public class ImportCSV {
	
	HashMap<String,String> objects = new HashMap<String,String>(); 
	
    BufferedReader br = null;
    String line = "";
    String split = ",";
    
	public ImportCSV(String fileName) {
		try {
		
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] object = line.split(split);
				String objectId = object[0];
				String objectData = object[1] + "," + object[2] + "," + object[3];
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
