package propra22.q8493367.draw.view;






// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving IDrawPanel events.
 * The class that is interested in processing a IDrawPanel
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIDrawPanelListener<code> method. When
 * the IDrawPanel event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IDrawPanelEvent
 */
public interface IDrawPanelListener {
	
	
	/**
	 * Is invoked, when a draw panel event
	 * has occured.
	 *
	 * @param e - the draw panel event
	 */
	public void drawPanelEventOccured(IDrawPanelEvent e);	
}
