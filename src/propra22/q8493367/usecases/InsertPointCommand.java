package propra22.q8493367.usecases;

import propra22.q8493367.entities.Point;
import propra22.q8493367.entities.PointSet;



/**
 * Command for inserting a point into the point set.
 */

public class InsertPointCommand implements ICommand {
    
    /** The point set, into which the point is inserted. */
    PointSet pointSet;
	
	/**  The point which is inserted. */
	Point point;
	
	
	
	/**
	 * Instantiates a new insert point command.
	 *
	 * @param point the point which is inserted into the point set.
	 * @param pointSet the point set
	 */
	public InsertPointCommand(Point point, PointSet pointSet) {
	      this.point = point;
	      this.pointSet = pointSet;
	}
	
	/**
	 * Executes the insert point command.
	 */
	@Override
	public void execute() {
		pointSet.addPoint(point);
		
	}

	/**
	 * Unexecutes the insert point command.
	 */
	@Override
	public void unexecute() {
		pointSet.removePoint(point);	
	}

}
