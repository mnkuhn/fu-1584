package propra22.q8493367.draw.view;

import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

/**
 * 
 * IDrawPanel is the interface for the view which shows the points and the 
 * convex hull etc.
 *
 */

public interface IDrawPanel  {
	
	/**
	 * sets the draw panel listener
	 * @param drawPanelListener - the draw panel listener
	 */
	public void setDrawPanelListener(IDrawPanelListener drawPanelListener);
	
	/**
	 * updates the draw panel
	 */
	public void update();

	void setConvexHullIsShown(boolean convexHullIsShown);

	void setDiameterIsShown(boolean diameterIsShown);

	void setQuadrangleIsShown(boolean quadrangleIsShown);

	void setTriangleIsShown(boolean b);
	
	void setShowAnimation(boolean b);

	boolean convexHullIsShown();

	boolean diameterIsShown();

	boolean quadrangleIsShown();

	boolean animationIsShown();

	/**
	 * Updates the draw panel
	 */
	

	void initialize();

	public IPoint getViewPointTranslatedToModelPoint(IPoint point);

	public boolean triangleIsShown();

	
   
}
