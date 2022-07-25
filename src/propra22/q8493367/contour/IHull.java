package propra22.q8493367.contour;



import java.util.List;

import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;


// TODO: Auto-generated Javadoc
/**
 * The Interface for a hull of a set of points.
 */
public interface IHull {
	
	/**
	 * Returns true, no points are in the section determined by
	 * the section type. False otherwise.
	 *
	 * @param sectionType - the section type
	 * @return true, if the section determined by the section type is empty
	 */
	boolean sectionIsEmpty(ContourType sectionType);
	
	/**
	 * Gets the number of points in the section
	 * determined by the section type.
	 *
	 * @param sectionType - the section type
	 * @return the number of points contained in the section
	 */
	int getSizeOfSection(ContourType sectionType);
	
	/**
	 * Adds a point to the section 
	 * determined by the section type.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	void addPointToSection(IPoint point, ContourType sectionType);
	
	/**
	 * Gets the point with index i from section 
	 * determined by the section type.
	 *
	 * @param i the i
	 * @param sectionType the section type
	 * @return the point from section
	 */
	IPoint getPointFromSection(int i, ContourType sectionType);
	
	/**
	 * Removes the point from the section
	 * determined by the section type.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	void removePointFromSection(IPoint point, ContourType sectionType);
	
	/**
	 * Removes the point from section.
	 *
	 * @param index the index
	 * @param sectionType the section type
	 */
	void removePointFromSection(int index, ContourType sectionType);
	
	
	/**
	 * Removes all points from the hull i.e. clears all four
	 * sections.
	 */
	void clear();
	
	
	
	
	
	/**
	 * Gets the index of right most point.
	 *
	 * @return the index of right most point
	 */
	int getIndexOfRightMostPoint();
	
	
	/**
	 * Empty.
	 *
	 * @return true, if successful
	 */
	boolean empty();
	
	/**
	 * Gets the point with index i.
	 *
	 * @param i - the index of the point
	 * @return the point with index i
	 */
	public IPoint get(int i);
	
	
	/**
	 * Creates the list of the points following the points
	 * counterclockwise.
	 */
	void createList();
    
	
	
	/**
	 * Returns true, if the point p is inside the hull.
	 * Returns false otherwise
	 *
	 * @param p - the point
	 * @return true, if the point p 
	 * is inside the hull, false otherwise
	 * 
	 */
	boolean contains(IPoint p);
	
	
	/**
	 * Gets the section in which the point p
	 * is located.
	 *
	 * @param p - the point
	 * @return the section
	 */
	ContourType getSection(IPoint p);
	
	/**
	 * Returns all points as an array with n rows and 2 columns 
	 * where n is the number of points and
	 * the two integers in each row represent the x coordinate and the
	 * y coordinate of a point. The points are inserted into the array 
	 * counterclockwise.
	 *
	 * @return the int array of the points following the points of the 
	 * hull counterclockwise.
	 */
	int[][] toArray();
	
	
	/**
	 * Returns the hull as a list.
	 *
	 * @return the hull as a list
	 */
	List<IPoint> toList();

	
	/**
	 * Gets an iterator starting from the left most
	 * point (lowest x coordinate and highest y coordinate)
	 * @return the hull iterator on the left most point
	 */
	IHullIterator getLeftIt();
	
	
	/**
	 * Gets an iterator starting from the right most
	 * point (highes x coordinate and lowest y coordinate).
	 *
	 * @return the hull iterator on the right most point
	 */
	IHullIterator getRightIt();
    
	
}