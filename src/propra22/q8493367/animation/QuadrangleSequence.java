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
 * as used for the animation.
 */
public class QuadrangleSequence {
	
	/** The quadrangles. */
	List<IQuadrangle> quadrangles = new ArrayList<>();
	
	/** The index. */
	int index;
	
	/**
	 * Gets the hull point before the current quadrangle point identified
	 * by the argument.
	 *
	 * @param quadranglePoint the quadrangle point A, B, C or D.
	 * @return the point of the convex hull before this point. We think in clockwise direction.
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
	 * Gets the hull point after.
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
	 * Clear.
	 */
	public void clear() {
		quadrangles.clear();
		index = 0;
	}
	
	/**
	 * Adds the.
	 *
	 * @param quadrangle the quadrangle
	 */
	public void add(IQuadrangle quadrangle) {
		quadrangles.add(quadrangle);
	}

	/**
	 * Next.
	 */
	public void next() {
		index = Math.floorMod(index + 1, quadrangles.size());	
	}
	
	/**
	 * Previous.
	 */
	public void previous() {
		index = Math.floorMod(index - 1, quadrangles.size());	
	}
	
	/**
	 * Biggest diameter is zero.
	 *
	 * @return true, if successful
	 */
	public boolean biggestDiameterIsZero() {
		IPoint a = quadrangles.get(0).getA();
	    IPoint c = quadrangles.get(0).getC();
		return  quadrangles.size() == 1 && a == c ? true : false;
	}
	
	/**
	 * Gets the quadrangle.
	 *
	 * @return the quadrangle
	 */
	public IQuadrangle getQuadrangle() {
		return quadrangles.get(index);
	}
	
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return quadrangles.isEmpty();
	}
	
	/**
	 * Gets the biggest quadrangle.
	 *
	 * @return the biggest quadrangle
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
	 * Gets the biggest diameter.
	 *
	 * @return the biggest diameter
	 */
	public IDiameter getBiggestDiameter() {
		IDiameter maxDiameter = new Diameter(quadrangles.get(0).getA(), quadrangles.get(0).getC());
		IDiameter tmpDiameter;
		for(int i = 1; i < quadrangles.size()/2 + 1; i++) {
			tmpDiameter = new Diameter(quadrangles.get(i).getA(), quadrangles.get(i).getC());
			if(tmpDiameter.length() > maxDiameter.length()) {
				maxDiameter.copy(tmpDiameter);
			}
		}
		return maxDiameter;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return quadrangles.size();
	}
}
