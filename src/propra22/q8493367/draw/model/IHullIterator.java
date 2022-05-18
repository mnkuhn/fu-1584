package propra22.q8493367.draw.model;

import propra22.q8493367.point.IPoint;

public interface IHullIterator {

	IPoint getPoint();

	IPoint getNextPoint();

	void next();

	boolean hasReachedLimit();
	
	int getIndex();

}
