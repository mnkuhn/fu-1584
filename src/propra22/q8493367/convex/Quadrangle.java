package propra22.q8493367.convex;



import propra22.q8493367.math.Calc;
import propra22.q8493367.point.IPoint;

public class Quadrangle {
     
	private IPoint a;
	private IPoint b;
	private IPoint c;
	private IPoint d;

	public Quadrangle(IPoint a, IPoint b, IPoint c, IPoint d) {
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
	
	public long area() {
		return Calc.DFV(a, b, c) + Calc.DFV(a, c, d);
	}
}
