package propra22.q8493367.draw.view;

import java.awt.Graphics;

public class DrawPanelEvent implements IDrawPanelEvent {
	
	private DrawPanelEventType type;private Object source;
	private int mouseX;
	private int mouseY;
	private Graphics g;
	
	public DrawPanelEvent(DrawPanelEventType type, Object source, int mouseX, int mouseY, Graphics g) {
		this.type = type;
		this.source = source;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.g = g;
		
	}
    
	@Override
	public DrawPanelEventType getType() {
		return type;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public int getMouseX() {
		return mouseX;
	}

	@Override
	public int getMouseY() {
		return mouseY;
	}
	
	@Override
	public Graphics getGraphicsObject() {
		return g;
	}

}
