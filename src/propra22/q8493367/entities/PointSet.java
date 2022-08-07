package propra22.q8493367.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/** The Class PointSet represents a set of points. The point
 * set is the data base of the application. 
 */
public class PointSet {
	
	
	/** The points*/
	private List<Point> points = new ArrayList<>();
	
	/** True, if the point set was changed since the last time
	 * this variable was set to false.
	 */
	private boolean hasChanged = false;
	
	/** The minimum x coordinate of all the points in the point set.*/
	private int minX = 0;
	
	/** The maximum x coordinate of all the points in the point set.*/
	private int maxX = 0;
	
	/** The minimum y coordinate of all the points in the point set.*/
	private int minY = 0;
	
	/** The maximum y coordinate of all the points in the point set.*/
	private int maxY = 0;
	
	
	/**
	 * Checks if the point set contains a point with the same
	 * coordinates as p.
	 *
	 * @param point  the point
	 * @return the index of the point in the point set if 
	 * the point set contains a point with same coordinates as p. A negative
	 * value is returned otherwise.
	 */
	public  int hasPoint(Point point) {
		return searchPoint(point);
	}
	
	
	/**
	 * Adds a point to the point set.
	 *
	 * @param point  the point which is added to the point set.
	 * 
	 */
	public void addPoint(Point point) {
		if(!(hasPoint(point) >= 0)) {
			points.add(point);
		}
		checkForNewBounds(point);
		hasChanged = true;
	}

	/**
	 * Checks for new minimum and maximum coordinates, 
	 * if a new point is added to the point set.
	 *
	 * @param point the point which is added to the point set.
	 */
	private void checkForNewBounds(Point point) {
		
	    if(points.size() == 1) {
			maxX = point.getX();
			minX = point.getX();
			maxY = point.getY();
			minY = point.getY();
		}
		else if(points.size() > 1) {
			int tempX = point.getX();
			int tempY = point.getY();
			if(tempX > maxX) {maxX = tempX;}
			if(tempX < minX) {minX = tempX;}
			if(tempY > maxY) {maxY = tempY;}
			if(tempY < minY) {minY = tempY;}
		}
	}
	
	
	/**
	 * Removes the point specified by the index from
	 * the point set.
	 *
	 * @param point the point to be removed.
	 */
	public void removePoint(Point point) {
		points.remove(point);
		hasChanged = true;
	}
	
	
	
	/**
	 * Removes a point with the coordinates x and y from
	 * the point set if the point set contains such a point.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {
			points.remove(index);
			hasChanged = true;
		}	
	}
	
	

	/**
	 * Sorts the elements of the point set lexicographically.
	 */
	public void lexSort() {
		if(!points.isEmpty()) {
			Collections.sort(points);
		}
	}

	
	/**
	 * Returns the index of the point, if the point
	 * list contains the point. If the point list
	 * does not contain the point, a negative
	 * number is returned.
	 *
	 * @param point the point whose presence in this list is to be tested
	 * @return the index of the point in the list, if it exists, a
	 * negative value otherwise. This method does not test on identity but
	 * on same coordinates.
	 */
	private int searchPoint(Point point) {
		return Collections.binarySearch(points, point);
	}
	
	
	/**
	 * Returns the number of Points contained in the point set. 
	 * @return number of points in the point set.
	 */
	public int getNumberOfPoints() {
		return points.size();
	}
	
	/**
	 * Returns the point with the specified index.
	 *
	 * @param index  the index of the point in the point set.
	 * @return the point with the specified index.
	 */
	public Point getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	
	/**
	 * Returns true, if the point set does not contain a point. Returns false otherwise.
	 *
	 * @return true, if the point set does not contain a point, false otherwise.
	 */
	public boolean isEmpty() {
		return points.isEmpty();
	}
		
	
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			  stringBuilder.append(points.get(i).toString() + "\n");
		}
		return stringBuilder.toString();
	}

	
	/**
	 * Removes all points from the point set.
	 */
    public void clear() {
		points.clear();
		hasChanged = true;
		
		
	}

	
    /**
	 * Tests if  the point set has changed. Returns true, if the point set 
	 * has changed since the last time the corresponding value was set to false with
	 * {@link #setHasChanged(boolean b)}.
	 *
	 * @return true, if the point set has changed since
	 * the last time the corresponding value was set to false.
	 */
	public boolean hasChanged() {
		return hasChanged;
	}
	
	
	/**
	 * Sets the state of the point set.
	 *
	 * @param b true if the state of the point set is to be set 
	 * to 'has changed', false if the state of the point 
	 * set is to be set to 'has not changed'.
	 */
	public void setHasChanged(boolean b) {
		hasChanged = b;
	}

	
	/**
	 * Gets the minimum x coordinate of all the points
	 * in the point set.
	 *
	 * @return the minimum x coordinate
	 */
	public int getMinX() {
		return minX;
	}

	
	/**
	 * Gets the maximum x coordinate of all the points
	 * in the point set.
	 *
	 * @return the maximum x coordinate of all the points
	 * in the point set.
	 */
	public int getMaxX() {
		return maxX;
	}

	
	/**
	 * Gets the minimum y coordinate of all the points
	 * in the point set.
	 *
	 * @return the minimum y coordinate.
	 */
	public int getMinY() {
		return minY;
	}

	
	/**
	 * Gets the maximum y coordinate of all the points
	 * in the point set.
	 *
	 * @return the maximum y coordinate of all the points
	 * in the point set.
	 */
	public int getMaxY() {
		return maxY;
	}

	
	/**
	 * Returns the number of points in the point set.
	 *
	 * @return the number of points in the point
	 * set.
	 */
	public int size() {
		return points.size();
	}
	
}
