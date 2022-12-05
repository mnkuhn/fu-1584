package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



/** 
 * The Class PointSet represents a set of points. Among others
 * it provides methods for inserting, removing and searching points and 
 * sorting the points in lexicographical order.
 */
public class PointSet {
	
	
	/** The points*/
	private List<Point> points = new ArrayList<>();
	
	/** True, if the point set was changed since the last time
	 * this variable was set to false.
	 */
	private boolean hasChanged = false;
	
	
	
	/**
	 * Checks if the point set contains a point with the same
	 * coordinates as p.
	 *
	 * @param p  the point to be checked
	 * @return the index of the point in the point set if 
	 * the point set contains a point with same coordinates as p. A negative
	 * value otherwise.
	 */
	public  int hasPoint(Point p) {
		return searchPoint(p);
	}
	
	
	/**
	 * Adds a point to the point set only if there 
	 * is no point with the same coordinates in 
	 * the point set. The point set is sorted 
	 * afterwards.
	 *
	 * @param point  the point which is added to the point set.
	 * 
	 */
	public void addCheckedWithSorting(Point point) {
		if(!(hasPoint(point) >= 0)) {
			points.add(point);
			lexSort();
			hasChanged = true;
		}
		
	}
	
	/**
	 * Adds a point to the point
	 * set without checking, if the point 
	 * is already in the point set. We only use
	 * this method for inserting a randomly 
	 * generated point, because we know already
	 * that this point is not in the point set.
	 * @param point the point to be inserted
	 */
	public void addUncheckedWithSorting(Point point) {
		points.add(point);
		lexSort();
		hasChanged = true;
		
	}
	
	
	/**
	 * Adds a point to the point set without checking 
	 * for duplicates and without sorting. This method
	 * is only used when reading points from a file 
	 * into the point set. By all means sorting and checking 
	 * has to be done after this method has been called.
	 * {@link PointSet#sortAndCheck()}
	 * @param point the point to be inserted
	 */
	public void addUncheckedWithoutSorting(Point point) {
		points.add(point);
	}
	
	/**
	 * This method sorts the point list and removes the duplicates.
	 * @see <a href="https://stackoverflow.com/questions/54671799/how-to-sort-and-eliminate-the-duplicates-from-arraylist">stackoverflow</a>
	 */
	public void sortAndCheck() {
		points = points.stream().sorted().distinct().collect(Collectors.toList());		
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
	 * value is returned.
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
	 * Returns the number of points contained in the point set. 
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
	 * @throws IndexOutOfBoundsException This exception is thrown,
	 * if the index is below 0 or equal or above the size of the 
	 * point set.
    */
	public Point getPointAt(int index) {
		return points.get(index);
	}
	
	
	/**
	 * Returns true, if the point set does not contain any point. Returns false otherwise.
	 *
	 * @return true, if the point set does not contain any point, false otherwise.
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
	 * Tests if the point set has changed. Returns true, if the point set 
	 * has changed since the last time the corresponding value was set to false with
	 * {@link PointSet#setHasChanged(boolean b)}.
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
	 * Returns the minimum x coordinate of all the points
	 * in the point set. Returns 0 if the 
	 * point set is empty.
	 *
	 * @return the minimum x coordinate of all the points
	 * in the point set.
	 */
	public int getMinX() {
		if(!points.isEmpty()) {
			return points.get(0).getX();
		}
		return 0;
		
	}

	
	/**
	 * Returns the maximum x coordinate of all the points
	 * in the point set. Returns 0 if the 
	 * point set is empty.
	 *
	 * @return the maximum x coordinate of all the points
	 * in the point set.
	 */
	public int getMaxX() {
		
		if(!points.isEmpty()) {
			return points.get(points.size() - 1).getX();
		}
		return 0;
		
	}

	
	/**
	 * Returns the minimum y coordinate of all the points
	 * in the point set. Returns 0 if the 
	 * point set is empty.
	 *
	 * @return the minimum y coordinate.
	 */
	public int getMinY() {
		if(!points.isEmpty()) {
			int min = points.get(0).getY();
			for(Point p : points) {
				if(p.getY() < min) {
					min = p.getY();
				}
			}
			return min;
		}
	
		return 0;
	}

	
	/**
	 * Returns the maximum y coordinate of all the points
	 * in the point set. Returns 0 if the 
	 * point set is empty.
	 *
	 * @return the maximum y coordinate of all the points
	 * in the point set.
	 */
	public int getMaxY() {
		if(!points.isEmpty()) {
			int max = points.get(0).getY();
			for(Point p : points) {
				if(p.getY() > max) {
					max = p.getY();
				}
			}
			return max;
		}
	
		return 0;
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
