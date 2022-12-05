package gui;

import usecases.DrawPanelEvent;

/**
 * The listener Interface for receiving drawPanel events
 * produced by the user by inserting, deleting or dragging points or by moving 
 * the mouse.
 *
 */
public interface IDrawPanelListener {
	
	
	/**
	 * This method is invoked, when a draw panel event has occurred.
	 *
	 * @param e the draw panel event
	 */
	public void drawPanelEventOccurred(DrawPanelEvent e);	
}
