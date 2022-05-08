package propra22.q8493367.draw.view;

import java.awt.Graphics;

public interface IDrawPanelEvent {

	DrawPanelEventType getType();
	Object getSource();
	int getMouseX();
	int getMouseY();
	Graphics getGraphicsObject();

}
