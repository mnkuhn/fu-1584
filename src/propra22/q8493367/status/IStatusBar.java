package propra22.q8493367.status;


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

	void setCoordinates(String x, String y);


	void setSelected(String x, String y);


}
