package propra22.q8493367.draw.model;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;


/**
 * A Collection of objects of the type Point.
 * The IDrawPanelModel interface provides methods to administrate these objects.
 * It also provides the administration of 4 subsets of these points, which are used to calculate the points which are
 * part of the contour polygon respectively the points which are part of the convex hull.
 */

public interface IDrawPanelModel {
	
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
	 * Returns true, if model contains a point with the coordinated x and y.
	 * @param x - x coordinate of the point
	 * @param y - y coordinate of the point
	 * @return true, if model contains a point with the coordinated x and y.
	 */
	public boolean hasPoint(int x, int y);
	
	/**
	 * adds a point to the model
	 * @param x - x coordinate of the point
	 * @param y - y coordinate of the point
	 */
	
	public void addPoint(int x, int y);
	
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
	
	/**
	 * returns true, if the specified section is empty 
	 * @param sectionType - the specified section
	 * @return
	 */
	public boolean sectionIsEmpty(SectionType sectionType);
	
	/**
	 * returns the size of the specified section
	 * @param sectionType - the specified section
	 * @return the size of the specified section
	 */
	public int getSizeOfSection(SectionType sectionType);
	
	/**
	 * return the point with index i from the specified section
	 * @param index - index of the point within the section
	 * @param sectionType - the specified section
	 * @return the point at the specified position in the specified section
	 */
	public IPoint getPointFromSection(int index, SectionType sectionType);
	
	/**
	 * adds a point to the specified section
	 * @param point - point which should be added to the section
	 * @param sectionType - the specified section
	 */
	public void addPointToSection(IPoint point, SectionType sectionType);
	
	/**
	 * clears the section with the specified sectionType
	 * @param sectionType - the specified section
	 */
	public void clearSection(SectionType sectionType);
	
	/**
	 * removes the point from the specified section
	 * @param index - index of the point which should be removed
	 * @param sectionType - the specified section
	 */
	public void removeSectionPoint(int index, SectionType sectionType);
	
	/**
	 * returns a String with all points and their x and y coordinates registered in the model
	 * @return String with all points and their x and y coordinates registered in the model
	 */
	public String toString();

	IPoint getSectionPointAt(int i, SectionType sectionType);

	void clear();

	void addPoint(IPoint point);

	
	
}
