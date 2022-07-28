package propra22.q8493367.draw.controller;


// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about IDrawPanelController information.
 */
public interface IDrawPanelControllerObserver {

	
	/**
	 * This method is called when the observer is notified. In this application the
	 * controller of the status bar is the observer of the draw panel controller.
	 *
	 * @param numberOfPoints the number of points in the point set
	 * @param mouseX the mouse X
	 * @param mouseY the mouse Y
	 * @param selectedX the selected X
	 * @param selectedY the selected Y
	 */
	public void update(String numberOfPoints, String mouseX, String mouseY, String selectedX, String selectedY);
}
