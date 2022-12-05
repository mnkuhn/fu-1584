package controllers;



/**
 * The Interface for the observer of a draw panel controller. It provides 
 * an update method. In this application, the observer pattern is used to 
 * notify the controller of the status bar.
 * 
 * @see <a href="https://www.youtube.com/watch?v=_BpmfnqjgzQ&t=662s">Explanation for the observer pattern on youtube</a>
 */
public interface IDrawPanelControllerObserver {

	
	/**
	 * This method is called when the observer is notified. In this application
	 * the observer of the draw panel is the controller of the status bar. All
	 * coordinates refer to the coordinate system of the model.
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
