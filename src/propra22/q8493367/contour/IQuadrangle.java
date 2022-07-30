package propra22.q8493367.contour;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The interface for a quadrangle.
 */
public interface IQuadrangle {

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
	 * Gets the point c.
	 *
	 * @return the point c
	 */
	IPoint getC();

	/**
	 * Gets the point d.
	 *
	 * @return the point d
	 */
	IPoint getD();

	/**
	 * Sets the point a.
	 *
	 * @param a the  point a
	 */
	void setA(IPoint a);

	/**
	 * Sets the point b.
	 *
	 * @param b the  point b
	 */
	void setB(IPoint b);

	/**
	 * Sets the point c.
	 *
	 * @param c the point c
	 */
	void setC(IPoint c);

	/**
	 * Sets the point d.
	 *
	 * @param d the point d
	 */
	void setD(IPoint d);
    
	/**
	 * Returns the area of the quadrangle.
	 *
	 * @return the area of the quadrangle
	 */
	long area();
    
	/**
	 * Return the point set as an n X 2 array, where n is the number of points.
	 * array[i][0] is the x coordinate of point i, array[i][1] is the y
	 * coordinate of point i. 
	 *
	 * @return the int[][]
	 */
	int[][] asArray();
	
	
	
	/**
	 * Copies the attributes of the 
	 * arguments into the caller.
	 *
	 * @param quadrangle the quadrangle
	 * whose attributes are copied
	 * into the caller.
	 */
	void copy(IQuadrangle quadrangle);

}
