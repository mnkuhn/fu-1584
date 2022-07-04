package propra22.q8493367.convex;

import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.Quadrangle;
import propra22.q8493367.point.Point;

/**
 * The Class QuadrangleCalculator is responsible for the calculation
 * of the biggest quadrangle.
 */
public class QuadrangleCalculator implements IQuadrangleCalculator {

	/**
	 *{@inheritDoc}
	 */
	@Override
	public Quadrangle calculateQuadrangle(
			IHullIterator aIt, 
			IHullIterator bIt, 
			IHullIterator cIt, 
			IHullIterator dIt) {
		
		while(Point.isHigher(cIt.getPoint(), aIt.getPoint(), bIt.getNextPoint(), bIt.getPoint())) {
			bIt.next();	
		}
		
		while(Point.isHigher(aIt.getPoint(), cIt.getPoint(), dIt.getNextPoint(), dIt.getPoint())) {
			dIt.next();	
		}
	
		return new Quadrangle(aIt.getPoint(), bIt.getPoint(), cIt.getPoint(), dIt.getPoint());    
	}
}
