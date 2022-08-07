package propra22.q8493367.entities;



/**
 * The Class ContourPolygonCalculator is responsible for the calculation
 * of the contour polygon.
 */
public class ContourPolygonCalculator implements IContourCalculator {

	/**  The point set. */
	private PointSet pointSet;
	
	/** The contour polygon as the result of the calculation. */
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
	 * @param pointSet  The point set.
	 * @param hull The contour polygon as the result of the calculation.
	 */
	public ContourPolygonCalculator(PointSet pointSet, IHull hull) {
		this.pointSet = pointSet;
		this.hull = hull;
	}
    
	
	/**
	 * Calculates one of the four contours of the contour polygon.
	 *
	 * @param contourType the type of the contour that is upper left,
	 * lower left, upper right or lower right.
	 */
	@Override
	public void calculateContour(ContourType contourType) {
		
		switch (contourType) {
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
	 * Calculates the lower left section. This function has to be called before
	 * calculateUpperRight because HighestYFound has to be set.
	 */
	private void calculateUpperLeft() {
		Point point = pointSet.getPointAt(0);
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
     * Calculates the upper left section. This function has to be called before
     * calculateLowerRight because the LowestYFound has to be set.
     */
    private void calculateLowerLeft() {
		Point point = pointSet.getPointAt(0);
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
	 * Calculates the lower right section. This function has to be after
	 * calculateUpperLeft() because the highestYFound has to be set. 
	 */
	private void calculateUpperRight() {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
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
	 * Calculate the lower right section. This function has to be called after 
	 * calculateLowerLeft() because the lowestYFound has to be set.
	 */
	private void calculateLowerRight() {
		Point point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
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
