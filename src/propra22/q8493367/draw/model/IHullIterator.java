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
	 * position.
	 *
	 * @return the next point referenced by the actual iterator
	 * position.
	 */
	IPoint getNextPoint();
	
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
