package propra22.q8493367.entities;



import java.util.List;



// TODO: Auto-generated Javadoc
/**
 * The Interface for the contour polygon and the convex hull.
 */
public interface IHull {
	
	/**
	 * Returns true, if no points are in the contour determined by
	 * the contour type. False otherwise.
	 *
	 * @param contourType  the contour type
	 * @return true, if the contour determined by the section type is empty, false
	 * otherwise.
	 * 
	 */
	boolean contourIsEmpty(ContourType contourType);
	
	/**
	 * Gets the number of points in the contour
	 * determined by the contour type.
	 *
	 * @param contourType the section type
	 * @return the number of points contained in the section
	 */
	int getSizeOfContour(ContourType contourType);
	
	/**
	 * Adds a point to the contour 
	 * determined by the contour type.
	 *
	 * @param point the point
	 * @param contourType the contour type
	 */
	void addPointToContour(Point point, ContourType contourType);
	
	/**
	 * Gets the point with index i from section 
	 * determined by the contour type.
	 *
	 * @param i the i
	 * @param contourType the contour type
	 * @return the point from section
	 */
	Point getPointFromContour(int i, ContourType contourType);
	
	/**
	 * Removes the point from the contour
	 * determined by the contour type.
	 *
	 * @param point the point
	 * @param contourType the contour type
	 */
	void removePointFromContour(Point point, ContourType contourType);
	
	/**
	 * Removes the point from contour.
	 *
	 * @param index the index
	 * @param contourType the section type
	 */
	void removePointFromContour(int index, ContourType contourType);
	
	
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
	 * Creates the contour polygon.
	 *
	 * @param pointSet the point set
	 */
	void set(PointSet pointSet);

	/**
	 * Creates the convex hull.
	 */
	void clean();
    
	
}
