package propra22.q8493367.draw.model;


import propra22.q8493367.convex.Calculator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * The Class Quadrangle represents a quadrangle.
 */
public class Quadrangle implements IQuadrangle {
     
	
	private IPoint a;
	private IPoint b;
	private IPoint c;
	private IPoint d;

	/**
	 * Instantiates a new quadrangle.
	 *
	 * @param a - the point a
	 * @param b - the point b
	 * @param c - the point c
	 * @param d - the point d
	 */
	public Quadrangle(IPoint a, IPoint b, IPoint c, IPoint d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * Instantiates a new quadrangle.
	 */
	public Quadrangle() {
		this.a = null;
		this.b = null;
		this.c = null;
		this.d = null;
	}
	

	/**
	 * Gets the point a.
	 *
	 * @return the point a
	 */
	@Override
	public IPoint getA() {return a;}
	
	/**
	 * Gets the point b.
	 *
	 * @return the point b
	 */
	@Override
	public IPoint getB() {return b;}
	
	/**
	 * Gets the point c.
	 *
	 * @return the point c
	 */
	@Override
	public IPoint getC() {return c;}
	
	/**
	 * Gets the point d.
	 *
	 * @return the point d
	 */
	@Override
	public IPoint getD() {return d;}
	
	/**
	 * Sets the point a.
	 *
	 * @param a - the  point a
	 */
	@Override
	public void setA(IPoint a) {this.a = a;}
	
	/**
	 * Sets the point b.
	 *
	 * @param b - the  point b
	 */
	@Override
	public void setB(IPoint b) {this.b = b;}
	
	/**
	 * Sets the c.
	 *
	 * @param c the point c
	 */
	@Override
	public void setC(IPoint c) {this.c = c;}
	
	/**
	 * Sets the d.
	 *
	 * @param d the point d
	 */
	@Override
	public void setD(IPoint d) {this.d = d;}
	
	/**
	 * Gets the area of the quadrangle.
	 *
	 * @return The area of the quadrangle
	 */
	public long area() {
		if(a != null && b != null && c != null && d != null) {
			return Calculator.DFV(a, b, c) + Calculator.DFV(a, c, d);
		}
		else {
			return -1;
		}	
	}
}
