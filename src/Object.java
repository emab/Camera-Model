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
	
	// Known object co-ordinates
	private double X;
	private double Y;
	
	// adjusted coordinates
	private double adjX;
	private double adjY;
	
	// Distance to object from camera
	private double distance;
	
	// Distance from object to center point
	private double distanceCenter;
	
	// Arc of object in camera vision
	private double arc;
	
	
	// Adds known object
	public Object(String id, double adjX, double adjY, double radius, double distance, double pxWidth, 
			double xCentre, double yCentre, double arc, double X, double Y) {
		this.id = id;
		this.adjX = adjX;
		this.adjY = adjY;
		this.radius = radius;
		this.distance = distance;
		this.widthPx = pxWidth;
		this.centerX = xCentre;
		this.centerY = yCentre;
		this.arc = arc;
		this.X = X;
		this.Y = Y;
	}
	
	// Getters and setters
	public String getId() {
		return id;
	}

	public double getDistance() {
		return distance;
	}

	public double getArc() {
		return arc;
	}

	public double getDistanceCenter() {
		return distanceCenter;
	}

	public double getAdjX() {
		return adjX;
	}

	public double getAdjY() {
		return adjY;
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
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
}
