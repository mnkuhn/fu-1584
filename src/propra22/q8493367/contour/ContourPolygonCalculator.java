package propra22.q8493367.contour;


import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;

/**
 * The Class ContourPolygonCalculator 
 */
public class ContourPolygonCalculator implements IHullGenerator {

	/** The model of the drawPanel */
	private IDrawPanelModel drawPanelModel;
	
	/**The contour polygon*/
	private IHull hull;
	
	/**
	 * Instantiates a new contour polygon calculator.
	 *
	 * @param drawPanelModel - the model of the drawPanel.
	 * @param contourPolygon - the contour polygon
	 */
	public ContourPolygonCalculator(IDrawPanelModel drawPanelModel, IHull hull) {
		this.drawPanelModel = drawPanelModel;
		this.hull = hull;
	}
    
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
	
	// kommt  raus
	private void calculateLeftSide() {
	
		IPoint point = drawPanelModel.getPointAt(0);
		
		drawPanelModel.addPointToSection(point, SectionType.LOWERLEFT);
		drawPanelModel.addPointToSection(point, SectionType.UPPERLEFT);
		
		int minYSoFar = point.getY();
		int maxYSoFar = minYSoFar;
		int pointY;

		for(int i = 1; i < drawPanelModel.getNumberOfPoints(); i++) {
			point = drawPanelModel.getPointAt(i);
			pointY = point.getY();
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				drawPanelModel.addPointToSection(point, SectionType.LOWERLEFT);
			}
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				drawPanelModel.addPointToSection(point, SectionType.UPPERLEFT);
			}
		}
	}
	
	
	

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
	 * calculates the right upper and the right lower section of the 
	 * contour polygon.
	 * @see propra22.q8493367.SectionType SectionType
	 */
	
	// kommt raus
	private void calculateRightSide() {
		
		IPoint point = drawPanelModel.getPointAt(drawPanelModel.getNumberOfPoints() - 1);
		
		drawPanelModel.addPointToSection(point, SectionType.LOWERRIGHT);
		drawPanelModel.addPointToSection(point, SectionType.UPPERRIGHT);		
		
		int maxYSoFar = point.getY();
		int minYSoFar = maxYSoFar;
		int pointY;
	
		for(int i = drawPanelModel.getNumberOfPoints() - 2; i >= 0; i --) {
			point = drawPanelModel.getPointAt(i);
			pointY = point.getY();
			
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				drawPanelModel.addPointToSection(point, SectionType.LOWERRIGHT);
			}
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				drawPanelModel.addPointToSection(point, SectionType.UPPERRIGHT);
			}
		}
	}



	public void calculateContourPolygon() {
		hull.clearAllSections();
		for(SectionType sectionType : SectionType.values()) {
			calculateSection(sectionType);
		}
	}



	

   /*
	public void concatenateParts(List<Point> contourPolygon, List<Point> leftLower, List<Point> leftUpper, List<Point> rightLower, List<Point> rightUpper) {
		contourPolygon.clear();
		int k = 1;
		System.out.println("leftLower Size: " + leftLower.size());
		for(int i = 0; i < leftLower.size(); i++) {
			point = leftLower.get(i);
			contourPolygon.add(point);
		}
		System.out.println("rightLower Size: " + rightLower.size());
		if(rightLower.size() > 1) {
			if(point == rightLower.get(rightLower.size() - 1)) {k = 2;}
			for(int i = rightLower.size() - k; i >= 0; i--) {
				point = rightLower.get(i);
				contourPolygon.add(rightLower.get(i));
			}
		}
		
		
		k = 1;
		
		int l = 0;;
		System.out.println("rightUpper Size: " + rightUpper.size());
		if(!rightUpper.isEmpty() && (point != rightUpper.get(0))) {l = 1;}
		for(int i = 1 - l; i < rightUpper.size(); i++) {
			point = rightUpper.get(i);
			contourPolygon.add(point);
		}
		System.out.println("leftUpper Size: " + leftUpper.size());
		
		if(!leftUpper.isEmpty() && point == (leftUpper.get(leftUpper.size() - 1 ))) {
			k = 2;
		}
			
			for(int i = leftUpper.size() - k; i >= 0; i--) {
				contourPolygon.add(leftUpper.get(i));
			}	
			
	}
	*/
	
}
