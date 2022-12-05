package entities;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class QuadrangleSequence represents a sequence of quadrangles
 * as used for the animation. It arises from the calculation of the antipodal 
 * pairs according to the specification.
 */
public class QuadrangleSequence {
	
	/** The quadrangles */
	List<Quadrangle> quadrangles = new ArrayList<>();
	
	/** The index of the current quadrangle*/
	int index;
	
	/**
	 * Returns the hull point before the quadrangle point identified
	 * by the argument moving counterclockwise in a standard
	 * cartesian coordinate system.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the convex hull before the quadrangle point 
	 */
	public Point getHullPointBefore(QuadranglePoint quadranglePoint) {
		if(quadrangles.size() == 0) {return null;}
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			Point point = quadrangles.get(tmpIndex).getA();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getA()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}	
			}
			return quadrangles.get(tmpIndex).getA();
		}

		case B: {
			Point point = quadrangles.get(tmpIndex).getB();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getB()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getB();	
		}

		case C: {
			Point point = quadrangles.get(tmpIndex).getC();
			if(quadrangles.size() > 1){
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getC()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			Point point = quadrangles.get(tmpIndex).getD();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getD()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getD();

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + quadranglePoint);
		}

	}	
	
	/**
	 * Returns the point of the quadrangle identified by the argument.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the quadrangle identified by the argument.
	 */
	public Point getHullPoint(QuadranglePoint quadranglePoint) {
		if(quadrangles.size() == 0) {return null;}
		switch (quadranglePoint) {
		case A: {
			return quadrangles.get(index).getA();
		}
		
		case B: {
			return quadrangles.get(index).getB();
		}
		
		case C: {
			return quadrangles.get(index).getC();
		}
		
		case D: {
			return quadrangles.get(index).getD();
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + quadranglePoint);
		}
	}
	
	/**
	 * Returns the hull point after the quadrangle point identified
	 * by the argument moving clockwise in a standard 
	 * cartesian coordinate system.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the convex hull after the identified quadrangle point
	 */
	public Point getHullPointAfter(QuadranglePoint quadranglePoint) {
		if(quadrangles.size() == 0) {return null;}
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			Point point = quadrangles.get(tmpIndex).getA();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getA()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getA();
		}

		case B: {
			Point point = quadrangles.get(tmpIndex).getB();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getB()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getB();
		}

		case C: {
			Point point = quadrangles.get(tmpIndex).getC();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getC()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			Point point = quadrangles.get(tmpIndex).getD();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getD()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getD();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + quadranglePoint);
		}
	}
	

	
	/**
	 * Removes all quadrangles from the quadrangle sequence.
	 */
	public void clear() {
		quadrangles.clear();
		index = 0;
	}
	
	/**
	 * Adds a quadrangle to the quadrangle sequence.
	 *
	 * @param quadrangle the quadrangle
	 */
	public void add(Quadrangle quadrangle) {
		quadrangles.add(quadrangle);
	}

	/**
	 * Goes to the next quadrangle in the quadrangle sequence.
	 */
	public void next() {
		index = Math.floorMod(index + 1, quadrangles.size());	
	}
	
	/** 
	 * Goes to the pevious quadrangle in the quadrangle sequence.
	 * @return returns 1 if the round starts again
	 */
	public int previous() {
		int lastIndex = index;
		index = Math.floorMod(index - 1, quadrangles.size());	
		if(index > lastIndex) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Returns true, if the length of the longest 
	 * diameter of all the quadrangles in the quadrangle
	 * sequence is zero. This method can be used to determine
	 * if the point set only keeps one point.
	 * Returns false otherwise.
	 *
	 * @return true, if the longest diameter is zero. False otherwise.
	 */
	public boolean longestDiameterIsZero() {
		Point a = quadrangles.get(0).getA();
	    Point c = quadrangles.get(0).getC();
		return  quadrangles.size() == 1 && a == c ? true : false;
	}
	
	/**
	 * Gets the current quadrangle.
	 *
	 * @return the current quadrangle
	 */
	public Quadrangle getQuadrangle() {
		return quadrangles.get(index);
	}
	
	
	/**
	 * Checks if the sequence of quadrangles contains
	 * any quadrangles.
	 *
	 * @return true, if the quadrangle sequence is empty,
	 * false otherwise.
	 */
	public boolean isEmpty() {
		return quadrangles.isEmpty();
	}
	
	/**
	 * Gets the quadrangle with the largest area of the 
	 * quadrangle sequence.
	 *
	 * @return the quadrangle with the largest area.
	 */
	public Quadrangle getBiggestQuadrangle() {
		Quadrangle maxQuadrangle = new Quadrangle(quadrangles.get(0));
		for(int i = 1; i < quadrangles.size()/2 + 1; i++) {
			if(quadrangles.get(i).area() > maxQuadrangle.area()) {
				maxQuadrangle.copy(quadrangles.get(i));
			}
		}
		return maxQuadrangle;
	}
	
	/**
	 * Gets the biggest diameter from all the quadrangles in 
	 * the quadrangle sequence.
	 *
	 * @return the biggest diameter
	 */
	public Diameter getLongestDiameter() {
		Diameter maxDiameter = new Diameter(quadrangles.get(0).getA(), quadrangles.get(0).getC());
		Diameter tmpDiameter;
		
		/*We only need to go for a half turn and we only need to check for the 
		* distance A to C in every quadrangle.
		*/
		for(int i = 1; i < quadrangles.size(); i++) {
			tmpDiameter = new Diameter(quadrangles.get(i).getA(), quadrangles.get(i).getC());
			if(tmpDiameter.length() > maxDiameter.length()) {
				maxDiameter.copy(tmpDiameter);
			}
		}
		return maxDiameter;
	}

	/**
	 * Returns the number of quadrangles in the quadrangle sequence.
	 *
	 * @return the number of quadrangles in the quadrangle sequence
	 */
	public int size() {
		return quadrangles.size();
	}
}
