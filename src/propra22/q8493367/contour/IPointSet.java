package propra22.q8493367.contour;


import propra22.q8493367.point.IPoint;


// TODO: Auto-generated Javadoc
/**
 * A Collection of objects of the type Point.
 * The IDrawPanelModel interface provides methods to manage these objects.
 * It also provides the administration of 4 subsets of these points, which are used to calculate the points which are
 * part of the contour polygon respectively the points which are part of the convex hull.
 */

public interface IPointSet {
	
	/**
	 * returns the number of Points contained in the model. 
	 * @return number of Points if the number is bigger than 1 and 0, if no points are contained.
	 */
	public int getNumberOfPoints();
	
	/**
	 * Returns true, if model contains no points.
	 *
	 * @return true, if model contains no points
	 */
	public boolean isEmpty();
	
	
	/**
	 * return the point with the specified index.
	 *
	 * @param index - the index of the point in the data structure used
	 * @return the point with the specified index
	 */
	public IPoint getPointAt(int index);
	
	/**
	 * removes the specified point from the model.
	 *
	 * @param p which should be removed
	 */
	public void removePoint(IPoint p);
	
	/**
	 * removes the point with the x coordinate x and the y coordinate y 
	 * from the model if it is contained by the model.
	 *
	 * @param x the x
	 * @param y the y
	 */
	void removePoint(int x, int y);
	
	/**
	 * sorts the elements of the model, i.e. the points, lexicographically
	 */
	public void lexSort();
	
	
	/**
	 * Removes all points from the point set.
	 */
	void clear();

	/**
	 * Adds a point to the point set.
	 *
	 * @param p - the point which is added 
	 * to the point set
	 */
	void addPoint(IPoint p);

	/**
	 * Checks if the point set contains a point with the same
	 * coordinates as p.
	 *
	 * @param p - the point
	 * @return the index of the point in the pointset if 
	 * the point set contains the point. A negative
	 * value otherwise.
	 */
	int hasPoint(IPoint p);
    
	

	/**
	 * Returns true, if the point set has changed since
	 * a defined moment. Returns false otherwise.
	 *
	 * @return true, the point set has changed since 
	 * a defined moment. False otherwise.
	 */
	public boolean hasChanged();

	/**
	 *Sets the point set to 'has changed' if b is true.
	 *If b is false, the point set is set to 'has not changed'.
	 *
	 * @param b the boolean value, which determines if the point
	 * set is set to 'has changed' or to 'has not changed'
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