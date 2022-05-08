package propra22.q8493367.draw.view;

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

}
