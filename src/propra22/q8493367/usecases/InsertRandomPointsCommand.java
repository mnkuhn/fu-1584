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
	private List<Point> points;
	
	/** The point set in which the points are inserted. */
	private PointSet pointSet;
	
	
	
	/**
	 * Instantiates a new insert random points command.
	 *
	 * @param points the randomly generated points
	 * @param pointSet the point set
	 */
	public InsertRandomPointsCommand(List<Point> points, PointSet pointSet) {
		this.points = points;
		this.pointSet = pointSet;
	}
    
	
	/**
	 * Execute the command.
	 */
	@Override
	public void execute() {
		for(Point point : points) {
			pointSet.addPoint(point);
		}	
	}

	/**
	 * Unexecute the command.
	 */
	@Override
	public void unexecute() {
		for(Point point : points) {
			pointSet.removePoint(point);
		}		
	}	
}
