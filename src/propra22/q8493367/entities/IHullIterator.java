package propra22.q8493367.entities;

/**
 * An iterator for the hull, which allows the programmer to traverse the points of the 
 * hull in both directions. In this application a hull is either the contour polygon
 * or the convex hull.
 */
public interface IHullIterator {

	/**
	 * Gets the point referenced by the actual iterator
	 * position.
	 *
	 * @return the point referenced by the actual iterator
	 * position.
	 */
	Point getPoint();

	/**
	 * Gets the next point of the point referenced by the actual iterator
	 * position (clockwise direction in a
	 * cartesian coordinate system).
	 *
	 * @return the next point of the point referenced by the actual iterator
	 * position.
	 */
	Point getNextPoint();
	
	/**
	 * Gets the previous point of the point referenced by the actual iterator
	 * position (clockwise direction in a
	 * cartesian coordinate system).
	 *
	 * @return the previous point of the point referenced by the actual iterator
	 * position.
	 */
	Point getPreviousPoint();

	/**
	 * Moves the iterator to the next
	 * position moving clockwise in a 
	 * cartesian coordinate
	 * system.
	 */
	void next();
	

	/**
	 * Moves the iterator to the previous
	 * position moving counterclockwise 
	 * in a cartesian coordinate system.
	 */
	void previous();

	

	
	
    
	
    
	

}
