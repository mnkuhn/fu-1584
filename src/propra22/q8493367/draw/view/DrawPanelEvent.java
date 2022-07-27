package propra22.q8493367.draw.view;



// TODO: Auto-generated Javadoc
/**
 * The Class DrawPanelEvent specifies all events which are induced 
 * on the draw panel.
 */
public class DrawPanelEvent implements IDrawPanelEvent {
	
	/**  The type of the draw panel event. */
	private DrawPanelEventType type;
	/** The source. */
    private Object source;
	
	/** The x coordinate of the mouse. */
	private int mouseX;
	
	/**  The y coordinate of the mouse. */
	private int mouseY;
	 
	
	/** The total scale which is the product of the scale and the panel scale. */
	private double totalScale;
	
	/**
	 * Instantiates a new draw panel event.
	 *
	 * @param type  the type of the draw panel event
	 * @param source  the source object of the draw panel event
	 * @param mouseX  the x coordinate of the mouse
	 * @param mouseY  the y coordinate of the mouse
	 * @param totalScale the product of scale and panel scale. This parameter is needed for....
	 */
	public DrawPanelEvent(DrawPanelEventType type, Object source, int mouseX, int mouseY, double totalScale) {
		this.type = type;
		this.source = source;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.totalScale = totalScale;
		
		
	}
    
	/**
	 * Gets the type of the event.
	 *
	 * @return the type of the event
	 */
	@Override
	public DrawPanelEventType getType() {
		return type;
	}

	/**
	 * Gets the source of the event.
	 *
	 * @return the source of the event
	 */
	@Override
	public Object getSource() {
		return source;
	}

	/**
	 * Gets the x coordinate of the mouse.
	 *
	 * @return the x coordinate of the mouse
	 */
	@Override
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * Gets the y coordinate of the mouse.
	 *
	 * @return the y coordinate of the mouse
	 */
	@Override
	public int getMouseY() {
		return mouseY;
	}
     
	/**
	 * Gets the total scale which is the product of the scale
	 * and the panel scale. This method is needed for...
	 *
	 * @return the total scale
	 */
	@Override
	public double getTotalScale() {
		
		return totalScale;
	}
	

}
