package propra22.q8493367.entities;



/**
 * The Class Point represents a point in a cartesian 
 * coordinate system. It implements the interface Comparable &lt; Point &gt;
 * so we can construct a lexicographic order over a set
 * of points.
 */
public class Point implements Comparable<Point> {

	/**  The x coordinate of the point in the coordinate 
	 * system of the model. 
	 */
	private int x;
	
	/**  The y coordinate of the point in the coordinate 
	 * system of the model. 
	 */
	private int y;
	
	/** True, if this point is selected, false otherwise. */
	private boolean selected;
	
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
	 * Returns the x coordinate of the point.
	 *
	 * @return the x coordinate of the point.
	 */
	public int getX() {return x;}
	

	
	
	/**
	 * Returns the y coordinate of the point.
	 *
	 * @return the y coordinate of the point.
	 */
	public int getY() {return y;}
	
	
	

	/**
	 * Translates a point along the x and the y axis so it represents
	 * the point (x+dy, y+dy).
	 *
	 * @param dx the distance to move this point along the x axis
	 * @param dy the distance to move this point along the y axis
	 */
	public void translate(int dx, int dy) {
		x = x + dx;
		y = y + dy;
	}
	
    
	/**
	 * Implements the compareTo method of the Comparable &lt; Point &gt;
	 * interface. In this application, a lexicographical order 
	 * is implemented.
	 *
	 * @param p the point to which the comparison refers.
	 */
	@Override
	public int compareTo(Point p) {
		if(this.x < p.getX()) {return -1;}
		 else if (this.x > p.getX()) {return 1;}
		 else if (this.y > p.getY()) {return -1;}
		 else if (this.y < p.getY()) {return 1;}
		 else {return 0;}
	}
	

	/**
	 * This method returns a string with the coordinates
	 * of the point.
	 *
	 * @return the string with the coordinates of the point.
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
	 * Returns true, if two points have the same coordinates,
	 * false otherwise.
	 *
	 * @param p the point whose coordinates are checked against 
	 * the coordinates of the point represented by this.
	 * 
	 * @return true, if the two points have the same coordinates,
	 * false otherwise.
	 */
	@Override
	public boolean equals(Object p) {
		return (this.x == ((Point)p).getX()) && (this.y == ((Point)p).getY());
	}
	
	
	
	/**
	 * Returns the signed area multiplied by 2 of the triangle created by the three points a, b and c.
	 * If, in a standard cartesian coordinate system, c is on the left side of the tip of 
	 * the vector ab the sign is negative.
	 * If c is on the right side of the tip of the vector ab, the sign is positive.
	 * If c is on the line going through the vector ab, the result is 0.
	 *
	 * @param a the point a
	 * @param b the point b
	 * @param c the point c
	 * @return the signed area of the triangle
	 */
	public static long signedTriangleArea(Point a, Point b, Point c) {
		
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
	public static long qaudraticDistance(Point a, Point b) {
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
	public static boolean isShorter(Point a, Point b, Point c, Point d) {
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
	public static boolean isLonger(Point a, Point b, Point c, Point d) {
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
	public static boolean isHigher(Point a, Point c, Point d1, Point d2) {
		long x = (long)a.getX() + (long)d2.getX() - (long)c.getX();
		long y = (long)a.getY() + (long)d2.getY() - (long)c.getY();
		Point sum = new Point((int)x, (int)y);
		return signedTriangleArea(sum, d2, d1) > 0;	
	}
	
	
	/**
	 * The method for the Angle Comparison test. 
	 *
	 * @param a the point a
	 * @param afterA the point following a in the convex hull
	 * @param b the point b
	 * @param afterB the point following b in the convex hull
	 * @return the result of the angle comparison test as described in the specification
	 */
	public  static long angleComparisonTest(Point a, Point afterA, Point b, Point afterB) {
		long xTip = (long) a.getX() + (long) b.getX() - (long) afterB.getX();
		long yTip = (long) a.getY() + (long) b.getY() - (long) afterB.getY();
		Point tip = new Point((int) xTip, (int) yTip);
		return  Point.signedTriangleArea(a, afterA, tip);
	}
	
	

	/**
	 * Sets the point as selected if b is true. The point is set as not selected
	 * if b is false.
	 *
	 * @param b true if this point is to be selected, otherwise false
	 */
	public void setSelected(boolean b) {
		selected = b;
	}
	/**
	 * Checks if this point is selected.
	 *
	 * @return true, if this point is selected, false otherwise
	 */
	public boolean isSelected() {
		return selected;
		
	}
	
}


