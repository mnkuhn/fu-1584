package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/** The Class PointSet represents a set of points. */
public class PointSet implements IPointSet {
	
	
	/** The points*/
	private List<IPoint> points = new ArrayList<>();
	
	/** True, if the point set was changed since a defined
	 * moment. False otherwise.
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
	 *{@inheritDoc}
	 */
	@Override
	public  int hasPoint(IPoint point) {
		return searchPoint(point);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void addPoint(IPoint point) {
		if(!(hasPoint(point) >= 0)) {
			points.add(point);
		}
		checkForNewBounds(point);
		hasChanged = true;
	}

	/**
	 * Check for new bounds.
	 *
	 * @param point the point
	 */
	private void checkForNewBounds(IPoint point) {
		if (points.size() == 0) {
			minX = 0;
			maxX = 0;
			minY = 0;
			maxY = 0;
		}
		else if(points.size() == 1) {
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
	 *{@inheritDoc}
	 */
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);
		hasChanged = true;
	}
	
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {
			points.remove(index);
			hasChanged = true;
		}	
	}
	
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void lexSort() {
		Collections.sort(points);
	}

	
	/**
	 * Returns the index of the point, if the point
	 * list contains the point. If the point list
	 * does not contain the point a negative
	 * number is returned.
	 *
	 * @param point the point whose presence in this list is to be tested
	 * @return the int
	 */
	private int searchPoint(IPoint point) {
		return Collections.binarySearch(points, point);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
		
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getNumberOfPoints(); i++) {
			  stringBuilder.append(points.get(i).toString() + "\n");
		}
		return stringBuilder.toString();
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
    public void clear() {
		points.clear();
		hasChanged = true;
		
		
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public boolean hasChanged() {
		return hasChanged;
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setHasChanged(boolean b) {
		hasChanged = b;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public int getMinX() {
		return minX;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public int getMaxX() {
		return maxX;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public int getMinY() {
		return minY;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public int getMaxY() {
		return maxY;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public int size() {
		return points.size();
	}
	
	
	
	
	
}
