package entities;

/**
 * The Class Quadrangle represents a quadrangle. It provides getters and
 * setters for the four points of the quadrangle and a method
 * which returns twice the area of the quadrangle. The last 
 * method is only used for comparison purposes.
 */
public class Quadrangle {
     
	
	/** The point a of the quadrangle. */
	private Point a;
	
	/** The point b of the quadrangle. */
	private Point b;
	
	/** The point c of the quadrangle. */
	private Point c;
	
	/** The point d of the quadrangle.. */
	private Point d;

	/**
	 * Instantiates a new quadrangle.
	 *
	 * @param a the the point A
	 * @param b the the point B
	 * @param c the the point C
	 * @param d the the point D
	 */
	public Quadrangle(Point a, Point b, Point c, Point d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * Instantiates a new quadrangle.
	 */
	public Quadrangle() {
		this.a = null;
		this.b = null;
		this.c = null;
		this.d = null;
	}
	
    
	/**
	 * Copy Constructor for the Quadrangle. Instantiates a new quadrangle
	 * with the same attributes as the argument.
	 *
	 * @param quadrangle the quadrangle whose attributes are copied into
	 * the newly created quadrangle.
	 */
	public Quadrangle(Quadrangle quadrangle) {
		this.a = quadrangle.getA();
		this.b = quadrangle.getB();
		this.c = quadrangle.getC();
		this.d = quadrangle.getD();
		
	}

	/**
	 * Returns the point A.
	 *
	 * @return the point A
	 */
	public Point getA() {return a;}
	
	/**
	 * Returns the point B.
	 *
	 * @return the point B
	 */
	public Point getB() {return b;}
	
	/**
	 * Returns the point C.
	 *
	 * @return the point C
	 */
	public Point getC() {return c;}
	
	/**
	 * Returns the point D.
	 *
	 * @return the point D
	 */
	public Point getD() {return d;}
	
	/**
	 * Sets the point A.
	 *
	 * @param a the  point A
	 */
	public void setA(Point a) {this.a = a;}
	
	/**
	 * Sets the point B.
	 *
	 * @param b the  point B
	 */
	public void setB(Point b) {this.b = b;}
	
	/**
	 * Sets the point C.
	 *
	 * @param c the point C
	 */
	public void setC(Point c) {this.c = c;}
	
	/**
	 * Sets the point D.
	 *
	 * @param d the point D
	 */
	public void setD(Point d) {this.d = d;}
	
	/**
	 * Returns two times the area of the quadrangle.
	 *
	 * @return two times the area of the quadrangle
	 */
	public long area() {
		if(a != null && b != null && c != null && d != null) {
			return Point.signedTriangleArea(a, b, c) + Point.signedTriangleArea(a, c, d);
		}
		else {
			return -1;
		}	
	}
	
	/**
	 * Returns the quadrangle as a 4 X 2 array.
	 * The inner array contains the x and the y coordinate of a point.
	 *
	 * @return the array of integers, containing the 
	 * coordinates of the points of the quadrangle.
	 */
	public int [][] asArray(){
		int[][] quadrangleAsArray = new int[4][2];
		
		quadrangleAsArray[0][0] = a.getX();
		quadrangleAsArray[0][1] = a.getY();
		
		quadrangleAsArray[1][0] = b.getX();
		quadrangleAsArray[1][1] = b.getY();
		
		quadrangleAsArray[2][0] = c.getX();
		quadrangleAsArray[2][1] = c.getY();
		
		quadrangleAsArray[3][0] = d.getX();
		quadrangleAsArray[3][1] = d.getY();
		
		return quadrangleAsArray;
	}

	/**
	 * Copies the attributes of the 
	 * arguments into the caller.
	 *
	 * @param quadrangle the quadrangle
	 * whose attributes are copied
	 * into the caller.
	 */
	public void copy(Quadrangle quadrangle) {
		this.a = quadrangle.getA();
		this.b = quadrangle.getB();
		this.c = quadrangle.getC();
		this.d = quadrangle.getD();
		
	}
}
