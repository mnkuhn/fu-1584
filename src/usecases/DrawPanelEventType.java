package usecases;

/**
 * The Enum DrawPanelEventType.
 */
public enum DrawPanelEventType {
		
	/** Insert a point into the point set. */
	INSERT_POINT, 
	/** Delete a point from the point set. */
	DELETE_POINT, 
	/** Dragging a point is initialized. */
	DRAG_POINT_INITIALIZED, 
	/** A point is dragged. */
	DRAG_POINT, 
	/** Dragging a point has ended. */
	DRAG_POINT_ENDED,  
	/** The mouse was moved. */
	MOUSE_MOVED, 
	/** The mouse has left the draw panel. */
	MOUSE_EXCITED, 
	/** The mouse has entered the draw panel. */
	MOUSE_ENTERED;
}
