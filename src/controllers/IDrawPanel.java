package controllers;

import entities.Point;
import gui.IDrawPanelListener;

/**
 * 
 * The Interface for the draw panel. The draw panel is responsible
 * for displaying the points of the point set, the convex hull, the 
 * diameter, the quadrangle, the triangle and the animation. 
 *
 */

public interface IDrawPanel  {
	
	/**
	 * Sets the draw panel listener. The draw panel listener
	 * listens for all events produced by the user. These 
	 * events may change the point set or the representation
	 * of the view due to zooming or panning.
	 *
	 * @param drawPanelListener the draw panel listener
	 */
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener);
	
	/**
	 * Updates the draw panel.
	 */
	public void update();
    
	/**
	 * Determines whether the convex hull is shown or not.
	 *
	 * @param b true, if the convex hull is to be shown, false
	 * otherwise.
	 */
	void setConvexHullIsShown(boolean b);
    
	/**
	 * Determines whether the diameter is shown or not.
	 *
	 * @param b true, if the diameter is to be shown,
	 * false otherwise.
	 */
	void setDiameterIsShown(boolean b);
	
	/**
	 * Determines whether the triangle is shown or not.
	 *
	 * @param b true, if the triangle is to be shown,
	 * false otherwise.
	 */
	void setTriangleIsShown(boolean b);
    
	/**
	 * Determines whether the quadrangle is shown or not.
	 *
	 * @param b true, if the quadrangle is to be shown,
	 * false otherwise.
	 */
	void setQuadrangleIsShown(boolean b);
    
	
	/**
	 * Determines, whether the animation is shown or not.
	 *
	 * @param b true, if the diameter is to be shown,
	 * false otherwise.
	 */
	void setShowAnimation(boolean b);

	/**
	 * Returns true, if the convex hull is shown,
	 * false otherwise.
	 *
	 * @return true, if the convex hull is shown,
	 * false otherwise.
	 */
	boolean convexHullIsShown();
    
	/**
	 * Returns true, if the diameter is shown or
	 * false otherwise.
	 *
	 * @return true, if the diameter is shown,
	 * false otherwise.
	 */
	boolean diameterIsShown();
    
	/**
	 * Returns true, if the quadrangle is shown or
	 * false otherwise.
	 *
	 * @return true, if the quadrangle is shown,
	 * false otherwise.
	 */
	boolean quadrangleIsShown();
	
	/**
	 * Returns true, if the triangle is shown,
	 * false otherwise.
	 *
	 * @return true, if the triangle is shown.
	 */
	public boolean triangleIsShown();
    
	/**
	 * Returns true, if the animation is shown or
	 * false otherwise.
	 *
	 * @return true, if the animation is shown,
	 * false otherwise.
	 */
	boolean animationIsShown();
	
    
	/**
	 * Centers the representation on the draw panel.
	 */
	void center();

	/**
	 * Returns a new point with the translated coordinates from 
	 * the point p. The coordinates are translated from the 
	 * view coordinate system to the model coordinate system.
	 *
	 * @param p the point whose coordinates are translated.
	 * @return the view point translated to model point
	 */
	public Point translatePointFromViewToModel(Point p);

	

	
   
}
