package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

/**
 * The Interface for a diameter
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

}
