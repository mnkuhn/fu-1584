package propra22.q8493367.draw.controller;


/**
 * An asynchronous update interface for receiving notifications
 * about IDrawPanelController information.
 */
public interface IDrawPanelControllerObserver {

	/**
	 * This method is called, when the observer is notified.
	 *
	 * @param number the number of points in the point set
	 */
	public void update(int number);
}
