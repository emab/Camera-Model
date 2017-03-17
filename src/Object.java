// Class to represent the objects which will be seen by the camera
public class Object {
	
	// This will always be a colour
	private String id;
	// Pixel co-ordinates of box
	private int topLeft_X;
	private int topLeft_Y;
	private int bottomRight_X;
	private int bottomRight_Y;
	
	// Object width pixels
	private int widthPx;
	
	// Known object width
	private int objWidth;
	
	// Known object co-ordinates
	//private int X;
	//private int Y;
	
	// Distance to object from camera
	private double distance;
	
	// Distance from object to center point
	private double distanceCenter;
	
	// Angle from camera to center of object
	private double angle;
	
	// Init object and calculate its width in px
	public Object(String id, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		this.id = id;
		this.topLeft_X = topLeftX;
		this.topLeft_Y = topLeftY;
		this.bottomRight_X = bottomRightX;
		this.bottomRight_Y = bottomRightY;
		
		widthPx = Math.abs(topLeft_X - bottomRight_X);
	}
	
	// Getters and setters
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

	public int getObjWidth() {
		return objWidth;
	}

	public void setObjWidth(int objWidth) {
		this.objWidth = objWidth;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getWidthPx() {
		return widthPx;
	}

	public double getDistanceCenter() {
		return distanceCenter;
	}

	public void setDistanceCenter(double distanceCenter) {
		this.distanceCenter = distanceCenter;
	}
	
}
