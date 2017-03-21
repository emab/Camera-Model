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
	private double X;
	private double Y;
	
	// Distance to object from camera
	private double distance;
	
	// Distance from object to center point
	private double distanceCenter;
	
	// Angle from camera to center of object
	private double angle;
	
	// Adds known object
	public Object(double X, double Y) {
		this.X = X;
		this.Y = Y;
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

	public double getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
}
