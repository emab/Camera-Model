// Class to represent the objects which will be seen by the camera
public class Object {
	
	// This will always be a colour
	private String id;
	// Pixel co-ordinates of box
	private int topLeft_X;
	private int topLeft_Y;
	private int bottomRight_X;
	private int bottomRight_Y;

	public Object(String id, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		this.id = id;
		this.topLeft_X = topLeftX;
		this.topLeft_Y = topLeftY;
		this.bottomRight_X = bottomRightX;
		this.bottomRight_Y = bottomRightY;
	}
	
	public String getId() {
		return id;
	}

	public int getTopLeft_X() {
		return topLeft_X;
	}

	public int getTopLeft_Y() {
		return topLeft_Y;
	}

	public int getBottomRight_X() {
		return bottomRight_X;
	}

	public int getBottomRight_Y() {
		return bottomRight_Y;
	}
	
}
