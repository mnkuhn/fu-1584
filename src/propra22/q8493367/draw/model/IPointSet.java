package propra22.q8493367.draw.model;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;


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
	 * Returns true, if model contains no points
	 * @return true, if model contains no points
	 */
	public boolean isEmpty();
	
	
	/**
	 * return the point with the specified index
	 * @param index - the index of the point in the data structure used
	 * @return the point with the specified index
	 */
	public IPoint getPointAt(int index);
	
	/**
	 * removes the specified point from the model
	 * @param point which should be removed
	 */
	public void removePoint(IPoint point);
	
	/**
	 * removes the point with the x coordinate x and the y coordinate y 
	 * from the model if it is contained by the model
	 * @param x
	 * @param y
	 */
	void removePoint(int x, int y);
	
	/**
	 * sorts the elements of the model, i.e. the points, lexicographically
	 */
	public void lexSort();
	
	

	void clear();

	void addPoint(IPoint point);

	boolean hasPoint(IPoint point);

	
	
}