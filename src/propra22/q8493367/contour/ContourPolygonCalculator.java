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
	
	private int highestYFound;
    private int lowestYFound;
	
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
	public void calculateSection(ContourType sectionType) {
		
		switch (sectionType) {
			case NEWUPPERLEFT: {
				calculateUpperLeft();
				break;
			}
			case NEWLOWERLEFT: {
				calculateLowerLeft();
				break;
			}
			case NEWUPPERRIGHT: {
				calculateUpperRight();
				break;
			}
			
			case NEWLOWERRIGHT: {
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
		hull.addPointToSection(point, ContourType.NEWUPPERLEFT);
		
		int maxYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, ContourType.NEWUPPERLEFT);
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
		hull.addPointToSection(point, ContourType.NEWLOWERLEFT);
		
		int minYSoFar = point.getY();
		int pointY;

		for(int i = 1; i < pointSet.getNumberOfPoints(); i++) {
			point = pointSet.getPointAt(i);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, ContourType.NEWLOWERLEFT);
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
		hull.addPointToSection(point, ContourType.NEWUPPERRIGHT);	
		
		int maxYSoFar = point.getY();
		int pointY;
	    int i = pointSet.getNumberOfPoints() - 2;
		while(maxYSoFar != highestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			
			if(pointY > maxYSoFar) {
				maxYSoFar = pointY;
				hull.addPointToSection(point, ContourType.NEWUPPERRIGHT);
			}
		}
	}
	
	
	/**
	 * Calculate upper right section. This function has to be called after 
	 * calculateLowerLeft() because the lowestYFound has to be set.
	 */
	private void calculateLowerRight() {
		IPoint point = pointSet.getPointAt(pointSet.getNumberOfPoints() - 1);
		hull.addPointToSection(point, ContourType.NEWLOWERRIGHT);		
		
		int minYSoFar = point.getY();
		int pointY;
		int i = pointSet.getNumberOfPoints() - 2;
		while(minYSoFar != lowestYFound) {
			point = pointSet.getPointAt(i--);
			pointY = point.getY();
			if(pointY < minYSoFar) {
				minYSoFar = pointY;
				hull.addPointToSection(point, ContourType.NEWLOWERRIGHT);
			}
		}
	}
	
	
	

	/**
	 * Calculate the contour polygon.
	 */
	public void calculateContourPolygon() {
		hull.clear();
		calculateUpperLeft();
		calculateLowerLeft();
		calculateUpperRight();
		calculateLowerRight();
	}	
}
