package propra22.q8493367.animation;

import java.util.List;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.Hull;
import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;

public class TangentPair implements ITangentPair {
    private double angle;
    private double diff = Math.PI / 1000;
	private Tangent tangent1;
	private Tangent tangent2;
	private float length;
	
	
	
	public TangentPair() {
		angle = 0;
		length = 120;
		
	}
	
	@Override
	public void initialize(IHull hull){
		// each tangent gets the hull ? 
		tangent1 = new Tangent(hull, 0, angle);
		tangent2 = new Tangent(hull, hull.getIndexOfRightMostPoint(), Math.PI);
		
	}
	
	private boolean nextAngleOfTangentIsValid(Tangent tangent) {
		return tangent.nextAngleIsValid();
	}
	
	@Override
	public void step() {
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
		if(!nextAngleOfTangentIsValid(tangent1)) {
			tangent1.stepToPreviousHullPoint();
		}
		if(!nextAngleOfTangentIsValid(tangent2)) {
			tangent2.stepToPreviousHullPoint();
		}	increaseAngle();
	}
	
	@Override
	public IPoint[] getTangent1(){
		return new IPoint[]{tangent1.getA(), tangent1.getCenter(),  tangent1.getB()};
	}
	
	@Override
	public IPoint[] getTangent2() {
		return new IPoint[]{tangent2.getA(), tangent2.getCenter(),  tangent2.getB()};
	}
	
	
	private void increaseAngle() {
		angle += diff;
		
	}
	
	@Override
	public void setLength(float length) {
		this.length = length;
	}


	private class Tangent  {
		private volatile List<IPoint> hullAsList;
		private int centerIndex;
        double angleOffset;
		
		public Tangent(IHull hull, int centerIndex, double angleOffset) {
			hull.createList();
			System.out.println("Vor hullAsList: " + Thread.currentThread().getName());
			this.hullAsList = hull.toList();
			this.centerIndex = centerIndex;
			this.angleOffset = angleOffset;
		}
		
		public void stepToPreviousHullPoint() {
			centerIndex = Math.floorMod(centerIndex - 1, hullAsList.size());
			
		}

		public boolean nextAngleIsValid() {
			IPoint center = getCenter();
			IPoint nextB = getNextB();
			IPoint previousHullPoint =  getPreviousHullPoint();
			long result = Point.signedTriangleArea(getCenter(), getNextB(), getPreviousHullPoint());
			return result <= 0;
		}

		
		private int getCenterIndex() {
			return centerIndex;
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
		
		private IPoint getNextB() {
			int x = (int)Math.round(getCenter().getX() + length/2 * Math.sin(angle + angleOffset + diff));
			int y = (int)Math.round(getCenter().getY() - length/2 * Math.cos(angle + angleOffset + diff));
			return new Point(x, y);
		}
		
		private IPoint getNextA() {
			int x = (int)Math.round(getCenter().getX() - length/2 * Math.sin(angle + angleOffset + diff));
			int y = (int)Math.round(getCenter().getY() + length/2 * Math.cos(angle + angleOffset + diff));
			return new Point(x, y);
		}
		
		private IPoint getNextHullPoint() {
			return hullAsList.get((centerIndex + 1) % hullAsList.size());
		}
		
		private IPoint getPreviousHullPoint() {
			int index = Math.floorMod(centerIndex - 1, hullAsList.size());
			return hullAsList.get(index);
		}
		
	}
	public static void main(String[] args) {
		IHull hull = new Hull();
		hull.addPointToSection(new Point(2, 2), SectionType.LOWERLEFT);
		hull.addPointToSection(new Point(3, 1), SectionType.LOWERLEFT);
		hull.addPointToSection(new Point(4, 2), SectionType.LOWERRIGHT);
		hull.addPointToSection(new Point(3, 1), SectionType.LOWERRIGHT);
		hull.addPointToSection(new Point(4, 2), SectionType.UPPERRIGHT);
		hull.addPointToSection(new Point(3, 3), SectionType.UPPERRIGHT);
		hull.addPointToSection(new Point(2, 2), SectionType.UPPERLEFT);
		hull.addPointToSection(new Point(3, 3), SectionType.UPPERLEFT);
		
		TangentPair tangentPair = new TangentPair();
		
		
		while(true) {
			try {
				tangentPair.step();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
