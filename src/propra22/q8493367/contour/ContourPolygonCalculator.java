package propra22.q8493367.contour;


import propra22.q8493367.convex.IHull;
import propra22.q8493367.draw.model.IPointSet;
import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Class ContourPolygonCalculator.
 */
public class ContourPolygonCalculator implements ISectionCalculator {

	/**  The point set. */
	private IPointSet pointSet;
	
	/** The contour polygon. */
	private IHull hull;
	
	/**
	 * Instantiates a new contour polygon calculator.
	 *
	 * @param pointSet - the model of the draw panel.
	 * @param hull the hull
	 */
	public ContourPolygonCalculator(IPointSet pointSet, IHull hull) {
		this.pointSet = pointSet;
		this.hull = hull;
	}
    
	
	/**
	 * Calculates one of the four sections of the contour polygon.
	 *
	 * @param sectionType the section type
	 */
	@Override
	public void calculateSection(SectionType sectionType) {
		
		switch (sectionType) {
			case LOWERLEFT: {
				calculateLowerLeftSection();
				break;
			}
			case UPPERLEFT: {
				calculateUpperLeftSection();
				break;
			}
			case LOWERRIGHT: {
				calculateLowerRightSection();
				break;
			}
			
			case UPPERRIGHT: {
				calculateUpperRightSection();
				break;
			}
		}
	}

    
	/**
	 * Calculate lower left section.
	 */
	private void calculateLowerLeftSection() {
		IPoint point = pointSet.getPointAt(0);
		hull.addPointToSection(point, SectionType.LOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, SectionType.LOWERLEFT);
			}
		}
	}
	
	
    /**
     * Calculate upper left section.
     */
    private void calculateUpperLeftSection() {
		IPoint point = pointSet.getPointAt(0);
		hull.addPointToSection(point, SectionType.UPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, SectionType.UPPERLEFT);
			}
		}
	}
	
	
	/**
	 * Calculate lower right section.
	 */
	private void calculateLowerRightSection() {
		IPoint point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		hull.addPointToSection(point, SectionType.LOWERRIGHT);	
		
		int minYSoFar = point.getY();
		int pointY;
	
		for(int i = pointSet.getNumberOfPoints() - 2; i >= 0; i--) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, SectionType.LOWERRIGHT);
			}
		}
	}
	
	
	/**
	 * Calculate upper right section.
	 */
	private void calculateUpperRightSection() {
		IPoint point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		hull.addPointToSection(point, SectionType.UPPERRIGHT);		
		
		int maxYSoFar = point.getY();
		int pointY;
	
		for(int i = pointSet.getNumberOfPoints() - 2; i >= 0; i--) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, SectionType.UPPERRIGHT);
			}
		}
	}
	
	
	

	/**
	 * Calculate the contour polygon.
	 */
	public void calculateContourPolygon() {
		hull.clear();
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
	}	
}
