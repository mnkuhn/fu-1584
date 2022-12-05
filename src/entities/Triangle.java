package entities;




/**
 * The Class Triangle represents a triangle. Among other methods it provides 
 * getters and setters for the three triangle points A, B and C and a method
 * which returns twice the area of the triangle. This method is only used for
 * comparison purposes.
 */
public class Triangle {
	
	/** The point A. */
	private Point a;
	
	/** The point B. */
	private Point b;
	
	/** The point C. */
	private Point c;
	
	
	/**
	 * Instantiates a new triangle.
	 *
	 * @param a the point A
	 * @param b the point B
	 * @param c the point C
	 */
	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * Instantiates a new triangle.
	 */
	public Triangle() {
		this.a = null;
		this.b = null;
		this.c = null;
	}
	
	/**
	 * The copy constructor
	 *
	 * @param triangle the triangle whose
	 * arguments are copied into this.
	 */
	public Triangle(Triangle triangle) {
		this.a = triangle.getA();
		this.b = triangle.getB();
		this.c = triangle.getC();
	}


	/**
	 * Returns the point A.
	 *
	 * @return the point A
	 */
	public Point getA() {
		return a;
	}
	
	/**
	 * Sets the point A.
	 *
	 * @param a the new point A
	 */
	public void setA(Point a) {
		this.a = a;
	}
	
	/**
	 * Returns the point B
	 *
	 * @return the point B
	 */
	public Point getB() {
		return b;
	}
	
	/**
	 * Sets the point B.
	 *
	 * @param b the point B
	 */
	public void setB(Point b) {
		this.b = b;
	}
	
	/**
	 * Returns the point C.
	 *
	 * @return the point C
	 */
	public Point getC() {
		return c;
	}
	
	/**
	 * Sets the point C.
	 *
	 * @param c the new point C
	 */
	public void setC(Point c) {
		this.c = c;
	}
	
	/**
	 * Returns twice the area of the triangle.
	 *
	 * @return twice the area of the triangle
	 */
	public long area() {
		return Math.abs(Point.signedTriangleArea(a, b, c));
	}
	
	/**
	 * Copies the attributes of the argument into the caller.
	 *
	 * @param triangle the triangle whose attributes are copied 
	 * into this.
	 */
	public void copy(Triangle triangle) {
		this.a = triangle.getA();
		this.b = triangle.getB();
		this.c = triangle.getC();
	}
	
	/**
	 *Returns the triangle as an array of integers where
	 *the two integers of the inner array are the x and the y
	 *coordinate of one of the three points.
	 *
	 * @return the coordinates of the points of the triangle 
	 * as an integer array.
	 */
	public int[][] asArray(){
		int[][] array = new int[3][2];
		array[0][0] = a.getX();
		array[0][1] = a.getY();
		array[1][0] = b.getX();
		array[1][1] = b.getY();
		array[2][0] = c.getX();
		array[2][1] = c.getY();
		return array;
	}
}
