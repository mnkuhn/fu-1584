package propra22.q8493367.contour;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Interface for a diameter.
 */
public interface IDiameter {

	/**
	 * Gets the first point of the diameter.
	 *
	 * @return the point A
	 */
	IPoint getA();

	/**
	 * Gets the second point of the diameter.
	 *
	 * @return the point b
	 */
	IPoint getB();

	/**
	 * Sets the first point of the diameter.
	 *
	 * @param a the point a
	 */
	void setA(IPoint a);

	/**
	 * Sets the second point of the diameter.
	 *
	 * @param b  the point b
	 */
	void setB(IPoint b);

	/**
	 * Returns the length of the diameter.
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
	 * are copied into the caller.
	 */
	void copy(IDiameter diameter);

}
