package propra22.q8493367.draw.view;



/**
 * The Interface for a draw panel event.
 */
public interface IDrawPanelEvent {

	/**
	 * Gets the type of the draw panel event
	 *
	 * @return the type
	 */
	DrawPanelEventType getType();
	
	/**
	 * Returns the source of the draw panel event
	 *
	 * @return the source of the draw panel event
	 */
	Object getSource();
	
	/**
	 * Returns the x coordinate of the mouse
	 *
	 * @return the x coordinate of the mouse
	 */
	int getMouseX();
	
	/**
	 * Returns the y coordinate of the mouse
	 *
	 * @return the y coordinate of the mouse
	 */
	int getMouseY();
	

	/**
	 * Gets the total scale which is the product of the scale and the panel scale of the view.
	 * This value is  needed to transfer distances from the view coordinate system into the 
	 * model coordinate system.
	 *
	 * @return the total scale
	 */
	double getTotalScale();


}
