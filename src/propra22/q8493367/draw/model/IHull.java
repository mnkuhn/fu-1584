package propra22.q8493367.draw.model;



import propra22.q8493367.contour.SectionType;
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
	boolean sectionIsEmpty(SectionType sectionType);
	
	/**
	 * Gets the number of points in the section
	 * determined by the section type.
	 *
	 * @param sectionType - the section type
	 * @return the number of points contained in the section
	 */
	int getSizeOfSection(SectionType sectionType);
	
	/**
	 * Adds a point to the section 
	 * determined by the section type.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	void addPointToSection(IPoint point, SectionType sectionType);
	
	/**
	 * Gets the point with index i from section 
	 * determined by the section type.
	 *
	 * @param i the i
	 * @param sectionType the section type
	 * @return the point from section
	 */
	IPoint getPointFromSection(int i, SectionType sectionType);
	
	/**
	 * Removes the point from the section
	 * determined by the section type.
	 *
	 * @param point the point
	 * @param sectionType the section type
	 */
	void removePointFromSection(IPoint point, SectionType sectionType);
	
	/**
	 * Removes the point from section.
	 *
	 * @param index the index
	 * @param sectionType the section type
	 */
	void removePointFromSection(int index, SectionType sectionType);
	
	
	/**
	 * Removes all points from the hull i.e. clears all four
	 * sections.
	 */
	void clear();
	
	
	
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
	 * Gets the index of right most point.
	 *
	 * @return the index of right most point
	 */
	int getIndexOfRightMostPoint();
	
	/**
	 * Gets the number of points in the hull.
	 *
	 * @return the number of points in the hull.
	 */
	int size();
	
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
	 * Gets the iterator.
	 *
	 * @param index - the index from which the iterator starts
	 * @return the iterator
	 */
	IHullIterator getIterator(int index);
}
