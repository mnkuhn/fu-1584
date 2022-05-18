package propra22.q8493367.draw.model;



import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;

public interface IHull {
	
	boolean sectionIsEmpty(SectionType sectionType);
	int getSizeOfSection(SectionType sectionType);
	void addPointToSection(IPoint point, SectionType sectionType);
	IPoint getPointFromSection(int index, SectionType sectionType);
	void removePointFromSection(IPoint point, SectionType sectionType);
	
	void clear();
	void removePointFromSection(int index, SectionType sectionType);
	int[][] toArray();
	
	int getIndexOfRightMostPoint();
	
	int size();
	public IPoint get(int i);
	IHullIterator getIterator(int index, int limit);
	void createList();
}
