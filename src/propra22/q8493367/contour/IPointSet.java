package propra22.q8493367.contour;


import propra22.q8493367.point.IPoint;



/**
 * This interface declares methods to manage a set of points.
 */

public interface IPointSet {
	
	/**
	 * Returns the number of Points contained in the point set. 
	 * @return number of points in the point set.
	 */
	public int getNumberOfPoints();
	
	/**
	 * Returns true, if the point set does not contain a point. Returns false otherwise.
	 *
	 * @return true, if the point set does not contain a point, false otherwise.
	 */
	public boolean isEmpty();
	
	
	/**
	 * Returns the point with the specified index.
	 *
	 * @param index  the index of the point in the point set.
	 * @return the point with the specified index.
	 */
	public IPoint getPointAt(int index);
	
	/**
	 * Removes removes the point specified by the index from
	 * the point set.
	 *
	 * @param p the point that is to be removed.
	 */
	public void removePoint(IPoint p);
	
	/**
	 * Removes a point with the coordinates x and y from
	 * the point set if the point set contains such a point.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	void removePoint(int x, int y);
	
	/**
	 * Sorts the elements of the point set lexicographically.
	 */
	public void lexSort();
	
	
	/**
	 * Removes all points from the point set.
	 */
	void clear();

	/**
	 * Adds a point to the point set.
	 *
	 * @param p  the point which is added to the point set.
	 * 
	 */
	void addPoint(IPoint p);

	/**
	 * Checks if the point set contains a point with the same
	 * coordinates as p.
	 *
	 * @param p  the point
	 * @return the index of the point in the point set if 
	 * the point set contains a point with same coordinates as p. A negative
	 * value is returned otherwise.
	 */
	int hasPoint(IPoint p);
    
	
	/**
	 * Tests if  the point set has changed. Returns true, if the point set 
	 * has changed since the last time the corresponding value was set to false with
	 * {@link #setHasChanged(boolean b)}.
	 *
	 * @return true, if the point set has changed since
	 * the last time the corresponding value was set to false.
	 */
	public boolean hasChanged();

	/**
	 * Sets the state of the point set.
	 *
	 * @param b true if the state of the point set is to be set 
	 * to 'has changed', false if the state of the point 
	 * set is to be set to 'has not changed'.
	 */
	void setHasChanged(boolean b);
	
	/**
	 * Gets the minimum x coordinate of all the points
	 * in the point set.
	 *
	 * @return the minimum x coordinate
	 */
	int getMinX();

	/**
	 * Gets the maximum x coordinate of all the points
	 * in the point set.
	 *
	 * @return the maximum x coordinate of all the points
	 * in the point set.
	 */
	int getMaxX();

	/**
	 * Gets the minimum y coordinate of all the points
	 * in the point set.
	 *
	 * @return the minimum y coordinate.
	 */
	int getMinY();

	/**
	 * Gets the maximum y coordinate of all the points
	 * in the point set.
	 *
	 * @return the maximum y coordinate of all the points
	 * in the point set.
	 */
	int getMaxY();

	/**
	 * Returns the number of points in the point set.
	 *
	 * @return the number of points in the point
	 * set.
	 */
	public int size();



	

	
	
}
