package propra22.q8493367.main;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewEvent represents a user input determining
 * which of the data (convex hull, diameter, quadrangle, triangle) should be displayed on the draw panel.
 */
public class ViewEvent  {

	private Object source;
	private boolean showConvexHull;
	private boolean showDiameter;
	private boolean showQuadrangle;
	private boolean showTriangle;

	/**
	 * Instantiates a new view event.
	 *
	 * @param source - the source
	 * @param showConvexHull - true, if the convex hull is to be shown, false otherwise.
	 * @param showDiameter - true, if the diameter is to be shown, false otherwise.
	 * @param showQuadrangle - true, if the quadrangle is to be shown, false otherwise.
	 * @param ShowTriangle - true, if the triangle is to be shown, false otherwise.
	 */
	public ViewEvent(Object source, boolean showConvexHull, boolean showDiameter, boolean showQuadrangle, boolean ShowTriangle) {
		this.source = source;
		this.showConvexHull = showConvexHull;
		this.showDiameter = showDiameter;
		this.showQuadrangle = showQuadrangle;
		this.showTriangle = ShowTriangle;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}
    
	
	/**
	 * Returns whether the convex hull is to be shown.
	 *
	 * @return true, if the convex hull is to be shown, false otherwise.
	 */
	public boolean convexHullIsDisplayed() {
		return showConvexHull;
	}
	
	/**
	 * Returns whether the diameter is to be shown.
	 *
	 * @return true, if the diameter is to be shown, false otherwise.
	 */
	public boolean DiameterIsDisplayed() {
		return showDiameter;
	}
	
	/**
	 * Returns whether the quadrangle is to be shown.
	 *
	 * @return true, if the quadrangle is to be shown, false otherwise.
	 */
	public boolean QuadrangleIsDisplayed() {
		return showQuadrangle;
	}

	/**
	 * Returns whether the triangle is to be shown.
	 *
	 * @return true, if the triangle is to be shown, false otherwise.
	 */
	public boolean TriangleIsDisplayed() {
		return showTriangle;
	}
}
