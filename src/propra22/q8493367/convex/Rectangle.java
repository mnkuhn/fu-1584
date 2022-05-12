package propra22.q8493367.convex;



import propra22.q8493367.point.IPoint;

public class Rectangle {
     
	private IPoint a;
	private IPoint b;
	private IPoint c;
	private IPoint d;

	public Rectangle(IPoint a, IPoint b, IPoint c, IPoint d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public IPoint getA() {
		return a;
	}

	public IPoint getB() {
		return b;
	}

	public IPoint getC() {
		return c;
	}

	public IPoint getD() {
		return d;
	}
}
