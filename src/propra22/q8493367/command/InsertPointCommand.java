package propra22.q8493367.command;

import propra22.q8493367.contour.IPointSet;
import propra22.q8493367.point.IPoint;


/**
 * Command for inserting a point into the point set.
 */

public class InsertPointCommand implements ICommand {
    
    /** The point set, into which the point is inserted. */
    IPointSet pointSet;
	
	/**  The point which is inserted. */
	IPoint point;
	
	
	
	/**
	 * Instantiates a new insert point command.
	 *
	 * @param point the point which is inserted into the point set.
	 * @param pointSet the point set
	 */
	public InsertPointCommand(IPoint point, IPointSet pointSet) {
	      this.point = point;
	      this.pointSet = pointSet;
	}
	
	/**
	 * Execute the insert point command.
	 */
	@Override
	public void execute() {
		pointSet.addPoint(point);
		
	}

	/**
	 * Unexecute the insert point command.
	 */
	@Override
	public void unexecute() {
		pointSet.removePoint(point);	
	}

}
