package propra22.q8493367.util;




/**
 * The Class DrawPanelEvent specifies all events which are generated 
 * by the user on the draw panel.
 */
public class DrawPanelEvent {
	
	/**  The type of the draw panel event. */
	private DrawPanelEventType type;
	
	/** The source of the draw panel event. */
    private Object source;
	
	/** The x coordinate of the mouse pointer in the model coordinate system. */
	private int mouseX;
	
	/**  The y coordinate of the mouse pointer in the model coordinate system.. */
	private int mouseY;
	 
	
	/** The total scale which is the product of the the panel scale. Theses are 
	 * attributes of the draw panel. They are needed to calculate distances in
	 * the coordinate system of the model.
	 */
	private double totalScale;
	
	/**
	 * Instantiates a new draw panel event.
	 *
	 * @param type  the type of the draw panel event
	 * @param source  the source object of the draw panel event
	 * @param mouseX  the x coordinate of the mouse pointer in the model coordinate system.
	 * @param mouseY  the y coordinate of the mouse pointer in the model coordinate system.
	 * @param totalScale the product of scale and panel scale. This parameter is needed for
	 * the calculation of distances in the model coordinate system.
	 */
	public DrawPanelEvent(DrawPanelEventType type, Object source, int mouseX, int mouseY, double totalScale) {
		this.type = type;
		this.source = source;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.totalScale = totalScale;
		
		
	}
    
	/**
	 * Returns the type of the draw panel event.
	 *
	 * @return the type of the draw panel event
	 */
	public DrawPanelEventType getType() {
		return type;
	}

	/**
	 * Returns the source of the draw panel event.
	 *
	 * @return the source of the draw panel event
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * Returns the x coordinate of the mouse.
	 *
	 * @return the x coordinate of the mouse
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * Returns the y coordinate of the mouse.
	 *
	 * @return the y coordinate of the mouse
	 */
	public int getMouseY() {
		return mouseY;
	}
     
	/**
	 * Returns the total scale which is the product of the scale
	 * and the panel scale. It is are needed to calculate distances in the 
	 * coordinate system of the model.
	 *
	 * @return the total scale
	 */
	public double getTotalScale() {
		
		return totalScale;
	}
	

}
