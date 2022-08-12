package propra22.q8493367.controllers;

/**
 * The controller for the status bar. It implements the method update
 * which updates the status bar. The updates method gets the informations
 * displayed on he status bar as arguments.
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
	
	@Override
	public void update(String numberOfPoints, String mouseX, String mouseY, String selectedX, String selectedY) {
		statusBar.setNumberOfPoints(numberOfPoints);	
		statusBar.setMouseCoordinates(mouseX, mouseY);
		statusBar.setCoordindatesOfSelectedPoint(selectedX, selectedY);
	}
}
