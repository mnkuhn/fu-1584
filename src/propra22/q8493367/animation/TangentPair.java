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
		angle = 0;
		//System.out.println("vor createList Thread: " + Thread.currentThread().getName());
		//hull.createList();
		//System.out.println("create List ist fertig in initialize");
		this.hullAsList = hull.toList();
		tangent1 = new Tangent(0, 0);
		tangent2 = new Tangent(hull.getIndexOfRightMostPoint(), Math.PI);	
	}
	
	private boolean nextAngleOfTangentIsValid(Tangent tangent) {
		return tangent.nextAngleIsValid();
	}
	
	@Override
	public void step() {
		//System.out.println("TangetPair -> step Angle: " + angle);
		//System.out.println("Tangent1 A: " + tangent1.getA().getX() + ", " + tangent2.getA().getY());
		/*
		System.out.println("tangent1 index: " + tangent1.getCenterIndex() + " tangent2 index: " + tangent2.getCenterIndex());
		System.out.println("Common Angle: " + angle);
		System.out.println("tangent1 A: " + tangent1.getA());
		System.out.println("tangent1 B: " + tangent1.getB());
		
		System.out.println("tangent2 A: " + tangent2.getA());
		System.out.println("tangent2 B: " + tangent2.getB());
		System.out.println("\n");
		*/
		// if next angle is not valid change center
		
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
	
	
	private void findNewAngle() {
		double diff1 = diff;  
		while(Point.signedTriangleArea(tangent1.getCenter(), tangent1.getNextB(diff1), tangent1.getPreviousHullPoint()) > 0) {
			diff1 /= 2;
		}
		
		double diff2 = diff;
		while(Point.signedTriangleArea(tangent2.getCenter(), tangent2.getNextB(diff2), tangent2.getPreviousHullPoint()) > 0) {
			diff2 /= 2;
		}
		angle = diff1 <= diff2 ? angle + diff1 : angle + diff2;	
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
			/*
			IPoint center = getCenter();
			IPoint nextB = getNextB(diff);
			IPoint previousHullPoint =  getPreviousHullPoint();
			*/
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
