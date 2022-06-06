package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Class Diameter.
 */
public class Diameter implements IDiameter {
	
	
	private IPoint a;
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
	
	
	@Override
	public IPoint getA() {
		return a;
	}
	
	
	@Override
	public IPoint getB() {
		return b;
	}
    
	
	@Override
	public void setA(IPoint a) {
		this.a = a;
		
	}
    
	
	@Override
	public void setB(IPoint b) {
		this.b = b;
		
	}

	
	@Override
	public double length() {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt( dx*dx + dy*dy );
	}
	
	@Override
	public int[][] asArray(){
		int[][] diameterArr = new int[2][2];
		diameterArr[0][0] = a.getX();
		diameterArr[0][1] = a.getY();
		diameterArr[1][0] = b.getX();
		diameterArr[1][1] = b.getY();
		return diameterArr;
	}
}
