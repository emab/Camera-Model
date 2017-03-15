// Class to represent the objects which will be seen by the camera
public class Object {
	
	// This will always be a colour
	private String id;
	// Radius of the circle
	private int topLeftBox;
	private int bottomRightBox;

	public Object(String id, int topLeftBox, int bottomRightBox) {
		this.id = id;
		this.topLeftBox = topLeftBox;
		this.bottomRightBox = bottomRightBox;
	}
	
	public String getId() {
		return id;
	}
	public int getTopLeftBox()
	{
		return topLeftBox;
	}
	public int getBottomRightBox()
	{
		return bottomRightBox;
	}
}
