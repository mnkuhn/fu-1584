package propra22.q8493367.entities;

/**
 * The Class Diameter represents the diameter of the convex hull
 * which is a pair of two points A and B of the hull. There is no other
 * pair with bigger distance between the points of the pair.
 */
public class Diameter {
	
	
	/** The first end point of the diameter*/
	private Point a;
	
	/** The second end point of the diameter */
	private Point b;
	
	/**
	 * Instantiates a new diameter.
	 */
	public Diameter() {
		this.a = null;
		this.b = null;
	}
	
	/**
	 * Instantiates a new diameter.
	 *
	 * @param a the first end point. 
	 * @param b the second end point.
	 */
	public Diameter(Point a, Point b) {
		this.a = a;
		this.b = b;
	}
	
	
	
	/**
	 * Returns the the point A.
	 *
	 * @return the point A
	 */
	public Point getA() {
		return a;
	}
	
	/**
	 * Returns the point B.
	 *
	 * @return the point b
	 */
	public Point getB() {
		return b;
	}
    
	/**
	 * Sets the the point A.
	 *
	 * @param a the point a
	 */
	public void setA(Point a) {
		this.a = a;
		
	}
    
	/**
	 * Sets the point B.
	 *
	 * @param b  the point b
	 */
	public void setB(Point b) {
		this.b = b;
		
	}

	/**
	 * Returns the length of the diameter.
	 *
	 * @return the length of the diameter as double.
	 */
	public double length() {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt( dx*dx + dy*dy );
	}
	
	/**
	 * Returns the Diameter as an 2x2 array of integers.
	 *
	 * @return the 2x2 array, containing the coordinates of the 
	 * two end points of the diameter. The inner array contains 
	 * the x and the y coordinate of the point.
	 */
	public int[][] asArray(){
		int[][] diameterArr = new int[2][2];
		diameterArr[0][0] = a.getX();
		diameterArr[0][1] = a.getY();
		diameterArr[1][0] = b.getX();
		diameterArr[1][1] = b.getY();
		return diameterArr;
	}
	
	/**
	 * Copies the attributes of the 
	 * arguments into the caller.
	 *
	 * @param diameter the diameter whose attributes
	 * are copied into the caller.
	 */
	public void copy(Diameter diameter) {
		this.a = diameter.getA();
		this.b = diameter.getB();	
	}
}
