package propra22.q8493367.draw.model;


import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class Quadrangle represents a quadrangle.
 */
public class Quadrangle implements IQuadrangle {
     
	
	/** The first of the four points of the quadrangle*/
	private IPoint a;
	
	/** The second of the four points of the quadrangle*/
	private IPoint b;
	
	/** The third of the four points of the quadrangle*/
	private IPoint c;
	
	/** The fourth of the four points of the quadrangle*/
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
	 *{@inheritDoc}
	 */
	@Override
	public IPoint getA() {return a;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public IPoint getB() {return b;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public IPoint getC() {return c;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public IPoint getD() {return d;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setA(IPoint a) {this.a = a;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setB(IPoint b) {this.b = b;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setC(IPoint c) {this.c = c;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public void setD(IPoint d) {this.d = d;}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public long area() {
		if(a != null && b != null && c != null && d != null) {
			return Point.signedTriangleArea(a, b, c) + Point.signedTriangleArea(a, c, d);
		}
		else {
			return -1;
		}	
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public int [][] asArray(){
		int[][] quadrangleAsArray = new int[4][2];
		
		quadrangleAsArray[0][0] = a.getX();
		quadrangleAsArray[0][1] = a.getY();
		
		quadrangleAsArray[1][0] = b.getX();
		quadrangleAsArray[1][1] = b.getY();
		
		quadrangleAsArray[2][0] = c.getX();
		quadrangleAsArray[2][1] = c.getY();
		
		quadrangleAsArray[3][0] = d.getX();
		quadrangleAsArray[3][1] = d.getY();
		
		return quadrangleAsArray;
	}
}
