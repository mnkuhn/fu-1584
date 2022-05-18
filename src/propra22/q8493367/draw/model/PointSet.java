package propra22.q8493367.draw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import propra22.q8493367.contour.ContourPolygonCalculator;
import propra22.q8493367.contour.SectionType;
import propra22.q8493367.convex.ConvexHullCalculator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * This class implements the interface IDrawPanelModel. It provides methods to 
 */
public class PointSet implements IPointSet {
	
	/** The points. */
	private List<IPoint> points = new ArrayList<>();
	
	/** The left lower. */
	private List<IPoint> leftLower = new ArrayList<>();
	
	/** The right lower. */
	private List<IPoint> rightLower = new ArrayList<>();
	
	/** The right upper. */
	private List<IPoint> rightUpper = new ArrayList<>();
	
	/** The left upper. */
	private List<IPoint> leftUpper = new ArrayList<>();
	
	
	/**
	 * Checks if a point is in the model
	 *
	 * @param point the point
	 * @return true, if successful
	 */
	@Override
	public  boolean hasPoint(IPoint point) {
		return searchPoint(point) >= 0;
	}
	
	
	/**
	 * Adds a point to the draw panel.
	 *
	 * @param point - point which is added.
	 */
	@Override
	public void addPoint(IPoint point) {
		if(!hasPoint(point)) {points.add(point);}
	}
	
	/**
	 * Removes a point from the draw panel model.
	 * @param point - the point which is removed.
	 */
	@Override
	public void removePoint(IPoint point) {
		points.remove(point);	
	}
	
	/**
	 * Removes the point with x coordinate x and y
	 * coordinate y if it is in the draw panel model.
	 *
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void removePoint(int x, int y) {
		int index = searchPoint(new Point(x, y));
		if(index >= 0) {points.remove(index);}	
	}
	
	/**
	 * Sorts the set of points in the model lexicographically.
	 */
	@Override
	public void lexSort() {
		Collections.sort(points);
	}

	/**
	 * Searches a point in the set of points in the
	 * draw panel model. If the point is in the draw panel 
	 * model, the index is returned, if it is not 
	 * in the draw panel otherwise -1.
	 *
	 * @param point - the point that is searched
	 * @return the int
	 */
	private int searchPoint(IPoint point) {
		return Collections.binarySearch(points, point);
	}
	
	/**
	 * Gets the number of points which are in the 
	 * draw panel model.
	 *
	 * @return the number of points
	 */
	@Override
	public int getNumberOfPoints() {
		return points.size();
	}
	
	/**
	 * Gets the point at the specified index.
	 *
	 * @param index the index
	 * @return the point at
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	@Override
	public IPoint getPointAt(int index) throws IndexOutOfBoundsException {
		return points.get(index);
	}
	
	/**
	 * Returns true if the point set is empty, false otherwise.
	 *
	 * @return true, if is empty
	 */
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
		
	
	/**
	 * Returns a string with all the points of the 
	 * point set.
	 *
	 * @return the string
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
	 * Clears the point set.
	 */
	@Override
    public void clear() {
		points.clear();	
	}
}
