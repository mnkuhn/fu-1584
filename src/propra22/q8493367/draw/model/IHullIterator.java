package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;



// TODO: Auto-generated Javadoc
/**
 * The Interface for an iterator over a hull.
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
	 * Gets the next point referenced by the actual iterator
	 * position (clockwise direction in a standard 
	 * cartesian coordinate system).
	 *
	 * @return the next point of the point referenced by the actual iterator
	 * position.
	 */
	IPoint getNextPoint();
	
	/**
	 * Gets the previous point referenced by the actual iterator
	 * position (counterclockwise direction in a standard 
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
	 * position in the hull
	 */
	void previous();

	

	
	
    
	
    
	

}
