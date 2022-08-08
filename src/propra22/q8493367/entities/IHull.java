package propra22.q8493367.entities;

import java.util.List;




/**
 * The Interface for the contour polygon and the convex hull.
 */
public interface IHull {
	


	/**
	 * Removes all points from the hull i.e. clears all four
	 * contours.
	 */
	void clear();
	
	
	/**
	 * Returns true, if all 4 contours are empty, false otherwise.
	 *
	 * @return true, if all 4 contours are empty, false otherwise.
	 */
	boolean isEmpty();
	

	
	/**
	 * Returns all points as an array with n rows and 2 columns 
	 * where n is the number of points in the hull and
	 * the two integers in each row represent the x coordinate and the
	 * y coordinate of a point. The points are inserted into the array 
	 * clockwise.
	 *
	 * @return the int array of the points following the points of the 
	 * hull clockwise.
	 */
	int[][] toArray();
	
	
	/**
	 * Returns the hull as a list.
	 *
	 * @return the hull as a list
	 */
	List<Point> toList();

	
	/**
	 * Gets an iterator starting from the left most
	 * point (lowest x coordinate with highest y coordinate).
	 *
	 * @return the hull iterator on the left most point
	 */
	IHullIterator leftIterator();
	
	
	/**
	 * Gets an iterator starting from the right most
	 * point (highest x coordinate with lowest y coordinate).
	 *
	 * @return the hull iterator on the right most point
	 */
	IHullIterator rightIterator();
    
	
	/**
	 * Checks if the hull contains exactly one point.
	 *
	 * @return true, if the hull contains exactly one point,
	 * false otherwise.
	 */
	boolean hasOnePoint();

	/**
	 * Calculates the contour polygon from
	 * the point set.
	 *
	 * @param pointSet the point set
	 */
	void set(PointSet pointSet);

	/**
	 * Creates the convex hull.
	 */
	void clean();
    
	
}
