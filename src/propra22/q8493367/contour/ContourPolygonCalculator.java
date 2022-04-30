package propra22.q8493367.contour;



import propra22.q8493367.draw.model.IDrawPanelModel;
import propra22.q8493367.point.IPoint;




public class ContourPolygonCalculator implements IContourPolygonCalculator {
	
	// the model which contains the points
	private IDrawPanelModel model;
	
	public ContourPolygonCalculator(IDrawPanelModel model) {
		this.model = model;
	}
    
	@Override
	public void updateModel() {
		
		if(!model.isEmpty()) {
			
			for(SectionType sectionType : SectionType.values()) {
				model.clearSection(sectionType);
			}
			calculateLeftSide();
			calculateRightSide();	    			
		}
	}
	
	
	/* calculates the left upper and the left lower section of the contour polygon
	 * adds the data to the model
	 */
	
	private void calculateLeftSide() {
	
		IPoint point = model.getPointAt(0);
		
		model.addPointToSection(point, SectionType.LOWERLEFT);
		model.addPointToSection(point, SectionType.UPPERLEFT);
		
		int minYSoFar = point.getY();
		int maxYSoFar = minYSoFar;
		int pointY;

		for(int i = 1; i < model.getNumberOfPoints(); i++) {
			point = model.getPointAt(i);
			pointY = point.getY();
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				model.addPointToSection(point, SectionType.LOWERLEFT);
			}
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				model.addPointToSection(point, SectionType.UPPERLEFT);
			}
		}
	}
	
	/* calculates the right upper and the right lower section of the contour polygon
	 * adds the data to the model
	 */
	private void calculateRightSide() {
		
		IPoint point = model.getPointAt(model.getNumberOfPoints() - 1);
		
		model.addPointToSection(point, SectionType.LOWERRIGHT);
		model.addPointToSection(point, SectionType.UPPERRIGHT);		
		
		int maxYSoFar = point.getY();
		int minYSoFar = maxYSoFar;
		int pointY;
	
		for(int i = model.getNumberOfPoints() - 2; i >= 0; i --) {
			point = model.getPointAt(i);
			pointY = point.getY();
			
			if(pointY > minYSoFar) {
				minYSoFar = pointY;
				model.addPointToSection(point, SectionType.LOWERRIGHT);
			}
			if(pointY < maxYSoFar) {
				maxYSoFar = pointY;
				model.addPointToSection(point, SectionType.UPPERRIGHT);
			}
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
