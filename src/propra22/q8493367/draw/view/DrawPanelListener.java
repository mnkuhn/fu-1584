package propra22.q8493367.draw.view;

import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.point.Point;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving drawPanel events.
 * The class that is interested in processing a drawPanel
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDrawPanelListener<code> method. When
 * the drawPanel event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DrawPanelEvent
 */
public class DrawPanelListener implements IDrawPanelListener {
	
	/** The draw panel controller. */
	private IDrawPanelController controller;
	
	/**
	 * Instantiates a new draw panel listener.
	 *
	 * @param controller the controller
	 */
	public DrawPanelListener(IDrawPanelController controller) {
		this.controller = controller;
	}
	
	/**
	 * Draw panel event occured.
	 *
	 * @param e - the e draw panel event
	 */
	public void drawPanelEventOccured(IDrawPanelEvent e) {
		DrawPanelEventType type = e.getType();
		switch (type) {
		case INSERT_POINT: {
			controller.insertPointToPointSetByUserInput(e.getMouseX(), e.getMouseY());
			break;
		}
		case DELETE_POINT: {
			controller.deletePointFromPointSetByUserInput(e.getMouseX(), e.getMouseY(), e.getTotalScale());
			break;
		}
		case DRAG_POINT_INITIALIZED: {
			controller.initializePointDrag(e.getMouseX(), e.getMouseY(), e.getTotalScale());
			break;
		}
		case DRAG_POINT: {
			controller.dragPoint(e.getMouseX(), e.getMouseY());
			break;	
		}
		case DRAG_POINT_ENDED: {
			controller.terminatePointDrag(e.getMouseX(), e.getMouseY());
			break;
		}
		/*
		case PAINT: {
			controller.paintDrawPanel(e.getGraphicsObject());
			break;
		}
		*/
		default:
			throw new IllegalArgumentException("Unexpected value: " + e);
		}
	}
}
