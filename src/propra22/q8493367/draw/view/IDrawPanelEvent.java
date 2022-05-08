package propra22.q8493367.draw.view;

import java.awt.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Interface for all draw panel events
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
	 * Returns the graphics object
	 *
	 * @return the graphics object
	 */
	Graphics getGraphicsObject();

}
