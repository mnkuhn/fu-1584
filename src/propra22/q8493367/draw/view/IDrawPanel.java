package propra22.q8493367.draw.view;

import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * 
 * IDrawPanel is the interface for the view which shows the points and the 
 * convex hull etc.
 *
 */

public interface IDrawPanel  {
	
	/**
	 * sets the draw panel listener.
	 *
	 * @param drawPanelListener - the draw panel listener
	 */
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener);
	
	/**
	 * updates the draw panel.
	 */
	public void update();
    
	/**
	 * Determines whether the convex hull is shown or not.
	 *
	 * @param convexHullIsShown the new convex hull is shown
	 */
	void setConvexHullIsShown(boolean convexHullIsShown);
    
	/**
	 * Determines whether the diameter is shown or not.
	 *
	 * @param diameterIsShown the new diameter is shown
	 */
	void setDiameterIsShown(boolean diameterIsShown);
    
	/**
	 * Determines whether the quadrangle is shown or not.
	 *
	 * @param quadrangleIsShown the new quadrangle is shown
	 */
	void setQuadrangleIsShown(boolean quadrangleIsShown);
    
	/**
	 * Determines whether the triangle is shown or not.
	 *
	 * @param b the new triangle is shown
	 */
	void setTriangleIsShown(boolean b);
	
	
	/**
	 * Sets the show animation.
	 *
	 * @param b the new show animation
	 */
	void setShowAnimation(boolean b);

	/**
	 * Returns true, if the convex hull is shown,
	 * false otherwise.
	 *
	 * @return true, if the convex hull is shown
	 */
	boolean convexHullIsShown();
    
	/**
	 * Returns true, if the diameter is shown,
	 * false otherwise.
	 *
	 * @return true, if the diameter is shown
	 */
	boolean diameterIsShown();
    
	/**
	 * Returns true, if the quadrangle is shown,
	 * false otherwise.
	 *
	 * @return true, if the quadrangle is shown.
	 */
	boolean quadrangleIsShown();
    
	/**
	 * Returns true, if the animation is running or false
	 * otherwise.
	 *
	 * @return true, if the animation is running.
	 */
	boolean animationIsShown();
	
    
	/**
	 * Initializes the draw panel when a point set
	 * is loaded from file. The inner offsets for x and y coordinates
	 * and the scale are calculated so that the point set is displayed
	 * centered on the draw panel.
	 */
	void center();

	/**
	 * Gets the view point translated to model point.
	 *
	 * @param point the point
	 * @return the view point translated to model point
	 */
	public IPoint getViewPointTranslatedToModelPoint(IPoint point);

	/**
	 * Returns true, if the triangle is shown,
	 * false otherwise.
	 *
	 * @return true, if the triangle is shown.
	 */
	public boolean triangleIsShown();

	
   
}
