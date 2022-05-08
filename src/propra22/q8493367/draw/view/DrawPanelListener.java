package propra22.q8493367.draw.view;

import propra22.q8493367.draw.controller.IDrawPanelController;
import propra22.q8493367.point.Point;

public class DrawPanelListener implements IDrawPanelListener {
	
	private IDrawPanelController controller;
	
	public DrawPanelListener(IDrawPanelController controller) {
		this.controller = controller;
	}
	
	public void drawPanelEventOccured(IDrawPanelEvent e) {
		DrawPanelEventType type = e.getType();
		switch (type) {
		case INSERT_POINT: {
			controller.insertPointToModel(new Point(e.getMouseX(), e.getMouseY()));
			controller.updateModel();
			controller.updateView();
			break;
		}
		case DELETE_POINT: {
			controller.deletePointFromModel(e.getMouseX(), e.getMouseY());
			controller.updateModel();
			controller.updateView();
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
		case PAINT: {
			controller.paintDrawPanel(e.getGraphicsObject());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + e);
		}
	}
}
