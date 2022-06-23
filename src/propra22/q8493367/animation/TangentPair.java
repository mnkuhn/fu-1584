package propra22.q8493367.animation;

import java.util.List;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class TangentPair implements ITangentPair {
    private double angle;
    private final double diff = Math.PI / 500;
	private Tangent tangent1;
	private Tangent tangent2;
	private float length;
	private List<IPoint> hullAsList;
	
	
	
	public TangentPair() {
		angle = 0;
		length = 400;
		
	}
	
	@Override
	public void initialize(IHull hull){
		this.angle = 0;
		this.hullAsList = hull.toList();
		tangent1 = new Tangent(0, 0);
		tangent2 = new Tangent(hull.getIndexOfRightMostPoint(), Math.PI);	
	}
	
	private boolean nextAngleOfTangentIsValid(Tangent tangent) {
		return tangent.nextAngleIsValid();
	}
	
	@Override
	public void step() {
		
		if(nextAngleOfTangentIsValid(tangent1) && nextAngleOfTangentIsValid(tangent2)){
			increaseAngle();
		}
		else {
			while(!nextAngleOfTangentIsValid(tangent1)) {
				tangent1.stepToPreviousHullPoint();
				}
			
			while(!nextAngleOfTangentIsValid(tangent2)) {
				tangent2.stepToPreviousHullPoint();
			}
			increaseAngle();
		}

	}
	
	private void increaseAngle() {
		angle += diff;
	}

	@Override
	public IPoint[] getTangent1(){
		return new IPoint[]{tangent1.getA(), tangent1.getCenter(),  tangent1.getB()};
	}
	
	@Override
	public IPoint[] getTangent2() {
		return new IPoint[]{tangent2.getA(), tangent2.getCenter(),  tangent2.getB()};
	}
	
	
	@Override
	public void setLength(float length) {
		this.length = length;
	}


	private class Tangent{
		private int centerIndex;
        double angleOffset;
		
		public Tangent(int centerIndex, double angleOffset) {
			
			this.centerIndex = centerIndex;
			this.angleOffset = angleOffset;
		}
		
		private void stepToPreviousHullPoint() {
			centerIndex = Math.floorMod(centerIndex - 1, hullAsList.size());
			
		}
		
		
   
		private boolean nextAngleIsValid() {
			long result = Point.signedTriangleArea(getCenter(), getNextB(diff), getPreviousHullPoint());
			return result < 0;
		}

		
	  
		
		
		public IPoint getCenter() {
			return hullAsList.get(centerIndex % hullAsList.size());
		}

	
		
		public float getLength() {
			return length;
		}

		
		
		
		
		
		public IPoint getA() {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		} 
		
		public IPoint getB() {
		
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset));
			return new Point(x, y);
		}
		
		
		
		private IPoint getNextB(double pDiff) {
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		private IPoint getNextA(double pDiff) {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset + pDiff));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset + pDiff));
			return new Point(x, y);
		}
		
		private IPoint getNextHullPoint() {
			
			return hullAsList.get(Math.floorMod(centerIndex + 1, hullAsList.size()));
		}
		
		private IPoint getPreviousHullPoint() {
			int index = Math.floorMod(centerIndex - 1, hullAsList.size());
			return hullAsList.get(index);
		}	
	}
}
