package propra22.q8493367.draw.model;



import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.Hull.HullIterator;
import propra22.q8493367.point.IPoint;

public interface IHull {
	
	public boolean sectionIsEmpty(SectionType sectionType);
	public int getSizeOfSection(SectionType sectionType);
	void addPointToSection(IPoint point, SectionType sectionType);
	IPoint getPointFromSection(int index, SectionType sectionType);
	void removePointFromSection(IPoint point, SectionType sectionType);
	// einfach clear
	void clearAllSections();
	void removePointFromSection(int index, SectionType sectionType);
	int[][] toArray();
	
	
	void outArray();  // for testing
	void outSections();
	int getIndexOfRightMostPoint();
	
	IPoint[] getDiameter();
	public void setDiameter(IPoint[] diameter);
	int size();
	public IPoint get(int i);
	HullIterator getIterator(int index, int limit);
	void createList();
	
}
