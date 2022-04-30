package propra22.q8493367.convex;

import propra22.q8493367.contour.SectionType;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;


public class ConvexHullCalculator implements IConvexHullCalculator {
	
	private IDrawPanelModel model;
	
	public ConvexHullCalculator(IDrawPanelModel model) {
		this.model = model;
	}
	
	@Override
	public void calculateSection(SectionType sectionType) {
		if(!model.sectionIsEmpty(sectionType)) {
			int size = model.getSizeOfSection(sectionType);
			System.out.println("ConveXHullCalculator calculateSection size of section: "+ sectionType.toString() + " " + size) ;
			if(size >= 3) {
				int next = 2;
				int leadingBase = 1;
				int followingBase = 0;
				
				while(next < size) {
					IPoint a = model.getPointFromSection(followingBase, sectionType);
					IPoint b = model.getPointFromSection(leadingBase, sectionType);
					IPoint c = model.getPointFromSection(next, sectionType);
					
					
					if( DFV(a, b, c, sectionType)  < 0){
						followingBase++;
						leadingBase++;
						next++;
					}
					
					else {
						model.removeSectionPoint(leadingBase, sectionType);
		                size--;
						if(followingBase > 0) {
							next--;
							leadingBase--;
							followingBase--;
							while(followingBase > 0 && DFV(model.getPointFromSection(followingBase, sectionType), model.getPointFromSection(leadingBase, sectionType), 
									model.getPointFromSection(next, sectionType), sectionType) > 0){
								model.removeSectionPoint(leadingBase, sectionType);
								size--;
								followingBase--;
								leadingBase--;
								
								// nicht so schön..
								if(next == size) {break;}
							}	
						}			
					}	
				}
			}
		}
	}
		
	
	
	private  long DFV(IPoint a, IPoint b, IPoint c, SectionType sectionType) {
		long summand1 = (long)a.getX()*(long)(b.getY() - (long)c.getY());
		long summand2 = (long)b.getX()*(long)(c.getY() - (long)a.getY());
		long summand3 = (long)c.getX()*(long)(a.getY() - (long)b.getY());
		
		return sectionType.getSign() * (summand1 + summand2 + summand3);
	}
	
	@Override
	public void updateModel() {
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
	}
}
