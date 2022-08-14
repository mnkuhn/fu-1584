package propra22.q8493367.usecases;

import java.util.List;

import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;



/**
 * 
 * Command for inserting a number of randomly generated 
 * points into a point set. 
 * 
 */

public class InsertRandomPointsCommand implements ICommand {

	/** The inserted points. */
	private List<Point> randomPoints;
	
	/** The point set in which the points are inserted. */
	private PointSet pointSet;
	
	
	
	/**
	 * Instantiates a new insert random points command.
	 *
	 * @param randomPoints the randomly generated points
	 * @param pointSet the point set
	 */
	public InsertRandomPointsCommand(List<Point> randomPoints, PointSet pointSet) {
		this.randomPoints = randomPoints;
		this.pointSet = pointSet;
	}
    
	
	/**
	 * Executes the command.
	 */
	@Override
	public void execute() {
		for(Point point : randomPoints) {
			// No check necessary but we keep it to stay independent
			pointSet.addCheckedWithSorting(point);
		}	
	}

	/**
	 * Unexecutes the command.
	 */
	@Override
	public void unexecute() {
		for(Point point : randomPoints) {
			pointSet.removePoint(point);
		}		
	}	
}
