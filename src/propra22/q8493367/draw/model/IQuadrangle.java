package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Interface IQuadrangle.
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
	 * @param a - the  point a
	 */
	void setA(IPoint a);

	/**
	 * Sets the point b.
	 *
	 * @param b - the  point b
	 */
	void setB(IPoint b);

	/**
	 * Sets the c.
	 *
	 * @param c the point c
	 */
	void setC(IPoint c);

	/**
	 * Sets the d.
	 *
	 * @param d the point d
	 */
	void setD(IPoint d);
    
	/**
	 * The area of the quadrangle.
	 *
	 * @return the area of the quadrangle
	 */
	long area();
    
	/**
	 * Return the point set as an nX2 array, where n is the number of points.
	 * array[i][0] is the x coordinate of point i, array[i][1] is the y
	 * coordinate of point i. 
	 *
	 * @return the int[][]
	 */
	int[][] asArray();
	
	void copy(IQuadrangle quadrangle);

}
