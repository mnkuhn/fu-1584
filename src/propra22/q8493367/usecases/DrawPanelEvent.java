package propra22.q8493367.usecases;


/**
 * The Class DrawPanelEvent specifies all events which are generated 
 * by the user on the draw panel.
 */
public class DrawPanelEvent {
	
	/**  The type of the draw panel event. */
	private DrawPanelEventType type;
	
	/** The source of the draw panel event. */
    private Object source;
	
	/** The x coordinate of the mouse pointer in the coordinate system of the model. */
	private int mouseX;
	
	/**  The y coordinate of the mouse pointer in the coordinate system of the model. */
	private int mouseY;
	 
	
	/** The total scale which is the product of the scale and the panel scale. Theses are 
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
	 * @param totalScale needed for the translation of distances from the model 
	 * coordinate system into the view coordinate system. It is the scale of the representation in
	 * the draw panel.
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
	 * Returns the x coordinate of the mouse pointer in the model coordinate system.
	 *
	 * @return the x coordinate of the mouse pointer in the model coordinate system.
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * Returns the y coordinate of the mouse pointer in the model coordinate system.
	 *
	 * @return the y coordinate of the mouse pointer in the model coordinate system.
	 */
	public int getMouseY() {
		return mouseY;
	}
     
	/**
	 * Returns the total scale which is the product of the scale
	 * and the panel scale. This value is needed to transfer distances from
	 * the coordinate system of the view into the coordinate system of the
	 * model.
	 *
	 * @return the total scale
	 */
	public double getTotalScale() {
		
		return totalScale;
	}
	

}
