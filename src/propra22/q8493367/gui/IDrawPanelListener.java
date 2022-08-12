package propra22.q8493367.gui;

import propra22.q8493367.usecases.DrawPanelEvent;

/**
 * The listener interface for receiving drawPanel events
 * produced by the user by changing the point set or moving 
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
