package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;



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

	/**
	 * Moves the iterator to the next
	 * position.
	 */
	void next();

	/**
	 * Returns true if the iterator has 
	 * reached the limit.
	 *
	 * @return true if the iterator has reached the limit.
	 * False otherwise.
	 */
	boolean hasReachedLimit();
	
	/**
	 * Gets the index of the point in the hull 
	 * referenced by the iterator.
	 *
	 * @return the index of the point in the hull
	 * referenced by the iterator.
	 */
	int getIndex();
	

}
