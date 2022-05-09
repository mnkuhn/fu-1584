package propra22.q8493367.contour;


import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;

// TODO: Auto-generated Javadoc
/**
 * The Class ContourPolygonCalculator.
 */
public class ContourPolygonCalculator implements ISectionCalculator {

	/**  The model of the drawPanel. */
	private IDrawPanelModel drawPanelModel;
	
	/** The contour polygon. */
	private IHull hull;
	
	/**
	 * Instantiates a new contour polygon calculator.
	 *
	 * @param drawPanelModel - the model of the draw panel.
	 * @param hull the hull
	 */
	public ContourPolygonCalculator(IDrawPanelModel drawPanelModel, IHull hull) {
		this.drawPanelModel = drawPanelModel;
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
		IPoint point = drawPanelModel.getPointAt(0);
		hull.addPointToSection(point, SectionType.LOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < drawPanelModel.getNumberOfPoints(); i++) {
			point = drawPanelModel.getPointAt(i);
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
		IPoint point = drawPanelModel.getPointAt(0);
		hull.addPointToSection(point, SectionType.UPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < drawPanelModel.getNumberOfPoints(); i++) {
			point = drawPanelModel.getPointAt(i);
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
		IPoint point = drawPanelModel.getPointAt(drawPanelModel.getNumberOfPoints() - 1);
		hull.addPointToSection(point, SectionType.LOWERRIGHT);	
		
		int minYSoFar = point.getY();
		int pointY;
	
		for(int i = drawPanelModel.getNumberOfPoints() - 2; i >= 0; i--) {
			point = drawPanelModel.getPointAt(i);
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
		IPoint point = drawPanelModel.getPointAt(drawPanelModel.getNumberOfPoints() - 1);
		hull.addPointToSection(point, SectionType.UPPERRIGHT);		
		
		int maxYSoFar = point.getY();
		int pointY;
	
		for(int i = drawPanelModel.getNumberOfPoints() - 2; i >= 0; i--) {
			point = drawPanelModel.getPointAt(i);
			pointY = point.getY();
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, SectionType.UPPERRIGHT);
			}
		}
	}
	
	
	

	/**
	 * Calculate contour polygon.
	 */
	public void calculateContourPolygon() {
		hull.clearAllSections();
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
	}
	
}
