package propra22.q8493367.entities;

/**
 * An Interface for an iterator, which allows the programmer to traverse the points of the 
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
	 * position.
	 *
	 * @return the next point of the point referenced by the actual iterator
	 * position.
	 */
	Point getNextPoint();
	
	/**
	 * Gets the previous point of the point referenced by the actual iterator
	 * position.
	 *
	 * @return the previous point of the point referenced by the actual iterator
	 * position.
	 */
	Point getPreviousPoint();

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
