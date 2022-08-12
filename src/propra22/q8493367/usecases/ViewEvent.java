package propra22.q8493367.usecases;


/**
 * An event indicating that the user has made a selection of
 * shapes to display.
 */
public class ViewEvent  {
    
	/** The source of the view event */
	private Object source;
	
	/** True, if user chose the convex hull to be shown, false otherwise */
	private boolean showConvexHull;
	
	/** True, if user chose the diameter to be shown, false otherwise */
	private boolean showDiameter;
	
	/** True, if user chose the quadrangle to be shown, false otherwise */
	private boolean showQuadrangle;
	
	/** True, if user chose the triangle to be shown, false otherwise */
	private boolean showTriangle;
	
	/** True, if user chose the animation to be shown, false otherwise */
	private boolean showAnimation;

	/**
	 * Instantiates a new view event.
	 *
	 * @param source the source
	 * @param showConvexHull true, if the convex hull is to be shown, false otherwise.
	 * @param showDiameter true, if the diameter is to be shown, false otherwise.
	 * @param showQuadrangle true, if the quadrangle is to be shown, false otherwise.
	 * @param showTriangle true, if the triangle is to be shown, false otherwise.
	 * @param showAnimation true, if the animation is to be shown, false otherwise. 
	 */
	public ViewEvent(Object source, boolean showConvexHull, boolean showDiameter, boolean showQuadrangle, boolean showTriangle, boolean showAnimation) {
		this.source = source;
		this.showConvexHull = showConvexHull;
		this.showDiameter = showDiameter;
		this.showQuadrangle = showQuadrangle;
		this.showTriangle = showTriangle;
		this.showAnimation = showAnimation;
	}

	/**
	 * Returns the source of the view event.
	 *
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}
    
	
	/**
	 * Returns true, if the convex hull is to be shown. 
	 * Returns false otherwise.
	 *
	 * @return true, if the convex hull is to be shown, false otherwise.
	 */
	public boolean convexHullIsDisplayed() {
		return showConvexHull;
	}
	
	/**
	 * Returns true, if the diameter is to be shown. 
	 * Returns false otherwise.
	 *
	 * @return true, if the diameter is to be shown, false otherwise.
	 */
	public boolean DiameterIsDisplayed() {
		return showDiameter;
	}
	
	/**
	 * Returns true, if the quadrangle is to be shown. 
	 * Returns false otherwise.
	 *
	 * @return true, if the quadrangle is to be shown, false otherwise.
	 */
	public boolean QuadrangleIsDisplayed() {
		return showQuadrangle;
	}

	/**
	 *Returns true, if the triangle is to be shown. 
	 * Returns false otherwise.
	 *
	 * @return true, if the triangle is to be shown, false otherwise.
	 */
	public boolean TriangleIsDisplayed() {
		return showTriangle;
	}

	/**
	 * Returns true, if the animation is to be shown. 
	 * Returns false otherwise.
	 *
	 * @return true, if the animation is to be shown, false otherwise.
	 */
	public boolean animationIsShown() {
		return showAnimation;
	}
}
