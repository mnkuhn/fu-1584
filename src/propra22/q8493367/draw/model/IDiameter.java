package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Interface for a diameter.
 */
public interface IDiameter {

	/**
	 * Gets the point a.
	 *
	 * @return the point a
	 */
	IPoint getA();

	/**
	 * Gets the point b.
	 *
	 * @return the point b
	 */
	IPoint getB();

	/**
	 * Sets the point a.
	 *
	 * @param a - the point a
	 */
	void setA(IPoint a);

	/**
	 * Sets the point b.
	 *
	 * @param b - the point b
	 */
	void setB(IPoint b);

	/**
	 * The length of the diameter.
	 *
	 * @return the length of the diameter as double.
	 */
	double length();
    
	
	/**
	 * Returns the Diameter as an 2x2 array.
	 *
	 * @return the 2x2 array, containing the coordinates of the 
	 * two end points of the diameter:
	 * [0][0] - the first end points x coordinate.
	 * [0][1] - the first end points y coordinate.
	 * [1][0] - the second end points x coordinate.
	 * [1][1] - the second end points y coordinate.
	 */
	int[][] asArray();
	
    
	/**
	 * Copies the attributes of the 
	 * arguments into the caller.
	 *
	 * @param diameter the diameter whose attributes
	 * are copied into this.
	 */
	void copy(IDiameter diameter);

}
