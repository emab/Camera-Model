
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");
		
		
		ImportCSV csv = new ImportCSV("/Users/emab/Documents/Eclipse/Home/Camera Model/res/input.csv");
		System.out.println(csv.getData().entrySet());
	}

}
