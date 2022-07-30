package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;



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
	IPoint getPoint();

	/**
	 * Gets the next point of the point referenced by the actual iterator
	 * position (clockwise direction in a standard 
	 * cartesian coordinate system).
	 *
	 * @return the next point of the point referenced by the actual iterator
	 * position.
	 */
	IPoint getNextPoint();
	
	/**
	 * Gets the previous point of the point referenced by the actual iterator
	 * position (clockwise direction in a standard 
	 * cartesian coordinate system).
	 *
	 * @return the previous point of the point referenced by the actual iterator
	 * position.
	 */
	IPoint getPreviousPoint();

	/**
	 * Moves the iterator to the next
	 * position.
	 */
	void next();
	

	/**
	 * Moves the iterator to the previous
	 * position.
	 */
	void previous();

	

	
	
    
	
    
	

}
