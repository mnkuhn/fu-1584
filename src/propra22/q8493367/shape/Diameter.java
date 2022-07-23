package propra22.q8493367.shape;

import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Class Diameter.
 */
public class Diameter implements IDiameter {
	
	
	/** The first end point of the diameter*/
	private IPoint a;
	
	/** The second end point of the diameter */
	private IPoint b;
	
	/**
	 * Instantiates a new diameter.
	 */
	public Diameter() {
		this.a = null;
		this.b = null;
	}
	
	/**
	 * Instantiates a new diameter.
	 *
	 * @param a the a
	 * @param b the b
	 */
	public Diameter(IPoint a, IPoint b) {
		this.a = a;
		this.b = b;
	}
	
	
	/**
	 * Gets the first end point of the diameter.
	 *
	 * @return the first end point.
	 */
	@Override
	public IPoint getA() {
		return a;
	}
	
	
	/**
	 * Gets the second end point of the diameter.
	 *
	 * @return the second end point.
	 */
	@Override
	public IPoint getB() {
		return b;
	}
    
	
	/**
	 * Sets the first end point of the diameter.
	 *
	 * @param a - the first end point
	 */
	@Override
	public void setA(IPoint a) {
		this.a = a;
		
	}
    
	
	/**
	 * Sets the second end point of the diameter.
	 *
	 * @param b - the second end point.
	 */
	@Override
	public void setB(IPoint b) {
		this.b = b;
		
	}

	
	/**
	 * Returns the length of the diameter in
	 * pixels.
	 * @return the the of the diameter in pixels.
	 */
	@Override
	public double length() {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt( dx*dx + dy*dy );
	}
	
	/**
	 * As array.
	 *
	 * @return the int[][]
	 */
	@Override
	public int[][] asArray(){
		int[][] diameterArr = new int[2][2];
		diameterArr[0][0] = a.getX();
		diameterArr[0][1] = a.getY();
		diameterArr[1][0] = b.getX();
		diameterArr[1][1] = b.getY();
		return diameterArr;
	}
	
	@Override
	public void copy(IDiameter diameter) {
		this.a = diameter.getA();
		this.b = diameter.getB();
		
	}
	
}
