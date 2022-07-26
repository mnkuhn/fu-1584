package propra22.q8493367.animation;

import java.util.ArrayList;
import java.util.List;

import propra22.q8493367.contour.IDiameter;
import propra22.q8493367.contour.IHull;
import propra22.q8493367.contour.IQuadrangle;
import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.point.IPoint;
import propra22.q8493367.point.Point;
import propra22.q8493367.shape.Diameter;
import propra22.q8493367.shape.Quadrangle;

public class QuadrangleSequence {
	List<IQuadrangle> quadrangles = new ArrayList<>();
	int index;
	
	public IPoint getHullPointBefore(QuadranglePoint quadranglePoint) {
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			IPoint point = quadrangles.get(tmpIndex).getA();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getA()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
				return quadrangles.get(tmpIndex).getA();
			} else if (quadrangles.size() == 1) {
				
			} else {
				return null;
			}	
		}

		case B: {
			IPoint point = quadrangles.get(tmpIndex).getB();
			if(quadrangles.size() > 1) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				while (point == quadrangles.get(tmpIndex).getB()) {
					tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
				}
				return quadrangles.get(tmpIndex).getB();
			} else if (quadrangles.size() == 1) {
				
			} else {
				return null;
			}
			
		}

		case C: {
			IPoint point = quadrangles.get(tmpIndex).getC();
			tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getC()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			IPoint point = quadrangles.get(tmpIndex).getD();
			tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getD()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getD();

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + quadranglePoint);
		}

	}	
	
	public IPoint getHullPoint(QuadranglePoint quadranglePoint) {
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
	
	public IPoint getHullPointAfter(QuadranglePoint quadranglePoint) {
		int tmpIndex = index;
		switch (quadranglePoint) {
		case A: {
			IPoint point = quadrangles.get(tmpIndex).getA();
			tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getA()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getA();
		}

		case B: {
			IPoint point = quadrangles.get(tmpIndex).getB();
			tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getB()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getB();
		}

		case C: {
			IPoint point = quadrangles.get(tmpIndex).getC();
			tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getC()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getC();
		}

		case D: {
			IPoint point = quadrangles.get(tmpIndex).getD();
			tmpIndex = Math.floorMod(tmpIndex + 1, quadrangles.size());
			while (point == quadrangles.get(tmpIndex).getD()) {
				tmpIndex = Math.floorMod(tmpIndex - 1, quadrangles.size());
			}
			return quadrangles.get(tmpIndex).getD();

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + quadranglePoint);
		}
	}
	

	
	public void clear() {
		quadrangles.clear();
		index = 0;
	}
	
	public void add(IQuadrangle quadrangle) {
		quadrangles.add(quadrangle);
	}

	public void next() {
		index = Math.floorMod(index + 1, quadrangles.size());	
	}
	
	public void previous() {
		index = Math.floorMod(index - 1, quadrangles.size());	
	}
	
	public boolean biggestDiameterIsZero() {
		IPoint a = quadrangles.get(0).getA();
	    IPoint c = quadrangles.get(0).getC();
		return  quadrangles.size() == 1 && a == c ? true : false;
	}
	
	public IQuadrangle getQuadrangle() {
		return quadrangles.get(index);
	}
	
	
	public boolean isEmpty() {
		return quadrangles.isEmpty();
	}
	
	public IQuadrangle getBiggestQuadrangle() {
		IQuadrangle maxQuadrangle = quadrangles.get(0);
		IQuadrangle tmpQuadrangle;
		for(int i = 1; i < quadrangles.size()/2 + 1; i++) {
			tmpQuadrangle = quadrangles.get(i);
			if(tmpQuadrangle.area() > maxQuadrangle.area()) {
				maxQuadrangle.copy(tmpQuadrangle);
			}
		}
		return maxQuadrangle;
	}
	
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
}
