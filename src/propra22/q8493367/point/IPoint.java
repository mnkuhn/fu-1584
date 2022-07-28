package propra22.q8493367.point;


// TODO: Auto-generated Javadoc
/**
 * The Interface IPoint provides methods for a class
 * in which represents a point.
 */
public interface IPoint extends Comparable<IPoint> {
    
	/**
	 * Gets the x coordinate of the point.
	 *
	 * @return the x coordinate of the point
	 */
	public int getX();
	
	/**
	 * Gets the y coordinate of the point.
	 *
	 * @return the y coordinate of the point
	 */
	public int getY();
	
	/**
	 * Translates a point along the x and the y axis so it represents
	 * the point (x+dy, y+dy).
	 *
	 * @param dx the distance to move this point along the x axis
	 * @param dy the distance to move this point along the y axis
	 */
	public void translate(int dx, int dy);
	
	/**
	 * Compare to is used to implement the used order relation
	 * to compare two points. This means, 
	 * if the returned int is smaller than zero then this &lt; point. 
	 * If the returned int is equal to zero then this == point.
	 * If the returned int is bigger than zero then this &gt; point.
	 *
	 * @param p the point which is compared with the point represented
	 * by this.
	 * @return the result of the comparison as described above.
	 */
	public int compareTo(IPoint p);
	
	/**
	 * This method returns a string with the coordinates
	 * of the point.
	 *
	 * @return the string with the coordinates of the point.
	 */
	public String toString();
	
	/**
	 * Adds two points using vector addition.
	 *
	 * @param p the point which is added to the point
	 * represented by this.
	 * @return the point which results from the addition.
	 */
	public IPoint add(IPoint p);
	
	/**
	 * Subtracts two points using vector addition.
	 *
	 * @param p the point which is subtracted from the point
	 * represented by this.
	 * @return the point which results from the subtraction.
	 */
	public IPoint subtract(IPoint p);
	
	/**
	 * Returns true, if two points have the same coordinates,
	 * false otherwise.
	 *
	 * @param p the point whose coordinates are checked against 
	 * the coordinates of the point represented by this.
	 * 
	 * @return true, if the two points have the same coordinates,
	 * false otherwise.
	 */
	public boolean equals(IPoint p);
	
	
	/**
	 * Sets the point as selected if b is true. The point is set as not selected
	 * if b is false.
	 *
	 * @param b true if this point is to be selected, otherwise false
	 */
	public void setSelected(boolean b);

	/**
	 * Checks if this point is selected.
	 *
	 * @return true, if is selected, false otherwise
	 */
	public boolean isSelected();

}
