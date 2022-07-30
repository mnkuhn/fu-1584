package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;


/** The Class PointSet represents a set of points. */
public class PointSet implements IPointSet {
	
	
	/** The points*/
	private List<IPoint> points = new ArrayList<>();
	
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
	
	
	
	
	@Override
	public  int hasPoint(IPoint point) {
		return searchPoint(point);
	}
	
	
	@Override
	public void addPoint(IPoint point) {
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
	private void checkForNewBounds(IPoint point) {
		
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
	
	
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);
		hasChanged = true;
	}
	
	
	
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {
			points.remove(index);
			hasChanged = true;
		}	
	}
	
	
	
	@Override
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
	private int searchPoint(IPoint point) {
		return Collections.binarySearch(points, point);
	}
	
	
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	
	@Override
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

	
	@Override
    public void clear() {
		points.clear();
		hasChanged = true;
		
		
	}

	
	@Override
	public boolean hasChanged() {
		return hasChanged;
	}
	
	
	@Override
	public void setHasChanged(boolean b) {
		hasChanged = b;
	}

	
	@Override
	public int getMinX() {
		return minX;
	}

	
	@Override
	public int getMaxX() {
		return maxX;
	}

	
	@Override
	public int getMinY() {
		return minY;
	}

	
	@Override
	public int getMaxY() {
		return maxY;
	}

	
	@Override
	public int size() {
		return points.size();
	}
	
}
