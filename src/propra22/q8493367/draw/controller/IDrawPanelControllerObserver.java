package propra22.q8493367.draw.controller;



/**
 * This Interface declares the update method for a draw panel controller observer.
 */
public interface IDrawPanelControllerObserver {

	
	/**
	 * This method is called when the observer is notified. In this application the
	 * controller the observer of the draw panel controller is the controller of the status bar.
	 * @param numberOfPoints the number of points in the point set
	 * @param mouseX the x coordinate of the mouse. The empty String if no mouse pointer is 
	 * located over the draw panel.
	 * @param mouseY the y coordinate of the mouse. The empty String if no mouse pointer is 
	 * located over the draw panel.
	 * @param selectedX the x coordinate of the selected point. The empty String if no point 
	 * is selected.
	 * @param selectedY the y coordinate of the selected point. The empty String if no point 
	 * is selected.
	 */
	public void update(String numberOfPoints, String mouseX, String mouseY, String selectedX, String selectedY);
}
