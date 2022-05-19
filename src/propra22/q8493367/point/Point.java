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

	@Override
	public IPoint add(IPoint p) {
		return new Point(this.x + p.getX(), this.y + p.getY());
	}

	@Override
	public IPoint subtract(IPoint p) {
		return new Point(this.x - p.getX(), this.y - p.getY());
	}

	@Override
	public boolean equals(IPoint p) {
		return (this.x == p.getX()) && (this.y == p.getY());
	}
	
	/*
	public static long DFV(IPoint a, IPoint b, IPoint c) {

		long summand1 = (long) a.getX() * ((long) b.getY() - (long) c.getY());
		long summand2 = (long) b.getX() * ((long) c.getY() - (long) a.getY());
		long summand3 = (long) c.getX() * ((long) a.getY() - (long) b.getY());
		// change the sign because (0, 0) is in the upper left corner
		return -(summand1 + summand2 + summand3);
	}
	*/
	
	
	
}


