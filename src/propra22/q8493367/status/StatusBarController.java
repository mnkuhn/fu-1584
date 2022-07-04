package propra22.q8493367.status;

import propra22.q8493367.draw.controller.IDrawPanelControllerObserver;


/**
 * The controller for the stauts bar.
 */
public class StatusBarController implements IDrawPanelControllerObserver{
	
	/** The status bar. */
	IStatusBar statusBar;
	
	/**
	 * Instantiates a new status bar controller.
	 *
	 * @param statusBar the status bar
	 */
	public StatusBarController(IStatusBar statusBar)  {
		this.statusBar = statusBar;
	}
	
	/**
	 * Updates the status bar
	 *
	 * @param number the number of points in the point set
	 */
	@Override
	public void update(int number) {
		statusBar.setNumberOfPoints(number);	
	}
}
