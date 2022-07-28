package propra22.q8493367.contour;


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
	
	/**The highest y value found during the sweep
	 * from left to right.
	 */
	private int highestYFound;
    
	/**The lowest y value found during the sweep
	 * from left to right.
	 */
	private int lowestYFound;
	
	/**
	 * Instantiates a new contour polygon calculator.
	 *
	 * @param pointSet  the point set.
	 * @param hull the hull, which is the result of this calculation,
	 * that is the contour polygon.
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
	public void calculateSection(ContourType sectionType) {
		
		switch (sectionType) {
			case UPPERLEFT: {
				calculateUpperLeft();
				break;
			}
			case LOWERLEFT: {
				calculateLowerLeft();
				break;
			}
			case UPPERRIGHT: {
				calculateUpperRight();
				break;
			}
			
			case LOWERRIGHT: {
				calculateLowerRight();
				break;
			}
		}
	}

    
	/**
	 * Calculate lower left section. This function has to be called before
	 * calculateUpperRight because HighestYFound has to be set.
	 */
	private void calculateUpperLeft() {
		IPoint point = pointSet.getPointAt(0);
		hull.addPointToSection(point, ContourType.UPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, ContourType.UPPERLEFT);
			}
		}
		highestYFound = maxYSoFar;
	}
	
	
    /**
     * Calculate upper left section. This function has to be called before
     * calculateLowerRight because the LowestYFound has to be set.
     */
    private void calculateLowerLeft() {
		IPoint point = pointSet.getPointAt(0);
		hull.addPointToSection(point, ContourType.LOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, ContourType.LOWERLEFT);
			}
		}
		lowestYFound = minYSoFar;
	}
	
	
	/**
	 * Calculate lower right section. This function has to be called before
	 * calculateUpperLeft() because the highestYFound has to be set. 
	 */
	private void calculateUpperRight() {
		IPoint point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		hull.addPointToSection(point, ContourType.UPPERRIGHT);	
		
		int maxYSoFar = point.getY();
		int pointY;
	    int i = pointSet.getNumberOfPoints() - 2;
		while(maxYSoFar != highestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, ContourType.UPPERRIGHT);
			}
		}
	}
	
	
	/**
	 * Calculate upper right section. This function has to be called after 
	 * calculateLowerLeft() because the lowestYFound has to be set.
	 */
	private void calculateLowerRight() {
		IPoint point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		hull.addPointToSection(point, ContourType.LOWERRIGHT);		
		
		int minYSoFar = point.getY();
		int pointY;
		int i = pointSet.getNumberOfPoints() - 2;
		while(minYSoFar != lowestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, ContourType.LOWERRIGHT);
			}
		}
	}
	


	/**
	 * Calculates the contour polygon.
	 */
	public void calculateContourPolygon() {
		hull.clear();
		if(!pointSet.isEmpty()) {
			calculateUpperLeft();
			calculateLowerLeft();
			calculateUpperRight();
			calculateLowerRight();
		}
	}	
}
