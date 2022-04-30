package propra22.q8493367.point;

public class PointEvent {

	private Object source;
	private int x;
	private int y;

	public PointEvent(Object source, int x, int y) {
		this.source = source;
		this.x = x;
		this.y = y;
	}

	public Object getSource() {
		return source;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
