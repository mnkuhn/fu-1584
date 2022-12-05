package entities;

import java.util.List;


/**
 * The Interface for a hull which can be 
 * the contour polygon and the convex hull.
 * It provides methods among others, which calculate
 * the contour polygon from a point set and the convex 
 * hull from the contour polygon and a method 
 * for retrieving an iterator for reading
 * purposes.
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
	 * where n is the number of points. The inner array contains
	 * the x and the y coordinate of the point.
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
	 * Creates the convex hull from the
	 * contour polygon.
	 */
	void clean();
    
	
}
