package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

public class Diameter {
	private IPoint a;
	private IPoint b;
	
	public Diameter() {
		this.a = null;
		this.b = null;
	}
	
	public Diameter(IPoint a, IPoint b) {
		this.a = a;
		this.b = b;
	}
	
	public IPoint getA() {
		return a;
	}
	
	public IPoint getB() {
		return b;
	}

	public void setA(IPoint a) {
		this.a = a;
		
	}

	public void setB(IPoint b) {
		this.b = b;
		
	}
	
	// Länge des Diameters gehört hier hin
}
