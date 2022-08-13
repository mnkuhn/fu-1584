package propra22.q8493367.controllers;



/**
 * The Interface for the status bar. It provides methods
 * to display status information in the status bar. With these methods
 * the number of points in the point set, the coordinates of the 
 * mouse pointer over the draw panel and the coordinates of the selected point 
 * can be displayed.
 */
public interface IStatusBar {

	/**
	 * Displays the number of points in the point set
	 * on the status bar.
	 *
	 * @param number the number of points in the point set
	 * 
	 */
	void setNumberOfPoints(String number);
     
	
	/**
	 * Displays the coordinates of the mouse pointer.
	 *
	 * @param x the x coordinate of the mouse pointer
	 * @param y the y coordinate of the mouse pointer
	 */
	void setMouseCoordinates(String x, String y);


	/**
	 * Displays the coordinates of the selected point.
	 *
	 * @param x the x coordinate of the selected point
	 * @param y the y coordinate of the selected point
	 */
	void setCoordindatesOfSelectedPoint(String x, String y);


}
