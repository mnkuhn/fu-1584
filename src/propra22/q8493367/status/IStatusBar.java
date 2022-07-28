package propra22.q8493367.status;


// TODO: Auto-generated Javadoc
/**
 * The Interface IStatusBar provides methods
 * to display status information in the status bar.
 */
public interface IStatusBar {

	/**
	 * Sets the number of points in the point set
	 * on the status bar.
	 *
	 * @param number the number of points in the point set
	 * 
	 */
	void setNumberOfPoints(String number);
     
	
	/**
	 * Sets the mouse coordinates
	 *
	 * @param x the x coordinate of the mouse
	 * @param y the y coordinate of the mouse
	 */
	void setMouseCoordinates(String x, String y);


	/**
	 * Sets the coordinates of selected point.
	 *
	 * @param x the x coordinate of the selected point
	 * @param y the y coordinate of the selected point
	 */
	void setCoordindatesOfSelectedPoint(String x, String y);


}
