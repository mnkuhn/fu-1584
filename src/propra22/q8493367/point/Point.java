package propra22.q8493367.point;

public class Point  implements IPoint {

	private int x;
	private int y;
	
	public Point(int x, int y)  {
		 this.x = x;
		 this.y = y;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	
	
	public void translate(int dx, int dy) {
		x = x + dx;
		y = y + dy;
	}
	
	

	@Override
	public int compareTo(IPoint p) {
		if(this.x < p.getX()) {return -1;}
		 else if (this.x > p.getX()) {return 1;}
		 else if (this.y > p.getY()) {return -1;}
		 else if (this.y < p.getY()) {return 1;}
		 else {return 0;}
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append(" ");
		builder.append(y);
		return builder.toString();
	}
	
}


