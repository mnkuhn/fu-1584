package propra22.q8493367.point;

public interface IPoint extends Comparable<IPoint> {
    
	public int getX();
	public int getY();
	public void translate(int dx, int dy);
	public int compareTo(IPoint p);
	public String toString();
	
}
