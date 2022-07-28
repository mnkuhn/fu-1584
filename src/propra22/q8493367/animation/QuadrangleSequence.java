package propra22.q8493367.animation;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.contour.IQuadrangle;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.shape.Diameter;
import propra22.q8493367.shape.Quadrangle;


/**
 * The Class QuadrangleSequence keeps a sequence of quadrangles
 * as used for the animation. This sequence can be seen as infinite. It arises 
 * from the calculation of the antipodal pairs according to the specification.
 */
public class QuadrangleSequence {
	
	/** The quadrangles */
	List<IQuadrangle> quadrangles = new ArrayList<>();
	
	/** The index of the current quadrangle*/
	int index;
	
	/**
	 * Gets the hull point before the current quadrangle point identified
	 * by the argument. We think in clockwise direction.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the convex hull before this point. 
	 */
	public IPoint getHullPointBefore(QuadranglePoint quadranglePoint) {
		if(quadrangles.size() == 0) {return null;}
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			IPoint point = quadrangles.get(tmpIndex).getA();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getA()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}	
			}
			return quadrangles.get(tmpIndex).getA();
		}

		case B: {
			IPoint point = quadrangles.get(tmpIndex).getB();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getB()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getB();	
		}

		case C: {
			IPoint point = quadrangles.get(tmpIndex).getC();
			if(quadrangles.size() > 1){
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getC()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			IPoint point = quadrangles.get(tmpIndex).getD();
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
	 * Gets the point of the quadrangle identified by the argument.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the quadrangle identified by the argument.
	 */
	public IPoint getHullPoint(QuadranglePoint quadranglePoint) {
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
	 * Gets the hull point after the current quadrangle point identified
	 * by the argument. We think in clockwise direction.
	 *
	 * @param quadranglePoint the quadrangle point
	 * @return the hull point after
	 */
	public IPoint getHullPointAfter(QuadranglePoint quadranglePoint) {
		if(quadrangles.size() == 0) {return null;}
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			IPoint point = quadrangles.get(tmpIndex).getA();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getA()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getA();
		}

		case B: {
			IPoint point = quadrangles.get(tmpIndex).getB();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getB()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getB();
		}

		case C: {
			IPoint point = quadrangles.get(tmpIndex).getC();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getC()) {
					tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
				}
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			IPoint point = quadrangles.get(tmpIndex).getD();
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
	public void add(IQuadrangle quadrangle) {
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
	 */
	public void previous() {
		index = Math.floorMod(index - 1, quadrangles.size());	
	}
	
	/**
	 * Returns true, if the length of the longest 
	 * diameter of all the quadrangles in the quadrangle
	 * sequence is zero. This method is used to determine
	 * if the point set only keeps one point.
	 * Returns false otherwise.
	 *
	 * @return true, if the longest diameter 
	 */
	public boolean longestDiameterIsZero() {
		IPoint a = quadrangles.get(0).getA();
	    IPoint c = quadrangles.get(0).getC();
		return  quadrangles.size() == 1 && a == c ? true : false;
	}
	
	/**
	 * Gets the current quadrangle.
	 *
	 * @return the current quadrangle
	 */
	public IQuadrangle getQuadrangle() {
		return quadrangles.get(index);
	}
	
	
	/**
	 * Checks if the sequence of quadrangles does 
	 * not contain any quadrangle.
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
	public IQuadrangle getBiggestQuadrangle() {
		IQuadrangle maxQuadrangle = new Quadrangle(quadrangles.get(0));
		for(int i = 1; i < quadrangles.size()/2 + 1; i++) {
			if(quadrangles.get(i).area() > maxQuadrangle.area()) {
				maxQuadrangle.copy(quadrangles.get(i));
			}
		}
		return maxQuadrangle;
	}
	
	/**
	 * Gets the longest diameter from all the quadrangles in 
	 * the quadrangle sequence.
	 *
	 * @return the biggest diameter
	 */
	public IDiameter getLongestDiameter() {
		IDiameter maxDiameter = new Diameter(quadrangles.get(0).getA(), quadrangles.get(0).getC());
		IDiameter tmpDiameter;
		
		/*We only need to go for a half turn and we only need to check for the 
		* distance A to C.
		*/
		for(int i = 1; i < quadrangles.size()/2 + 1; i++) {
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
	 * @return the int
	 */
	public int size() {
		return quadrangles.size();
	}
}
