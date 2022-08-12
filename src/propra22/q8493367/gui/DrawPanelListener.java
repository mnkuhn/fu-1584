package propra22.q8493367.gui;

import propra22.q8493367.controllers.IDrawPanelController;
import propra22.q8493367.usecases.DrawPanelEvent;
import propra22.q8493367.usecases.DrawPanelEventType;



/**
 * The listener for receiving drawPanel events
 * produced by the user by changing the point set or moving 
 * the mouse.
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
	
	@Override
	public void drawPanelEventOccurred(DrawPanelEvent e) {
		DrawPanelEventType type = e.getType();
		switch (type) {
		case INSERT_POINT: {
			controller.insertPointToPointSetByCommand(e.getMouseX(), e.getMouseY());
			break;
		}
		case DELETE_POINT: {
			controller.deletePointFromPointSetByCommand(e.getMouseX(), e.getMouseY(), e.getTotalScale());
			break;
		}
		case DRAG_POINT_INITIALIZED: {
			controller.initializePointDrag(e.getMouseX(), e.getMouseY());
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

		case MOUSE_MOVED: {
			controller.updateMouseData(e.getMouseX(), e.getMouseY(), e.getTotalScale());
			break;
		}

		case MOUSE_EXCITED: {
			controller.setMousePositionIsOverPanel(false);
			break;
		}

		case MOUSE_ENTERED: {
			controller.setMousePositionIsOverPanel(true);
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + e);
		}
	}
}
