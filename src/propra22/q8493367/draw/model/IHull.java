package propra22.q8493367.draw.model;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.point.IPoint;

public interface IHull {
	
	public boolean sectionIsEmpty(SectionType sectionType);
	public int getSizeOfSection(SectionType sectionType);
	void addPointToSection(IPoint point, SectionType sectionType);
	IPoint getPointFromSection(int index, SectionType sectionType);
	void removePointFromSection(IPoint point, SectionType sectionType);
	void clearAllSections();
	void removePointFromSection(int index, SectionType sectionType);
}
