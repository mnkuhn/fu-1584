package propra22.q8493367.entities;

/**
 * The Class Quadrangle represents a quadrangle.
 */
public class Quadrangle {
     
	
	/**  The first of the four points of the quadrangle. */
	private Point a;
	
	/**  The second of the four points of the quadrangle. */
	private Point b;
	
	/**  The third of the four points of the quadrangle. */
	private Point c;
	
	/**  The fourth of the four points of the quadrangle. */
	private Point d;

	/**
	 * Instantiates a new quadrangle.
	 *
	 * @param a the first point
	 * @param b the second point
	 * @param c the third point
	 * @param d the fourth point
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
	 * Gets the point a.
	 *
	 * @return the point a
	 */
	public Point getA() {return a;}
	
	/**
	 * Gets the point b.
	 *
	 * @return the point b
	 */
	public Point getB() {return b;}
	
	/**
	 * Gets the point c.
	 *
	 * @return the point c
	 */
	public Point getC() {return c;}
	
	/**
	 * Gets the point d.
	 *
	 * @return the point d
	 */
	public Point getD() {return d;}
	
	/**
	 * Sets the point a.
	 *
	 * @param a the  point a
	 */
	public void setA(Point a) {this.a = a;}
	
	/**
	 * Sets the point b.
	 *
	 * @param b the  point b
	 */
	public void setB(Point b) {this.b = b;}
	
	/**
	 * Sets the point c.
	 *
	 * @param c the point c
	 */
	public void setC(Point c) {this.c = c;}
	
	/**
	 * Sets the point d.
	 *
	 * @param d the point d
	 */
	public void setD(Point d) {this.d = d;}
	
	/**
	 * Returns the area of the quadrangle.
	 *
	 * @return the area of the quadrangle
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
	 * Return the point set as an n X 2 array, where n is the number of points.
	 * array[i][0] is the x coordinate of point i, array[i][1] is the y
	 * coordinate of point i. 
	 *
	 * @return the int[][]
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
