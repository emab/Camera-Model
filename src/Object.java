// Class to represent the objects which will be seen by the camera
public class Object {
	
	// This will always be a colour
	private String id;
	
	// Camera co-ordinates of box
	private double centerX;
	private double centerY;
	
	// Real radius
	private double radius;
	
	// Object width pixels
	private double widthPx;
	
	// Known object width
	private double objWidth;
	
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
	public Object(String id, double X, double Y, double radius, double distance, double pxWidth, double xCentre, double yCentre) {
		this.id = id;
		this.X = X;
		this.Y = Y;
		this.radius = radius;
		this.distance = distance;
		this.widthPx = pxWidth;
		this.centerX = xCentre;
		this.centerY = yCentre;
	}
	
	// Getters and setters
	public String getId() {
		return id;
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

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public double getRadius() {
		return radius;
	}

	public double getWidthPx() {
		return widthPx;
	}

	public double getObjWidth() {
		return objWidth;
	}
	
}
