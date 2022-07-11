package propra22.q8493367.point;




// TODO: Auto-generated Javadoc
/**
 * The Class Point represents a point in a cartesian coordinate system.
 */
public class Point  implements IPoint {

	/**  The x coordinate of the point. */
	private int x;
	
	/**  The y coordinate of the point. */
	private int y;
	
	/**
	 * Instantiates a new point.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point(int x, int y)  {
		 this.x = x;
		 this.y = y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getX() {return x;}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getY() {return y;}
	
	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void translate(int dx, int dy) {
		x = x + dx;
		y = y + dy;
	}
	
	


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(IPoint p) {
		if(this.x < p.getX()) {return -1;}
		 else if (this.x > p.getX()) {return 1;}
		 else if (this.y > p.getY()) {return -1;}
		 else if (this.y < p.getY()) {return 1;}
		 else {return 0;}
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append(" ");
		builder.append(y);
		return builder.toString();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint add(IPoint p) {
		return new Point(this.x + p.getX(), this.y + p.getY());
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPoint subtract(IPoint p) {
		return new Point(this.x - p.getX(), this.y - p.getY());
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(IPoint p) {
		return (this.x == p.getX()) && (this.y == p.getY());
	}
	
	
	/**
	 * Returns the signed area multiplied by 2 of the triangle created by the three points a, b and c.
	 * If, in a cartesian coordinate system, c is on the left side of the line going through a and b
	 * the sign is negative.
	 * If c is on the right side of the line going throug a and b, the sign is positive.
	 * If c is on the line going through a and b, the result is 0.
	 *
	 * @param a the point a
	 * @param b the point b
	 * @param c the point c
	 * @return the signed area of the triangle
	 */
	public static long signedTriangleArea(IPoint a, IPoint b, IPoint c) {
		
		long summand1 = (long) a.getX() * ((long) b.getY() - (long) c.getY());
		long summand2 = (long) b.getX() * ((long) c.getY() - (long) a.getY());
		long summand3 = (long) c.getX() * ((long) a.getY() - (long) b.getY());

		return - (summand1 + summand2 + summand3);
	}
	
	/**
	 *Returns the square of the euclidean distance 
	 *between the two points a and b.
	 *
	 * @param a the point a
	 * @param b the point b
	 * @return the square of the euclidean distance
	 */
	public static long qaudraticDistance(IPoint a, IPoint b) {
		long dx = (long) a.getX() - (long) b.getX();
		long dy = (long) a.getY() - (long) b.getY();
		return dx * dx + dy * dy;
	}
	
	/**
	 * Returns true, if the square of the euclidean distance
	 * between the points a and b is shorter than the euclidean 
	 * distance between the point c and d.
	 *
	 * @param a the point a
	 * @param b the point b
	 * @param c the point c
	 * @param d the point d
	 * @return true, if the square of the euclidean distance 
	 * between the points a and b is shorter than the euclidean 
	 * distance between the point c and d. 
	 * Returns false otherwise.
	 */
	public static boolean isShorter(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) < qaudraticDistance(c, d);
	}
	
	/**
	 * Returns true, if the square of the euclidean distance
	 * between the points a and b is longer than the euclidean 
	 * distance between the point c and d.
	 *
	 * @param a the point a
	 * @param b the point b
	 * @param c the point c
	 * @param d the point d
	 * @return true, if the square of the euclidean distance 
	 * between the points a and b is longer than the euclidean 
	 * distance between the point c and d. 
	 * Returns false otherwise.
	 */
	public static boolean isLonger(IPoint a, IPoint b, IPoint c, IPoint d) {
		return qaudraticDistance(a, b) > qaudraticDistance(c, d);
	}
	
	/**
	 * Compares the two triangles a - c - d1 and a - c - d2. Returns true, if the first
	 * triangle is higher over the base a - c than the second triangle.
	 * Returns false otherwise
	 *
	 * @param a the point a
	 * @param c the the point c
	 * @param d1 the point d1
	 * @param d2 the point d2
	 * @return true, if the triangle a - c - d1 is higher over the base a - c than
	 * the triangle a - c - d2
	 */
	public static boolean isHigher(IPoint a, IPoint c, IPoint d1, IPoint d2) {
		long x = (long)a.getX() + (long)d2.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)d2.getY() - (long)c.getY();
		IPoint sum = new Point((int)x, (int)y);
		return signedTriangleArea(sum, d2, d1) > 0;	
	}
	
	
	/**
	 * Angle comparison test. 
	 *
	 * @param a the point a
	 * @param afterA the point following a in the convex hull
	 * @param b the point b
	 * @param afterB the point following b in the convex hull
	 * @return the result of the angle comparison test as described in the specification
	 */
	public  static long angleComparisonTest(IPoint a, IPoint afterA, IPoint b, IPoint afterB) {
		long xTip = (long) a.getX() + (long) b.getX() - (long) afterB.getX();
		long yTip = (long) a.getY() + (long) b.getY() - (long) afterB.getY();
		IPoint tip = new Point((int) xTip, (int) yTip);
		return  Point.signedTriangleArea(a, afterA, tip);
	}
	
}


