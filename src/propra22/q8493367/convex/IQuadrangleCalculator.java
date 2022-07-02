package propra22.q8493367.convex;

import propra22.q8493367.draw.model.IHullIterator;
import propra22.q8493367.draw.model.Quadrangle;

public interface IQuadrangleCalculator {
	public Quadrangle calculateQuadrangle(
			IHullIterator aIt, 
			IHullIterator bIt, 
			IHullIterator cIt, 
			IHullIterator dIt);
}
